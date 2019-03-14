package com.afinal.android.trafficpal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afinal.android.trafficpal.R;
import com.firebase.client.Firebase;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


public class Scanning1Fragment extends Fragment {

    private Button submit1,submit2,capture1,expandableButton1,expandableButton2,expandableButton3;
    private EditText text1,text2,text3,text4,text5;
    private Firebase mRef;
    final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
    private DatabaseReference mDatabase,demoRef,demoRef1;
    ExpandableRelativeLayout expandableLayout1,expandableLayout2,expandableLayout3;

    public Scanning1Fragment() {
        // Required empty public constructor
    }

    public static Scanning1Fragment newInstance(int page, String title) {
        Scanning1Fragment scanning1fragment = new Scanning1Fragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        args.putString("Title1", title);
        scanning1fragment.setArguments(args);
        return scanning1fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent i1 = new Intent(getActivity(), Scanner.class);
        startActivity(i1);*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scanning1, container, false);
        //SurfaceView cameraView = (SurfaceView) rootView.findViewById(R.id.surface_view_scanner);
        text1 = (EditText) rootView.findViewById(R.id.id1);
        text2 = (EditText) rootView.findViewById(R.id.id2);
        text3 = (EditText) rootView.findViewById(R.id.id3);
        text4 = (EditText) rootView.findViewById(R.id.id4);
        text5 = (EditText) rootView.findViewById(R.id.id5);
        submit1 = (Button) rootView.findViewById(R.id.submit1);
        submit2 = (Button) rootView.findViewById(R.id.submit2);
        capture1 = (Button) rootView.findViewById(R.id.capture1);
       // done = (Button) rootView.findViewById(R.id.done);
        expandableLayout1 = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout1);
        expandableButton1= (Button) rootView.findViewById(R.id.expandableButton1);
        expandableLayout2 = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout2);
        expandableButton2= (Button) rootView.findViewById(R.id.expandableButton2);
        expandableLayout3 = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout3);
        expandableButton3= (Button) rootView.findViewById(R.id.expandableButton3);

        Firebase.setAndroidContext(getActivity());
        mRef = new Firebase("https://traffic-pal.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        demoRef = mDatabase.child("LicenceDetails");
        demoRef1= mDatabase.child("VehicleDetails");


        addListenerOnSubmit();
        //addListenerOnDone();

        expandableButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                expandableLayout1.toggle();
                expandableLayout2.collapse();
                expandableLayout3.collapse();
                expandableButton1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_green,0);
                expandableButton2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_white,0);
                expandableButton3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_white,0);
            }
        });

        expandableButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                expandableLayout2.toggle();
                expandableLayout1.collapse();
                expandableLayout3.collapse();
                expandableButton1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_white,0);
                expandableButton2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_green,0);
                expandableButton3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_white,0);

            }
        });

        expandableButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                expandableLayout3.toggle();
                expandableLayout1.collapse();
                expandableLayout2.collapse();

                expandableButton1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_white,0);
                expandableButton2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_white,0);
                expandableButton3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.button_green,0);
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
   /* public void expandableButton1(View view) {
        expandableLayout1.toggle(); // toggle expand and collapse

    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    private void addListenerOnSubmit() {

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text1.getText().toString().length() == 0) {
                    text1.setError("This field is required!");
                    text1.requestFocus();
                }
                else if (text2.getText().toString().length() == 0) {
                    text2.setError("This field is required!");
                    text2.requestFocus();
                }
                else {
                    //thiswas Fragment Scanning2Fragment = new Scanning2Fragment();
                    //thiswas android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    //   ViewPagerActivity viewPagerActivity = new ViewPagerActivity();
                    //   viewPagerActivity.setCurrentItem(true);

                    //thiswas android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    //thiswas fragmentTransaction.replace(R.id.container_body, Scanning2Fragment);
                    //thiswas fragmentTransaction.addToBackStack(null);
                    String input1 = text1.getText().toString().toUpperCase();
                    String input2 = text2.getText().toString();
                    final String input = input1 + " " + input2;

                    demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(input)) {
                                // run some code
                                //SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
                                sharedPreferenceUtils.setString(getActivity(),"Lno",input);
                                Fragment Scanning2Fragment = new Scanning2Fragment();
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                fragmentTransaction.replace(R.id.container_body, Scanning2Fragment);
                                fragmentTransaction.addToBackStack(null);

                                // Commit the transaction
                                fragmentTransaction.commit();
                            }
                            else {
                                Toast.makeText(getActivity(),"Licence number is not valid",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    /*SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
                    sharedPreferenceUtils.setString(getActivity(),"Lno",input);*/


                    // Commit the transaction
                    // thiswas fragmentTransaction.commit();
                    //    Firebase.setAndroidContext(getActivity());
                    //   mDatabase = FirebaseDatabase.getInstance().getReference().child("LicenceDetails").child(input);

               /*     mDatabase.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Detail detail = dataSnapshot.getValue(Detail.class);

                                String name = detail.getName();
                                if (name.equals("")){
                                    Toast temp = Toast.makeText(getActivity(), "invalid input", Toast.LENGTH_SHORT);
                                    temp.show();
                                }
                                else{
                                    Intent intent=new Intent(getActivity(),ViewPagerActivity.class);
                                    startActivity(intent);
                                }


                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            // [START_EXCLUDE]
                            System.out.println("The read failed: " + databaseError.getMessage());
                        }
                    });




                    Scanning2Fragment s2f = new Scanning2Fragment();
                    Bundle args = new Bundle();
                    args.putString("Lno", text.getText().toString());
                    s2f.setArguments(args);*/
                    // getFragmentManager().beginTransaction().add(R.id.container_body, s2f).commit();

                    // ((ViewPagerActivity)getActivity()).setCurrentItem(true);

                    // Intent intent=new Intent(getActivity(),ViewPagerActivity.class);
                    //  startActivity(intent);

                }



            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text3.getText().toString().length() == 0) {
                    text3.setError("This field is required!");
                    text3.requestFocus();
                }
                else if (text4.getText().toString().length() == 0) {
                    text4.setError("This field is required!");
                    text4.requestFocus();
                }else if (text5.getText().toString().length() == 0) {
                    text5.setError("This field is required!");
                    text5.requestFocus();
                }
                else {

                    String input1 = text3.getText().toString().toUpperCase();
                    String input2 = text4.getText().toString().toUpperCase();
                    String input3 = text5.getText().toString().toUpperCase();
                    final String input = input1 + input2 + input3;
                    final DatabaseReference demotemp;
                    demotemp=demoRef1.child(input);
                    demoRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(input)) {
                                // run some code
                                demotemp.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String lic = dataSnapshot.getValue(String.class);
                                      //  sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
                                        sharedPreferenceUtils.setString(getActivity(),"Lno",lic);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });


                                Fragment Scanning2Fragment = new Scanning2Fragment();
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                fragmentTransaction.replace(R.id.container_body, Scanning2Fragment);
                                fragmentTransaction.addToBackStack(null);

                                // Commit the transaction
                                fragmentTransaction.commit();
                            }
                            else {
                                Toast.makeText(getActivity(),"Vehicle number is not valid",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }



            }
        });

        capture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ScanFragment = new ScanFragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container_body, ScanFragment);
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
    }
    /*private void addListenerOnDone() {

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(getActivity(), OcrActivity.class);
                startActivity(i1);

            }
        });

    }*/
}

