package com.afinal.android.trafficpal.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afinal.android.trafficpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    long value;
    private Button button1;
    private TextView click;
    private EditText text, text1;
    private CheckBox check;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase db;
    private DbQueries dbQueries;
    private FirebaseAuth fbAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String str,pass;
    public String currentUserUid;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        String id = sharedPreferenceUtils.getString(this, "key");

        if (id != null) {
            Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i1);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        click = (TextView) findViewById(R.id.click);
        text = (EditText) findViewById(R.id.username);
        text1 = (EditText) findViewById(R.id.password);
        button1 = (Button) findViewById(R.id.signin);
        check = (CheckBox) findViewById(R.id.check);


        FirebaseApp.initializeApp(this);
        fbAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("police");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LOG_Login", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("LOG_Login", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        addListenerOnButton();
        addListenerOnCheckBox();
        addListenerOnClick();
    }

    private void addListenerOnButton() {

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str = text.getText().toString();
                pass = text1.getText().toString();

              /*  dbQueries = new DbQueries(LoginActivity.this);
                value = dbQueries.validateUser(str,pass);
                if(value == -1){
                    Toast temp = Toast.makeText(LoginActivity.this, "invalid username or password", Toast.LENGTH_SHORT);
                    temp.show();

                }
                else {
                    SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                    sharedPreferenceUtils.setLong(LoginActivity.this,"key",value);
                    text.setText("");
                    text1.setText("");
                    Intent t2 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(t2);

                }*/
                if (str.equals("") || str.length()==0 || pass.equals("") || pass.length()==0 ) {
                    //Toast temp1 = Toast.makeText(LoginActivity.this, "Enter Email id and password", Toast.LENGTH_SHORT);
                    Snackbar snackbar = Snackbar.make(button1, "Enter Email and Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    fbAuth.signInWithEmailAndPassword(str,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("LOG_Login", "signInWithEmail:onComplete:" + task.isSuccessful());


                            if (!task.isSuccessful()){
                                Log.w("LOG_Login", "signInWithEmail", task.getException());
                                //   Snackbar.make(button1, "Emailid or Password is Invalid", Snackbar.LENGTH_LONG).show();
                                Toast temp = Toast.makeText(LoginActivity.this, "invalid username or password", Toast.LENGTH_SHORT);
                                temp.show();

                                text.setText("");
                                text1.setText("");
                            }
                            else {
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                String currentUserUid = user.getUid().toString();
                                //  String currentUserUid = "abc";
                                SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                sharedPreferenceUtils.setString(LoginActivity.this, "key", currentUserUid);
                                Log.d(currentUserUid, " ");
                                Intent i1 = new Intent(LoginActivity.this, MainActivity.class);

                                startActivity(i1);
                                text.setText("");
                                text1.setText("");
                            }
                        }
                    });
                }



            }
        });

    }
    private  void addListenerOnCheckBox(){
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(!ischecked){
                    text1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    text1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    private void addListenerOnClick(){

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i2 = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(i2);

            }
        });
    }

}
