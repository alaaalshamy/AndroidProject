package eg.iti.losh.splash;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import eg.iti.losh.splash.ModelTrip;
import eg.iti.losh.splash.R;

public class MainActivityasmeen extends AppCompatActivity {
    RecyclerView recyclerView;
    //ArrayList<ModelTrip>triplist;
    private DatabaseReference mdatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main_activityasmeen );

        mdatabase= FirebaseDatabase.getInstance ().getReference ().child("Trip");
        mdatabase.keepSynced ( true );


        // Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        // setSupportActionBar ( toolbar );

        FloatingActionButton fab = (FloatingActionButton) findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Snackbar.make ( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction ( "Action", null ).show ();
            }
        } );


        recyclerView=findViewById ( R.id.rv );

        /*triplist=new ArrayList<> (  );


        triplist.add ( new ModelTrip ( "univesity" ) );
        triplist.add ( new ModelTrip ( "shopping" ) );
        triplist.add ( new ModelTrip ( "play" ) );
        triplist.add ( new ModelTrip ( "univesity1" ) );
        triplist.add ( new ModelTrip ( "Cairo" ) );
        triplist.add ( new ModelTrip ( "game" ) );
        triplist.add ( new ModelTrip ( "univesity2" ) );
        triplist.add ( new ModelTrip ( "univesity3" ) );
        triplist.add ( new ModelTrip ( "univesity" ) );
        triplist.add ( new ModelTrip ( "shopping" ) );
        triplist.add ( new ModelTrip ( "play" ) );
        triplist.add ( new ModelTrip ( "univesity1" ) );
        triplist.add ( new ModelTrip ( "Cairo" ) );
        triplist.add ( new ModelTrip ( "game" ) );
        triplist.add ( new ModelTrip ( "univesity2" ) );
        triplist.add ( new ModelTrip ( "univesity3" ) );

        */
        LinearLayoutManager layoutManager=new LinearLayoutManager ( this );
        RecyclerView.LayoutManager rvliLayoutManager=layoutManager;
        recyclerView.setLayoutManager ( rvliLayoutManager );
        recyclerView.setHasFixedSize ( true );

        //tripAdapter tripadapter=new tripAdapter ( this ,triplist);
        // recyclerView.setAdapter ( tripadapter );

    }

    @Override
    protected void onStart() {
        super.onStart ();
        FirebaseRecyclerAdapter<ModelTrip, TripViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelTrip, TripViewHolder>
                ( ModelTrip.class, R.layout.rv_trip_item, TripViewHolder.class, mdatabase ) {
            @Override
            protected void populateViewHolder(TripViewHolder viewHolder, ModelTrip model, int position) {

                viewHolder.setname ( model.getName () );
            }
        };
        recyclerView.setAdapter ( firebaseRecyclerAdapter );

    }
    public  static class TripViewHolder extends  RecyclerView.ViewHolder{
        View mView;
        public  TripViewHolder (View item_view){
            super(item_view);
            mView=item_view;

        }
        public  void setname(String name){
            TextView item_name=(TextView)mView.findViewById ( R.id.item_name );
            item_name.setText ( "name" );

        }

    }

}