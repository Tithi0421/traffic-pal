package com.afinal.android.trafficpal.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afinal.android.trafficpal.R;

public class SubmitChangesFragment extends Fragment {
    TextView vid,ofid,lno,date,vio;
    EditText changes;
    public SubmitChangesFragment() {
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
        View rootView= inflater.inflate(R.layout.fragment_submit_changes, container, false);
        final SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
        final String s_vid = sharedPreferenceUtils.getString(getActivity(), "sc_vid");
        final String s_ofid = sharedPreferenceUtils.getString(getActivity(), "sc_ofid");
        final String s_lno = sharedPreferenceUtils.getString(getActivity(), "sc_lno");
        final String s_date = sharedPreferenceUtils.getString(getActivity(), "sc_date");
        final String s_vio = sharedPreferenceUtils.getString(getActivity(), "sc_vio");

        vid = (TextView) rootView.findViewById(R.id.textView_vidvalue);
        ofid = (TextView) rootView.findViewById(R.id.textView_ofidvalue);
        lno = (TextView) rootView.findViewById(R.id.textView_lnovalue);
        date = (TextView) rootView.findViewById(R.id.textView_datevalue);
        vio = (TextView) rootView.findViewById(R.id.textView_viovalue);

        //auth = (TextView) rootView.findViewById(R.id.textView_aodvalue);

        vid.setText(s_vid);
        ofid.setText(s_ofid);
        lno.setText(s_lno);
        date.setText(s_date);
        vio.setText(s_vio);


        Button email = (Button) rootView.findViewById(R.id.post_message);
        changes=(EditText)rootView.findViewById(R.id.your_message);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s_changes   = changes.getText().toString();

                if (TextUtils.isEmpty(s_changes)){
                    changes.setError("Enter Your Message");
                    changes.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"trafficpal18@gmail.com"});
              //  sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                //sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                //        "name:"+name+'\n'+"Email ID:"+email+'\n'+"Message:"+'\n'+message);

                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT, "Violation id:"+s_vid+"\nOfficer id:"+s_ofid+"\nLicense Number:"+s_lno
                +"\nDate:"+s_date+"\nViolations:"+s_vio+'\n'+'\n'+"Changes: "+s_changes);

            /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));


            }
        });
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onResume() {
      // getActivity().getFragmentManager().popBackStack();
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
