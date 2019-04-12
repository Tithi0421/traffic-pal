package com.afinal.android.trafficpal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.afinal.android.trafficpal.R;
import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Tithi on 29/07/15.
 */
public class Scanning4Fragment extends Fragment {

    TextView item,date,no,name;
    Long total,ftotal,black,lastsus;
    String item1,name1,dt,no1,update;
    private Firebase mRef;
    private DatabaseReference mDatabase,demoRef;
    private Records records;
    private DbQueries dbqueries;
    SharedPreferenceUtils sharedPreferenceUtils;
    private long id;
    Button submit,pre;
    Date strDate;
    int flag=0; // set when suspended

    public Scanning4Fragment() {
        // Required empty public constructor
    }

 /*   public static Scanning4Fragment newInstance(int page, String title) {
        Scanning4Fragment scanning4fragment = new Scanning4Fragment();
        Bundle args = new Bundle();
        args.putInt("3", page);
        args.putString("Title3", title);
        scanning4fragment.setArguments(args);
        return scanning4fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scanning4, container, false);

        item = (TextView) rootView.findViewById(R.id.item);
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        item1 = sharedPreferenceUtils.getString(getActivity(), "key3");
        total = sharedPreferenceUtils.getLong(getActivity(), "key4");
        black = sharedPreferenceUtils.getLong(getActivity(),"black");
        lastsus = sharedPreferenceUtils.getLong(getActivity(),"lastsus");

        //   black = Long.parseLong(blackno);
        ftotal = total + black;
        item.setText(item1);
        date = (TextView) rootView.findViewById(R.id.date1);


        Date cal = (Date) Calendar.getInstance().getTime();
        dt = cal.toLocaleString();
        date.setText(dt.toString());


        // add days code starts
        if(ftotal >= lastsus + 12) {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            String formattedDate = df.format(cal);
            try {
                c.setTime(df.parse(formattedDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 7);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            update = sdf1.format(c.getTime());
            flag=1;
        }
        // add days code ends

        // comparison code starts
       /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            strDate = sdf.parse("12-11-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > strDate.getTime()) {
            update = "is less";
        }
        else{
            update= "not less";
        }*/
        // comparison code ends



        no = (TextView) rootView.findViewById(R.id.Id1);
        name = (TextView) rootView.findViewById(R.id.name1);
        no1 = sharedPreferenceUtils.getString(getActivity(), "key5");
        no.setText(no1);
        name1 = sharedPreferenceUtils.getString(getActivity(), "key6");
        name.setText(name1);

        Firebase.setAndroidContext(getActivity());
        mRef = new Firebase("https://traffic-pal.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        demoRef = mDatabase.child("LicenceDetails").child(no1);
        submit = (Button) rootView.findViewById(R.id.submit3);
        pre = (Button) rootView.findViewById(R.id.pre3);
        addListenerOnSubmit();
        addListenerOnPrevious();
        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public void addListenerOnSubmit(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item1.equals("")) {
                    Snackbar.make(submit, "You have not selected any faults", Snackbar.LENGTH_LONG).show();
                }
                else {

                records = new Records();

                records.setLno(no1);
                records.setName1(name1);
                records.setVio(item1);
                records.setDate(dt.toString());
                records.setTotal(total.toString());
                if (flag == 1) {
                    records.setSdate(update);
                }

                dbqueries = new DbQueries(getActivity());
                id = dbqueries.addRecords(records);
                Snackbar.make(submit, "Entries are successfully added into Database", Snackbar.LENGTH_LONG).show();

                ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
                if (connectionDetector.isConnected()) {

                    //   Firebase mRefChild = mRef.child("name");
                    // mRefChild.setValue("value");
                    // SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();

                    String uid = sharedPreferenceUtils.getString(getActivity(), "key");
                    String key = mDatabase.push().getKey();
                    mDatabase.child("LicenceDetails").child(no1).child("blackdata").child(key).child("date").setValue(dt.toString());
                    mDatabase.child("LicenceDetails").child(no1).child("blackdata").child(key).child("points").setValue(total.toString() + " " + "points");
                    mDatabase.child("LicenceDetails").child(no1).child("blackdata").child(key).child("violations").setValue(item1);
                    mDatabase.child("LicenceDetails").child(no1).child("blackdata").child(key).child("ukey").setValue(key);


                    demoRef.child("blacknumber").setValue(ftotal);
                    if (!(flag == 0 || item1.toLowerCase().contains("accident"))) {
                        demoRef.child("sdate").setValue(update);
                        demoRef.child("lastsuspend").setValue(ftotal);
                        demoRef.child("sstatus").setValue("Suspended until " + update);
                    } else if (item1.toLowerCase().contains("accident")) {
                        demoRef.child("lastsuspend").setValue(ftotal);
                        demoRef.child("sstatus").setValue("Accidental Case Pending");
                    }

                    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                    mDatabase.child("BlackEntries").child(userid).child(key).child("date").setValue(dt.toString());
                    mDatabase.child("BlackEntries").child(userid).child(key).child("points").setValue(total.toString() + " " + "points");
                    mDatabase.child("BlackEntries").child(userid).child(key).child("violations").setValue(item1);
                    mDatabase.child("BlackEntries").child(userid).child(key).child("ukey").setValue(key);
                    mDatabase.child("BlackEntries").child(userid).child(key).child("linum").setValue(no1);

                    Snackbar.make(submit, "Entries are successfully added", Snackbar.LENGTH_LONG).show();

                    dbqueries.deleteRecords(id);

                    Intent i1 = new Intent(getActivity(), MainActivity.class);

                    startActivity(i1);

                }
            }
            }
        });
    }

    public void addListenerOnPrevious(){
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Fragment Scanning3Fragment = new Scanning3Fragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container_body, Scanning3Fragment);
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();*/

                getActivity().onBackPressed();
            }
        });
    }
}

