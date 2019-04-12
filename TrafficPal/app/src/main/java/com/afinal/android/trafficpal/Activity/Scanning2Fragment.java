package com.afinal.android.trafficpal.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.afinal.android.trafficpal.R;
import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tithi on 29/07/15.
 */
public class Scanning2Fragment extends Fragment {

    //Button scan;
    TextView cname,licenceno,dob,doi,addr,val,black,status,auth;
    ImageView image;
    Button next;
    private DatabaseReference mDatabase;
    Date strDate;

    FirebaseDatabase database;
    DatabaseReference myRef,newRef;
    List<BlackList> list;
    RecyclerView recyclerview;

    public Scanning2Fragment() {
        // Required empty public constructor
    }


  /*  public static Scanning2Fragment newInstance(int page, String title) {
        Scanning2Fragment scanning2fragment = new Scanning2Fragment();
        Bundle args = new Bundle();
        args.putInt("2", page);
        args.putString("Title2", title);
        scanning2fragment.setArguments(args);
        return scanning2fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scanning2, container, false);

        hideKeyboard(getContext());
        final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
        final String temp = sharedPreferenceUtils.getString(getActivity(), "Lno");
        cname = (TextView) rootView.findViewById(R.id.textView_namevalue);
        auth = (TextView) rootView.findViewById(R.id.textView_aodvalue);
        licenceno = (TextView) rootView.findViewById(R.id.textView_numbervalue);
        dob = (TextView) rootView.findViewById(R.id.textView_dobvalue);
        doi = (TextView) rootView.findViewById(R.id.textView_issuevalue);
        addr = (TextView) rootView.findViewById(R.id.textView_addrvalue);
        image=(ImageView)rootView.findViewById(R.id.imageView_imagevalue) ;
        val=(TextView)rootView.findViewById(R.id.textView_validityvalue);
        black = (TextView) rootView.findViewById(R.id.textView_blacklistvalue);
        status=(TextView)rootView.findViewById(R.id.textView_statusvalue);

        next = (Button) rootView.findViewById(R.id.next1);

        Firebase.setAndroidContext(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp);

        recyclerview = (RecyclerView)rootView.findViewById(R.id.rview);
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp).child("blackdata");
        newRef =FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp);

        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Detail detail = dataSnapshot.getValue(Detail.class);

                    String autho = detail.getAod();
                    String name = detail.getCname();
                    String lno = detail.getLicenceno();
                    String bdate = detail.getBdate();
                    String issuedate = detail.getIssue_date();
                    String address = detail.getAddr();
                    String validity = detail.getValidity();
                    String url= detail.getUrl();
                    Long blackno = detail.getBlacknumber();
                    String stat = detail.getSstatus();
                    Long lastsus = detail.getLastsuspend();
                    String susdate = detail.getSdate();

                    if(!((stat.startsWith("Not")) || (stat.startsWith("not")) || (stat.startsWith("A"))||(stat.startsWith("a")))){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            strDate = sdf.parse(susdate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (System.currentTimeMillis() > strDate.getTime()) {
                            stat = "Not Suspended";
                            newRef.child("sstatus").setValue("Not Suspended");
                            //status.setTextColor(Color.BLACK);
                        }
                    }

                    SharedPreferenceUtils sharedPreferenceUtils1 = SharedPreferenceUtils.getInstance();
                    sharedPreferenceUtils.setString(getActivity(), "key5" , lno);
                    sharedPreferenceUtils.setString(getActivity(), "key6" , name);
                    sharedPreferenceUtils.setLong(getActivity(), "black" , blackno);
                    sharedPreferenceUtils.setLong(getActivity(), "lastsus" , lastsus);

                    auth.setText(autho);
                    cname.setText(name);
                    licenceno.setText(lno);
                    dob.setText(bdate);
                    doi.setText(issuedate);
                    addr.setText(address);
                    val.setText(validity);
                    black.setText(blackno.toString());
                    status.setText(stat);
                    Glide.with(getActivity().getApplicationContext()).load(url).into(image);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                // StringBuffer stringbuffer = new StringBuffer();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    BlackData blackData = dataSnapshot1.getValue(BlackData.class);
                    BlackList listdata = new BlackList();
                    String date=blackData.getDate();
                    String points=blackData.getPoints();
                    String violations=blackData.getViolations();
                    String ukey = blackData.getUkey();
                    listdata.setDate(date);
                    listdata.setPoints(points);
                    listdata.setViolations(violations);
                    listdata.setUkey(ukey);
                    list.add(listdata);
                    // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                }

                RecyclerviewAdapter recycler = new RecyclerviewAdapter(list);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity());
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator( new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //  Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        addListenerOnNext();
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

    public static void hideKeyboard(Context ctx){
        InputMethodManager inputmanager = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        //check if no view has focus
        View v = ((Activity)ctx).getCurrentFocus();
        if(v==null)
            return;

        inputmanager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }


    public void addListenerOnNext(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment Scanning3Fragment = new Scanning3Fragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container_body, Scanning3Fragment);
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
    }

}

