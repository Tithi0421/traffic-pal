package com.afinal.android.trafficpal.Activity;

/**
 * Created by DHIREN on 01-02-2018.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NetworkMonitor extends BroadcastReceiver {

    DatabaseReference mDatabase;
    private  Firebase mRef;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Network Monitor", "action: "
                + intent.getAction());
        int[] type = {ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};
      /*  if(isNetworkAvailable(context,type) == true){
            mRef = new Firebase("https://krte-8d43f.firebaseio.com/");
            mDatabase = FirebaseDatabase.getInstance().getReference();
            UserForms userForms = new UserForms();
            long id = userForms.getId1();
            String uid = SharedPreferenceUtils.getInstance().getString(context, "key");
            String key = mDatabase.push().getKey();
            mDatabase.child("users").child(uid).child(key).setValue(userForms);
            Toast.makeText(context,"entries are successfully added",Toast.LENGTH_LONG).show();
         //   Snackbar.make(submit, "Entries are successfully added", Snackbar.LENGTH_LONG).show();
            DbQueries dbQueries = new DbQueries(context);
            dbQueries = new DbQueries(context);
            dbQueries.deleteUserForms(id);

           return;
        }
        else {
            Toast.makeText(context,"No Net",Toast.LENGTH_SHORT).show();
        }*/
        if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo =
                    intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {
                // Wifi is connected
                Firebase.setAndroidContext(context);
                mRef = new Firebase("https://traffic-pal.firebaseio.com/");
                mDatabase = FirebaseDatabase.getInstance().getReference();
             /*   UserForms userForms = new UserForms();
                DbQueries dbQueries = new DbQueries(context);
                long id = userForms.getId1();
                List<UserForms> uForms;
                uForms = dbQueries.getAllUserForms();
                String uid = SharedPreferenceUtils.getInstance().getString(context, "key");
                String key = mDatabase.push().getKey();
                for (int i=0;i<uForms.size();i++) {

                    mDatabase.child("users").child(uid).child(key).setValue(uForms.get(i));
                    Toast.makeText(context, "entries are successfully added", Toast.LENGTH_LONG).show();
                    //   Snackbar.make(submit, "Entries are successfully added", Snackbar.LENGTH_LONG).show();
                    dbQueries = new DbQueries(context);
                    dbQueries.deleteUserForms(id);

                }*/
                return;
            }

        }
    }

    public static boolean isNetworkAvailable(Context context, int[] typeNetworks){
        try {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Service.CONNECTIVITY_SERVICE);
            for (int typeNetwork : typeNetworks) {
                NetworkInfo networkInfo = connectivity.getNetworkInfo(typeNetwork);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;

    }
}