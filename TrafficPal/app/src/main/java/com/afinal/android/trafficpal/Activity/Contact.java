package com.afinal.android.trafficpal.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afinal.android.trafficpal.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Contact extends Fragment {
EditText your_name,your_email,your_subject,your_message;

    public Contact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_contact, container, false);

        your_name = (EditText) rootView.findViewById(R.id.your_name);
        //your_email = (EditText) rootView.findViewById(R.id.your_email);
        your_subject = (EditText) rootView.findViewById(R.id.your_subject);
        your_message = (EditText) rootView.findViewById(R.id.your_message);

        Button email = (Button) rootView.findViewById(R.id.post_message);


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name      = your_name.getText().toString();
               // String email     = your_email.getText().toString();
                String subject   = your_subject.getText().toString();
                String message   = your_message.getText().toString();
                if (TextUtils.isEmpty(name)){
                    your_name.setError("Enter Your Name");
                    your_name.requestFocus();
                    return;
                }

                Boolean onError = false;
                /*if (!isValidEmail(email)) {
                    onError = true;
                    your_email.setError("Invalid Email");
                    return;
                }*/

                if (TextUtils.isEmpty(subject)){
                    your_subject.setError("Enter Your Subject");
                    your_subject.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)){
                    your_message.setError("Enter Your Message");
                    your_message.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"trafficpal18@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                //sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                //        "name:"+name+'\n'+"Email ID:"+email+'\n'+"Message:"+'\n'+message);

                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT, "Name:"+name+'\n' + message);

            /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));


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
    public void onResume() {
        super.onResume();
        your_name.setText("");
      //  your_email.setText("");
        your_subject.setText("");
        your_message.setText("");
        //Get a Tracker (should auto-report)
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
