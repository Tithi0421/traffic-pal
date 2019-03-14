package com.afinal.android.trafficpal1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.afinal.android.trafficpal.R;

import java.util.List;
/**
 * Created by DHIREN on 11-03-2018.
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder>{

    List<BlackList> listdata;

    public RecyclerviewAdapter(List<BlackList> listdata) {
        this.listdata = listdata;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(final MyHolder holder, int position) {
        BlackList data = listdata.get(position);
        holder.vname.setText(data.getPoints());
        holder.vemail.setText(data.getViolations());
        holder.vaddress.setText(data.getDate());
        holder.ukey.setText(data.getUkey());
        holder.linear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                ClipboardManager myClipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);


                ClipData myClip = ClipData.newPlainText("label","Violation id: " + holder.ukey.getText().toString()+"\nViolations: "+holder.vemail.getText().toString()+"\nDate: "+holder.vaddress.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(v.getContext(), "Copied to clipboard" , Toast.LENGTH_SHORT ).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vname , vaddress,vemail, ukey;
        LinearLayout linear;

        public MyHolder(View itemView) {
            super(itemView);
            vname = (TextView) itemView.findViewById(R.id.vname);
            vemail = (TextView) itemView.findViewById(R.id.vemail);
            vaddress = (TextView) itemView.findViewById(R.id.vaddress);
            ukey= (TextView)itemView.findViewById(R.id.ukey);
            linear=(LinearLayout)itemView.findViewById(R.id.linearcopy);
        }
    }


}