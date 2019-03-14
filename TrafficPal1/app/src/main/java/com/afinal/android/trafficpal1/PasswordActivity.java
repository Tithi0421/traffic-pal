package com.afinal.android.trafficpal1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordActivity extends AppCompatActivity {

    private EditText pass,conf;
    private Button submit;
    private Firebase mRef;
    final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
    private DatabaseReference mDatabase,demoRef,demoRef1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        final String temp = sharedPreferenceUtils.getString(PasswordActivity.this, "lno");
        pass = (EditText) findViewById(R.id.pass);
        conf = (EditText) findViewById(R.id.conf);
        submit = (Button) findViewById(R.id.submit1);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://traffic-pal.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        demoRef = mDatabase.child("LicenceDetails");
        demoRef1= mDatabase.child("UserData").child(temp);
        addListenerOnSubmit();
    }

    private void addListenerOnSubmit(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pass.getText().toString().length() == 0){
                    pass.setError("This field is required!");
                    pass.requestFocus();
                    return;
                }
                if( conf.getText().toString().length() == 0 ) {
                    conf.setError("This field is required!");
                    conf.requestFocus();
                    return;
                }
                else  {
                    if (!pass.getText().toString().equals(conf.getText().toString())) {
                        pass.setError("Password does not match");
                        pass.requestFocus();
                        return;
                    }
                    else {
                        String password = pass.getText().toString();
                        demoRef1.child("password").setValue(password);
                        Intent i1 = new Intent(PasswordActivity.this, ViewPagerActivity.class);
                        startActivity(i1);

                    }

                }



            }
        });

    }
}
