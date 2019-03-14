package com.afinal.android.trafficpal.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afinal.android.trafficpal.R;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import android.util.SparseArray;
import android.widget.Toast;

import info.androidhive.barcode.BarcodeReader;

public class ScanFragment extends Fragment implements BarcodeReader.BarcodeReaderListener{
    BarcodeReader barcodeReader;

    public ScanFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);

        barcodeReader= (BarcodeReader)getChildFragmentManager().findFragmentById(R.id.barcode_scanner);
        barcodeReader.setListener(this);
        return rootView;

    }

    @Override

    public void onScanned(final Barcode barcode) {

        //Log.e(TAG, "onScanned: " + barcode.displayValue);

        barcodeReader.playBeep();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                DatabaseReference demoRef= FirebaseDatabase.getInstance().getReference().child("LicenceDetails");
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(barcode.displayValue)) {
                            // run some code
                            SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance();
                            sharedPreferenceUtils.setString(getActivity(),"Lno",barcode.displayValue);
                            Fragment Scanning2Fragment = new Scanning2Fragment();
                            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.container_body, Scanning2Fragment);
                            fragmentTransaction.addToBackStack(null);

                            // Commit the transaction
                            fragmentTransaction.commit();
                        }
                        else {
                            Toast.makeText(getActivity(),"Licence number is not valid",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getActivity(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
