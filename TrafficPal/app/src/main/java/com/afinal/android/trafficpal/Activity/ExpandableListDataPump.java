package com.afinal.android.trafficpal.Activity;

/**
 * Created by Tithi on 04-04-2018.
 */
import android.content.Context;
import android.content.Intent;

import com.afinal.android.trafficpal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Context context) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> q1 = new ArrayList<String>();
        q1.add(context.getString(R.string.faq_a1));


        List<String> q2 = new ArrayList<String>();
        q2.add(context.getString(R.string.faq_a2));


        List<String> q3 = new ArrayList<String>();
        q3.add(context.getString(R.string.faq_a3));

        List<String> q4 = new ArrayList<String>();
        q4.add(context.getString(R.string.faq_a4));




        expandableListDetail.put(context.getString(R.string.faq_q1), q1);
        expandableListDetail.put(context.getString(R.string.faq_q2), q2);
        expandableListDetail.put(context.getString(R.string.faq_q3), q3);
        expandableListDetail.put(context.getString(R.string.faq_q4), q4);
        return expandableListDetail;
    }
}