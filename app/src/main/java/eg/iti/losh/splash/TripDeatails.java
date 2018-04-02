package eg.iti.losh.splash;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TripDeatails extends AppCompatActivity {

    EditText name_txt;
    EditText start_txt;
    EditText end_txt;
     static EditText date_txt;
    Button save;
    Button edit;
    Button delete_btn;
    Button Date_Btn;
    static int Round=0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Trip");

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trip_deatails);
//        Toolbar toolbar= findViewById(R.id.action_bar);
//        setSupportActionBar(toolbar);

        name_txt = findViewById(R.id.name_txt);
        start_txt = findViewById(R.id.start_txt);
        end_txt = findViewById(R.id.end_txt);
        date_txt = findViewById(R.id.date_txt);
        EnableFunction(false);
        //////////////Buttons//////////////
        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);
        delete_btn=findViewById(R.id.delete_btn);
        Date_Btn = (Button) findViewById(R.id.editDate);
        Date_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Round=0;
                showTruitonTimePickerDialog(view);
                showTruitonDatePickerDialog(view);
            }
        });
        save.setEnabled(false);
        /////////////////////////////////
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//
                System.out.println(snapshot.child("name").getValue());
                name_txt.setText((CharSequence) snapshot.child("name").getValue());
                start_txt.setText((CharSequence) snapshot.child("Start_address").getValue());
                end_txt.setText((CharSequence) snapshot.child("End_address").getValue());

                date_txt.setText(new StringBuilder().append(snapshot.child("Date").getValue()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setEnabled(true);
                EnableFunction(true);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myRef.child("name").setValue(name_txt.getText().toString());
                    myRef.child("Start_address").setValue(start_txt.getText().toString());
                    myRef.child("End_address").setValue(end_txt.getText().toString());
                    myRef.child("Date").setValue(date_txt.getText().toString());
                    EnableFunction(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.removeValue();
                Intent homeIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homeIntent);
            }

        });
    }
    void EnableFunction(Boolean status){
        name_txt.setEnabled(status);
        start_txt.setEnabled(status);
        end_txt.setEnabled(status);
        date_txt.setEnabled(status);



    }
    ////////////
    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dpd=new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if(Round==0) {
                date_txt.setText(day + "/" + (month + 1) + "/" + year);
            }else{
//                Return_Date.setText(day + "/" + (month + 1) + "/" + year);
            }
        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            TimePickerDialog tpd=new TimePickerDialog(getActivity(), this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
            return tpd;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            if(Round==0) {
                date_txt.setText(date_txt.getText() + " -" + hourOfDay + ":" + minute);
            }else{
//                Return_Date.setText(Return_Date.getText() + " -" + hourOfDay + ":" + minute);
            }
        }
    }
}
