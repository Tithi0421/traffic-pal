package com.afinal.android.trafficpal.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afinal.android.trafficpal.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by DHIREN on 11-03-2018.
 */
public class RecyclerviewAdapter2 extends RecyclerView.Adapter<RecyclerviewAdapter2.MyHolder>{

    List<BlackList> listdata;
    Context con;

    public RecyclerviewAdapter2(List<BlackList> listdata,Context con) {
        this.listdata = listdata;
        this.con=con;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview2,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(final MyHolder holder, int position) {
        BlackList data = listdata.get(position);
        holder.vlinum.setText(data.getLinum());
        holder.violations.setText(data.getViolations());
        holder.vdate.setText(data.getDate());
        holder.ukey.setText(data.getUkey());
        holder.vpoints.setText(data.getPoints());
        holder.linear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String user= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                /*ClipboardManager myClipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                String user= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                ClipData myClip = ClipData.newPlainText("label","Violation id: " + holder.ukey.getText().toString()+"\nOfficer id:"+user+"\nLicense number:"+holder.vlinum.getText().toString()+"\nViolations: "+holder.violations.getText().toString()+"\nDate: "+holder.vdate.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(v.getContext(), "Copied to clipboard" , Toast.LENGTH_SHORT ).show();*/
                final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
                sharedPreferenceUtils.setString(con,"sc_vid",holder.ukey.getText().toString());
                sharedPreferenceUtils.setString(con,"sc_ofid",user);
                sharedPreferenceUtils.setString(con,"sc_lno",holder.vlinum.getText().toString());
                sharedPreferenceUtils.setString(con,"sc_date",holder.vdate.getText().toString());
                sharedPreferenceUtils.setString(con,"sc_vio",holder.violations.getText().toString());

                Fragment SubmitChangesFragment = new SubmitChangesFragment();
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)con).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container_body, SubmitChangesFragment);
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vlinum,vdate,violations, ukey,vpoints;
        LinearLayout linear;

        public MyHolder(View itemView) {
            super(itemView);
            vlinum = (TextView) itemView.findViewById(R.id.vlinum);
            vdate = (TextView) itemView.findViewById(R.id.vdate);
            ukey= (TextView)itemView.findViewById(R.id.ukey);
            violations = (TextView) itemView.findViewById(R.id.violations);
            vpoints=(TextView)itemView.findViewById(R.id.vpoints);
            linear=(LinearLayout)itemView.findViewById(R.id.linearcopy);
        }
    }


}