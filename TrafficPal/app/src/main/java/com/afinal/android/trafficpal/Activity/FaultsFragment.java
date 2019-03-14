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
import android.widget.Toast;

import com.afinal.android.trafficpal.R;


/**
 * Created by Ravi on 29/07/15.
 */
public class FaultsFragment extends Fragment {

    public FaultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faults, container, false);


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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

