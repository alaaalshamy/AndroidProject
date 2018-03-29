package eg.iti.losh.splash;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.Calendar;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addTrip extends AppCompatActivity {
    PlaceAutocompleteFragment autocompleteFragment;
    PlaceAutocompleteFragment autocompleteFragment1;
    String Start_Address;
    String End_Address;
    EditText Name_text;
    Button Date_Btn;
    static TextView Date_text;
    EditText Notes_text;
    static int Round=0;
    CheckBox Round_check;
    Button Save;
    View focusView=null;
    Button Return;
    static TextView Return_Date;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        Name_text = (EditText) findViewById(R.id.Name_text);
        Notes_text = (EditText) findViewById(R.id.Notes_text);
        Round_check = (CheckBox) findViewById(R.id.checkBox);
        Date_text = (TextView) findViewById(R.id.Date_Text);
        Save = (Button) findViewById(R.id.Save_BTN);
        Return = (Button) findViewById(R.id.Return_BTN);
        Return_Date = (TextView) findViewById(R.id.Return_Date);
        Return.setVisibility(View.GONE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.Start_Frag);
        autocompleteFragment1 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.End_Frag);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                // Log.i(TAG, "Place: " + place.getName());
                Start_Address = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                // Log.i(TAG, "An error occurred: " + status);
            }
        });
        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                // Log.i(TAG, "Place: " + place.getName());
                End_Address = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                // Log.i(TAG, "An error occurred: " + status);
            }
        });
        Date_Btn = (Button) findViewById(R.id.Pick_Date);
        Date_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Round=0;
                showTruitonTimePickerDialog(view);
                showTruitonDatePickerDialog(view);
            }
        });
        Round_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    Return.setVisibility(View.VISIBLE);
                    Round=1;
                    Return.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showTruitonTimePickerDialog(view);
                            showTruitonDatePickerDialog(view);
                        }
                    });
                }else{
                    Round=0;
                    Return.setVisibility(View.GONE);
                }
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cancle=false;
                if(TextUtils.isEmpty(Name_text.getText().toString())){
                    Name_text.setError("this field is required");
                    focusView=Name_text;
                    cancle=true;
                }
                if(TextUtils.isEmpty(Start_Address)){
                    cancle=true;
                }if(TextUtils.isEmpty(End_Address)){
                    cancle=true;
                }if(TextUtils.isEmpty(Date_text.getText().toString())){
                    Date_text.setError("this field is required");
                    focusView=Date_text;
                    cancle=true;
                }if(TextUtils.isEmpty(Notes_text.getText().toString())){
                    Notes_text.setError("this field is required");
                    focusView=Notes_text;
                    cancle=true;
                }
                if(cancle==false) {
                    mDatabase.child(Name_text.getText().toString()).child("name").setValue(Name_text.getText().toString());
                    mDatabase.child(Name_text.getText().toString()).child("Start_address").setValue(Start_Address);
                    mDatabase.child(Name_text.getText().toString()).child("End_address").setValue(End_Address);
                    mDatabase.child(Name_text.getText().toString()).child("Date").setValue(Date_text.getText());
                    mDatabase.child(Name_text.getText().toString()).child("Notes").setValue(Notes_text.getText().toString());
                    if(Round_check.isChecked()){
                        mDatabase.child(Name_text.getText().toString()).child("Round").setValue("true");
                        mDatabase.child(Name_text.getText().toString()).child("ReturnDate").setValue(Return_Date.getText().toString());
                    }
                }else{
                    focusView.requestFocus();
                }
            }
        });
    }
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
                Date_text.setText(day + "/" + (month + 1) + "/" + year);
            }else{
                Return_Date.setText(day + "/" + (month + 1) + "/" + year);
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
                    DateFormat.is24HourFormat(getActivity()));
            return tpd;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            
            if(Round==0) {
                Date_text.setText(Date_text.getText() + " -" + hourOfDay + ":" + minute);
            }else{
                    Return_Date.setText(Return_Date.getText() + " -" + hourOfDay + ":" + minute);
                }
        }
    }

}
