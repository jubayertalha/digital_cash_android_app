package com.kawsarkobir2018.ipl;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {


    public TopFragment() {
        // Required empty public constructor
    }

    DatabaseReference allReference;
    List<UserInfo> userInfo = new ArrayList<UserInfo>();
    List<UserInfo> topTen = new ArrayList<UserInfo>();
    ListView topListView;
    TopAdapter topAdapter;
    TextView topInfoTextView,uPositionTextView,uNameTextView,uRoralTextView;
    int uPosition=0;
    FirebaseAuth auth;
    FirebaseUser user;
    UserInfo uf = new UserInfo();
    private AdView mAdView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        mAdView = view.findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        allReference = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        topListView = (ListView) view.findViewById(R.id.topListView);
        topInfoTextView = view.findViewById(R.id.topInfoTextView);
        uPositionTextView = view.findViewById(R.id.UtopPosition);
        uNameTextView = view.findViewById(R.id.UtopName);
        uRoralTextView = view.findViewById(R.id.UtopPoint);

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Downloading Top Earner List...");
        dialog.setCancelable(false);
        dialog.show();

//        String appKey = "f942955b9e8c209e6dc759190846c456af8459a7629a504a";
//        Appodeal.disableLocationPermissionCheck();
//        Appodeal.setBannerViewId(R.id.appodealBannerView3);
//        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
//        Appodeal.show(getActivity(), Appodeal.BANNER_VIEW);

        allReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    userInfo.add(dataSnapshot1.getValue(UserInfo.class));
                }

                Collections.sort(userInfo, new Comparator<UserInfo>() {
                    @Override
                    public int compare(UserInfo lhs, UserInfo rhs) {
                        return rhs.totalPoint.compareTo(lhs.totalPoint);
                    }
                });

                for(int i=0;i<userInfo.size();i++){
                    UserInfo us = userInfo.get(i);
                    if(us.email.equals(user.getEmail())){
                        uPosition = i+1;
                        uf = us;
                        break;
                    }
                }

                topInfoTextView.setText("Total Users: "+userInfo.size()+"\nYour Position:");
                uPositionTextView.setText(String.valueOf(uPosition));
                uNameTextView.setText(uf.name);
                uRoralTextView.setText(String.valueOf(uf.totalPoint)+" Royal");

                if(userInfo.size()>=100){
                    topTen.clear();
                    for (int i = 0; i < 100;i++){
                        topTen.add(userInfo.get(i));
                    }
                }else {
                    topTen = userInfo;
                }

                topAdapter = new TopAdapter(getActivity(),topTen,uf.email);

                topListView.setAdapter(topAdapter);

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        topAdapter = new TopAdapter(getActivity(),userInfo,"nai");

        topListView.setAdapter(topAdapter);

        topListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(uPosition-1 == position){
                    Toast.makeText(getActivity(),"Its You!!",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    UserInfo uo = userInfo.get(position);
                    builder.setTitle(String.valueOf(position+1)+". "+uo.name);
                    builder.setMessage(""+uo.email
                            +"\nTotal Point: "+String.valueOf(uo.totalPoint)+" Royal"
                            +"\nPoint: "+String.valueOf(uo.point)+" Royal"
                            +"\nRef Point: "+String.valueOf(uo.refPoint)+" Royal"
                            +"\nRefer by: "+String.valueOf(uo.refedPersons.size())+" Persons"
                            +"\nCash Out: "+String.valueOf(uo.paymentHistory.size())+" Times");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                    TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                    textView.setTextSize(20);
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        //Log.v("Resume","Done");
        //Appodeal.onResume(getActivity(), Appodeal.BANNER);
        super.onResume();
    }

}
