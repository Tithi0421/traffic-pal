package com.afinal.android.trafficpal.Activity;

import android.content.Context;
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
import android.widget.Toast;

import com.afinal.android.trafficpal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChangesFragment extends Fragment {
    List<BlackList> list;
    RecyclerView recyclerview;
    DatabaseReference myRef;

    public ChangesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.fragment_changes, container, false);
        recyclerview = (RecyclerView)rootView.findViewById(R.id.rview);
        myRef = FirebaseDatabase.getInstance().getReference().child("BlackEntries").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

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
                    String linum=blackData.getLinum();
                    String ukey = blackData.getUkey();

                    listdata.setDate(date);
                    listdata.setPoints(points);
                    listdata.setViolations(violations);
                    listdata.setLinum(linum);
                    listdata.setUkey(ukey);

                    list.add(listdata);
                    // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                }

                RecyclerviewAdapter2 recycler = new RecyclerviewAdapter2(list,getContext());
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
