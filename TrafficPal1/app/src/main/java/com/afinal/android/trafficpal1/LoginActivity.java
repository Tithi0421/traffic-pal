package com.afinal.android.trafficpal1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText text1,text2,text3;
    private CheckBox check;
    private Firebase mRef;
    private TextView sign,click;
    final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
    private DatabaseReference mDatabase,demoRef,demoRef1;
    private Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String id = sharedPreferenceUtils.getString(this, "lno");

        if (id != null) {
            Intent i1 = new Intent(LoginActivity.this, ViewPagerActivity.class);
            startActivity(i1);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text1 = (EditText) findViewById(R.id.id1);
        text2 = (EditText) findViewById(R.id.id2);
        text3 = (EditText) findViewById(R.id.id3);
        check = (CheckBox) findViewById(R.id.check);
        sign = (TextView) findViewById(R.id.signup);
        click = (TextView) findViewById(R.id.click);
        signin = (Button)findViewById(R.id.signin);

        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://traffic-pal.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        demoRef = mDatabase.child("LicenceDetails");
        demoRef1= mDatabase.child("UserData");
        addListenerOnSignUp();
        addListenerOnCheckBox();
        addListenerOnSign();
        addListenerOnClick();

    }

    private  void addListenerOnCheckBox(){
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(!ischecked){
                    text3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    text3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    private void addListenerOnSignUp(){
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i1);

            }
        });

    }

    private void addListenerOnClick(){
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(i1);

            }
        });

    }

    public  void addListenerOnSign(){
        signin.setOnClickListener(new View.OnClickListener() {
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

                 /*   demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(input)) {


                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Licence number is not valid",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }); */

                    final DatabaseReference demotemp;
                    demotemp=demoRef1.child(input).child("password");

                    sharedPreferenceUtils.setString(LoginActivity.this,"Lno",input);

                    demoRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(input)) {
                                // run some code
                                sharedPreferenceUtils.setString(LoginActivity.this,"lno",input);
                                demotemp.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String pas = dataSnapshot.getValue(String.class);
                                        String pas1 = text3.getText().toString();
                                        if(pas.equals(pas1)){
                                            sharedPreferenceUtils.setString(LoginActivity.this,"pas",pas);
                                            Intent i1 = new Intent(LoginActivity.this, ViewPagerActivity.class);
                                            startActivity(i1);
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this,"password is not valid",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });



                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Licence number is not valid",Toast.LENGTH_LONG).show();
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
