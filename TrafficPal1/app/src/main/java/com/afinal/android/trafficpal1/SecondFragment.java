package com.afinal.android.trafficpal1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Ravi on 29/07/15.
 */
public class SecondFragment extends Fragment {

    List<BlackList> list;
    RecyclerView recyclerview;
    private DatabaseReference mDatabase;
    Date strDate;
    private Button logout;
    FirebaseDatabase database;
    DatabaseReference myRef,newRef;


    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance(int page, String title) {
        SecondFragment secondfragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        args.putString("Title1", title);
        secondfragment.setArguments(args);
        return secondfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
        final String temp = sharedPreferenceUtils.getString(getActivity(), "lno");

        logout = (Button) rootView.findViewById(R.id.logout2);
        Firebase.setAndroidContext(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp);

        recyclerview = (RecyclerView)rootView.findViewById(R.id.rview);
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp).child("blackdata");
        newRef =FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(temp);

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


        addListenerOnLogout();
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

    private void addListenerOnLogout(){

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(getActivity(), LoginActivity.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SharedPreferenceUtils sharedPreferenceUtils=SharedPreferenceUtils.getInstance();
                sharedPreferenceUtils.logout(getActivity());
                startActivity(i1);

            }
        });

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

