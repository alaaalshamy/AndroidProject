package eg.iti.losh.splash;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Google       Company on 3/30/2018.
 */

public class tripAdapter extends RecyclerView.Adapter<tripAdapter.ViewHolder> {
    private Context mContext;
    private  ArrayList<ModelTrip>mlist;
    tripAdapter(Context context, ArrayList<ModelTrip>list){

        mContext=context;
        mlist=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from ( mContext );
        View view=layoutInflater.inflate ( R.layout.rv_trip_item,parent,false );
        ViewHolder viewHolder=new ViewHolder ( view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelTrip modelTrip=mlist.get ( position );
        TextView name=holder.item_name;


        name.setText (modelTrip.getName ()  );
    }

    @Override
    public int getItemCount() {
        return mlist.size ();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView item_name;

        public ViewHolder(View itemView){
            super(itemView);
            item_name=itemView.findViewById ( R.id.item_name );
        }
    }
}
