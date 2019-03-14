package com.afinal.android.trafficpal1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText text1,text2,text3;
    private Button next;
    private Firebase mRef;
    final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
    private DatabaseReference mDatabase,demoRef,demoRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        text1 = (EditText) findViewById(R.id.id1);
        text2 = (EditText) findViewById(R.id.id2);
        text3 = (EditText) findViewById(R.id.adhar1);
        next = (Button)findViewById(R.id.next);

        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://traffic-pal.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        demoRef = mDatabase.child("LicenceDetails");
        demoRef1= mDatabase.child("UserData");
        addListenerOnNext();
    }

    private void addListenerOnNext(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text1.getText().toString().length() == 0) {
                    text1.setError("This field is required!");
                    text1.requestFocus();
                    return;
                }
                else if (text2.getText().toString().length() == 0) {
                    text2.setError("This field is required!");
                    text2.requestFocus();
                    return;
                }
                else if (text3.getText().toString().length() == 0) {
                    text3.setError("This field is required!");
                    text3.requestFocus();
                    return;
                }
                else{
                    String input1 = text1.getText().toString().toUpperCase();
                    String input2 = text2.getText().toString();
                    final String input = input1 + " " + input2;


                    final DatabaseReference demotemp,demotemp1,demotemp2;
                    demotemp=demoRef1.child(input).child("password");
                    demotemp1=demoRef1.child(input).child("adharno");
                   // demotemp2=demoRef1.child(input).child("phone");

                    demoRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(input)) {
                                // run some code
                                sharedPreferenceUtils.setString(SignupActivity.this,"lno",input);
                                demotemp.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String pas = dataSnapshot.getValue(String.class);

                                        if(pas.equals("not set")){

                                            demotemp1.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Long adhar = dataSnapshot.getValue(Long.class);
                                                    String aa = adhar.toString();
                                                    String adhar1 = text3.getText().toString();
                                                    if(aa.equals(adhar1)){


                                                        Intent i1 = new Intent(SignupActivity.this, OtpActivity.class);
                                                        startActivity(i1);

                                                    }
                                                    else{
                                                        Toast.makeText(SignupActivity.this,"Adhar number is not valid",Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });




                                        }
                                        else{
                                            Toast.makeText(SignupActivity.this,"You are already registered.",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });


                            }
                            else {
                                Toast.makeText(SignupActivity.this,"Licence number is not valid",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });

    }
}
