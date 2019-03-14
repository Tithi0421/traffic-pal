package com.afinal.android.trafficpal.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.afinal.android.trafficpal.R;


/**
 * Created by Dhiren on 29/07/15.
 */
public class Scanning3Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button pre,next;
    private Spinner type;
    private String item,item1 = "";
    private int total=0;
    final SharedPreferenceUtils sharedPreferenceUtils=SharedPreferenceUtils.getInstance();
    private static final String[]paths = {"MC 50CC", "MCWG", "MCWOG", "LMV", "LMV-NT", "LMV-TR", "HMV", "HPMV", "HTV", "TRAILER"};
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21;
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21;

    public Scanning3Fragment() {
        // Required empty public constructor
    }

 /*   public static Scanning3Fragment newInstance(int page, String title) {
        Scanning3Fragment scanning3fragment = new Scanning3Fragment();
        Bundle args = new Bundle();
        args.putInt("3", page);
        args.putString("Title3", title);
        scanning3fragment.setArguments(args);
        return scanning3fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scanning3, container, false);

        type= (Spinner) rootView.findViewById(R.id.Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

        t1 = (TextView) rootView.findViewById(R.id.vcheck1);
        t2 = (TextView) rootView.findViewById(R.id.vcheck2);
        t3 = (TextView) rootView.findViewById(R.id.vcheck3);
        t4 = (TextView) rootView.findViewById(R.id.vcheck4);
        t5 = (TextView) rootView.findViewById(R.id.vcheck5);
        t6 = (TextView) rootView.findViewById(R.id.vcheck6);
        t7 = (TextView) rootView.findViewById(R.id.vcheck7);
        t8 = (TextView) rootView.findViewById(R.id.vcheck8);
        t9 = (TextView) rootView.findViewById(R.id.vcheck9);
        t10 = (TextView) rootView.findViewById(R.id.vcheck10);
        t11 = (TextView) rootView.findViewById(R.id.vcheck11);
        t12 = (TextView) rootView.findViewById(R.id.vcheck12);
        t13 = (TextView) rootView.findViewById(R.id.vcheck13);
        t14 = (TextView) rootView.findViewById(R.id.vcheck14);
        t15 = (TextView) rootView.findViewById(R.id.vcheck15);
        t16 = (TextView) rootView.findViewById(R.id.vcheck16);
        t17 = (TextView) rootView.findViewById(R.id.vcheck17);
        t18 = (TextView) rootView.findViewById(R.id.vcheck18);
        t19 = (TextView) rootView.findViewById(R.id.vcheck19);
        t20 = (TextView) rootView.findViewById(R.id.vcheck20);
        t21 = (TextView) rootView.findViewById(R.id.vcheck21);

        c1 = (CheckBox) rootView.findViewById(R.id.s1);
        c2 = (CheckBox) rootView.findViewById(R.id.s2);
        c3 = (CheckBox) rootView.findViewById(R.id.s3);
        c4 = (CheckBox) rootView.findViewById(R.id.s4);
        c5 = (CheckBox) rootView.findViewById(R.id.s5);
        c6 = (CheckBox) rootView.findViewById(R.id.s6);
        c7 = (CheckBox) rootView.findViewById(R.id.s7);
        c8 = (CheckBox) rootView.findViewById(R.id.s8);
        c9 = (CheckBox) rootView.findViewById(R.id.s9);
        c10 = (CheckBox) rootView.findViewById(R.id.s10);
        c11 = (CheckBox) rootView.findViewById(R.id.s11);
        c12 = (CheckBox) rootView.findViewById(R.id.s12);
        c13 = (CheckBox) rootView.findViewById(R.id.s13);
        c14 = (CheckBox) rootView.findViewById(R.id.s14);
        c15 = (CheckBox) rootView.findViewById(R.id.s15);
        c16 = (CheckBox) rootView.findViewById(R.id.s16);
        c17 = (CheckBox) rootView.findViewById(R.id.s17);
        c18 = (CheckBox) rootView.findViewById(R.id.s18);
        c19 = (CheckBox) rootView.findViewById(R.id.s19);
        c20 = (CheckBox) rootView.findViewById(R.id.s20);
        c21 = (CheckBox) rootView.findViewById(R.id.s21);

        pre = (Button) rootView.findViewById(R.id.pre2);
        next = (Button) rootView.findViewById(R.id.next2);



        addListnerOnNext();
        addListnerOnPrevious();


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
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        item = parent.getItemAtPosition(position).toString();
        //    Toast.makeText(getActivity(),item,Toast.LENGTH_LONG).show();
        //sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        sharedPreferenceUtils.setString(getActivity(),"vehicle",item);
    }


    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public  void  addListnerOnNext(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        item1="";
        total=0;
                if (c1.isChecked()){
                    total = total + 2;
                    item1 = item1 + t1.getText().toString() + "." + " ";
                }
                if (c2.isChecked()){
                    total = total + 2;
                    item1 = item1 + t2.getText().toString() + "." + " ";
                }
                if (c3.isChecked()){
                    total = total + 1;
                    item1 = item1 + t3.getText().toString() + "." + " ";
                }
                if (c4.isChecked()){
                    total = total + 1;
                    item1 = item1 + t4.getText().toString() + "." + " ";
                }
                if (c5.isChecked()){
                    total = total + 2;
                    item1 = item1 + t5.getText().toString() + "." + " ";
                }
                if (c6.isChecked()){
                    total = total + 1;
                    item1 = item1 + t6.getText().toString() + "." + " ";
                }
                if (c7.isChecked()){
                    total = total + 1;
                    item1 = item1 + t7.getText().toString() + "." + " ";
                }
                if (c8.isChecked()){
                    total = total + 2;
                    item1 = item1 + t8.getText().toString() + "." + " ";
                }
                if (c9.isChecked()){
                    total = total + 2;
                    item1 = item1 + t9.getText().toString() + "." + " ";
                }
                if (c10.isChecked()){
                    total = total + 2;
                    item1 = item1 + t10.getText().toString() + "." + " ";
                }
                if (c11.isChecked()){
                    total = total + 2;
                    item1 = item1 + t11.getText().toString() + "." + " ";
                }
                if (c12.isChecked()){
                    total = total + 2;
                    item1 = item1 + t12.getText().toString() + "." + " ";
                }
                if (c13.isChecked()){
                    total = total + 2;
                    item1 = item1 + t13.getText().toString() + "." + " ";
                }
                if (c14.isChecked()){
                    total = total + 1;
                    item1 = item1 + t14.getText().toString() + "." + " ";
                }
                if (c15.isChecked()){
                    total = total + 1;
                    item1 = item1 + t15.getText().toString() + "." + " ";
                }
                if (c16.isChecked()){
                    total = total + 1;
                    item1 = item1 + t16.getText().toString() + "." + " ";
                }
                if (c17.isChecked()){
                    total = total + 1;
                    item1 = item1 + t17.getText().toString() + "." + " ";
                }
                if (c18.isChecked()){
                    total = total + 2;
                    item1 = item1 + t18.getText().toString() + "." + " ";
                }
                if (c19.isChecked()){
                    total = total + 1;
                    item1 = item1 + t19.getText().toString() + "." + " ";
                }
                if (c20.isChecked()){
                    total = total + 1;
                    item1 = item1 + t20.getText().toString() + "." + " ";
                }
                if (c21.isChecked()){
                    total = total + 15;
                    item1 = item1 + t21.getText().toString() + "." + " ";
                }
              //  sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
              /* SharedPreferences preferences=getContext().getSharedPreferences("MyPref",getContext().MODE_PRIVATE);
               SharedPreferences.Editor editor=preferences.edit();
                editor.remove("key3").apply();*/
                sharedPreferenceUtils.setString(getActivity(), "key3", item1);
                sharedPreferenceUtils.setLong(getActivity(), "key4",total);

                //    ((ViewPagerActivity)getActivity()).setCurrentItem(true);
                Fragment Scanning4Fragment = new Scanning4Fragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container_body, Scanning4Fragment);
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();

            }
        });
    }

    public void addListnerOnPrevious(){
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   ((ViewPagerActivity)getActivity()).setCurrentItem1(true);
            /*   Fragment Scanning2Fragment = new Scanning2Fragment();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container_body, Scanning2Fragment);
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit(); */
                //  pre.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                getActivity().onBackPressed();
            }

        });
    }



}

