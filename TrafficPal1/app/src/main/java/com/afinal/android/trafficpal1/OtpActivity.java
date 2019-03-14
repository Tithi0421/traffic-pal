package com.afinal.android.trafficpal1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtpActivity extends AppCompatActivity {

    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        submit = (Button) findViewById(R.id.submit);
        addListenerOnSubmit();

    }

    private void addListenerOnSubmit(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(OtpActivity.this, PasswordActivity.class);
                startActivity(i1);

            }
        });

    }
}
