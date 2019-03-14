package com.afinal.android.trafficpal1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FirstFragment extends Fragment {

    TextView cname,licenceno,dob,doi,addr,val,black,status,auth;
    ImageView image;
    Button next,logout;
    private DatabaseReference mDatabase;
    Date strDate;

    FirebaseDatabase database;
    DatabaseReference myRef,newRef;
    RecyclerView recyclerview;

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance(int page, String title) {
        FirstFragment firstfragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        args.putString("Title1", title);
        firstfragment.setArguments(args);
        return firstfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
       final String temp = sharedPreferenceUtils.getString(getActivity(), "lno");

        cname = (TextView) rootView.findViewById(R.id.textView_namevalue);
        licenceno = (TextView) rootView.findViewById(R.id.textView_numbervalue);
        dob = (TextView) rootView.findViewById(R.id.textView_dobvalue);
        doi = (TextView) rootView.findViewById(R.id.textView_issuevalue);
        auth = (TextView) rootView.findViewById(R.id.textView_aodvalue);
        addr = (TextView) rootView.findViewById(R.id.textView_addrvalue);
        image=(ImageView)rootView.findViewById(R.id.imageView_imagevalue) ;
        val=(TextView)rootView.findViewById(R.id.textView_validityvalue);
        black = (TextView) rootView.findViewById(R.id.textView_blacklistvalue);
        status=(TextView)rootView.findViewById(R.id.textView_statusvalue);
        logout=(Button) rootView.findViewById(R.id.logout1);

   //     FirebaseDatabase.getInstance().setPersistenceEnabled(true);
     //   if (!FirebaseApp.getApps(getActivity()).isEmpty())
       //     FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase.setAndroidContext(getActivity());
        database = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp);

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

                    if(!((stat.startsWith("Not")) || (stat.startsWith("not")))){
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



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(getActivity(), LoginActivity.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //SharedPreferenceUtils sharedPreferenceUtils=SharedPreferenceUtils.getInstance();
                sharedPreferenceUtils.logout(getActivity());
                startActivity(i1);

            }
        });



        // Inflate the layout for this fragment

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener( new View.OnKeyListener()
        {
            private Boolean exit = false;
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        // if (getFragmentManager().getBackStackEntryCount() > 0 ){
                        //  getFragmentManager().popBackStack();
                        //   }
                        //   else {
                        //logic for identifying double back press, expires after 3 seconds
                        if (exit) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            getActivity().finish(); // finish activity
                            System.exit(0);
                            // getFragmentManager().popBackStack();
                        } else { //exit = true;
                            Toast.makeText(getActivity(), "Press again to exit application", Toast.LENGTH_SHORT).show();
                            exit = true;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    exit = false;

                                }

                            }, 3000);


                        }
                        return true;
                    }
                }
                return false;
            }
        } );


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
}

