package com.afinal.android.trafficpal1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotActivity extends AppCompatActivity {

    private TextView text,text1;
    private Button reset;
    private Firebase mRef;
    final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
    private DatabaseReference mDatabase,demoRef,demoRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        text = (TextView) findViewById(R.id.id1);
        text1 = (TextView) findViewById(R.id.id2);
        reset = (Button)findViewById(R.id.reset);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://traffic-pal.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        demoRef = mDatabase.child("LicenceDetails");
        demoRef1= mDatabase.child("UserData");

        addListenerOnReset();
    }

    private void addListenerOnReset(){

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (text.getText().toString().length() == 0) {
                    text.setError("This field is required!");
                    text.requestFocus();
                    return;
                }
                else if (text1.getText().toString().length() == 0) {
                    text1.setError("This field is required!");
                    text1.requestFocus();
                    return;
                }

                else{
                    String input1 = text.getText().toString().toUpperCase();
                    String input2 = text1.getText().toString();
                    final String input = input1 + " " + input2;


                    demoRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(input)) {
                                // run some code
                                sharedPreferenceUtils.setString(ForgotActivity.this,"lno",input);

                                Intent i1 = new Intent(ForgotActivity.this,OtpActivity.class);
                                startActivity(i1);


                            }
                            else {
                                Toast.makeText(ForgotActivity.this,"Licence number is not valid",Toast.LENGTH_LONG).show();
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
