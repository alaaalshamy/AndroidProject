package eg.iti.losh.splash;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TripDeatails extends AppCompatActivity {

    EditText name_txt;
    EditText start_txt;
    EditText end_txt;
    EditText date_txt;
    Button save;
    Button edit;
    Button delete_btn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("losh");

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
        Toolbar toolbar= findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        name_txt = findViewById(R.id.name_txt);
        start_txt = findViewById(R.id.start_txt);
        end_txt = findViewById(R.id.end_txt);
        date_txt = findViewById(R.id.date_txt);
        EnableFunction(false);
        //////////////Buttons//////////////
        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);
        delete_btn=findViewById(R.id.delete_btn);

        save.setEnabled(false);
        /////////////////////////////////
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//
                System.out.println(snapshot.child("name").getValue());
                name_txt.setText((CharSequence) snapshot.child("name").getValue());
                start_txt.setText((CharSequence) snapshot.child("start").getValue());
                end_txt.setText((CharSequence) snapshot.child("end").getValue());

                date_txt.setText(new StringBuilder().append(snapshot.child("date").getValue()));

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
                    myRef.child("start").setValue(start_txt.getText().toString());
                    myRef.child("end").setValue(end_txt.getText().toString());
                    myRef.child("date").setValue(date_txt.getText().toString());
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
}
