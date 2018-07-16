package com.kawsarkobir2018.ipl;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarnFragment extends Fragment implements RewardedVideoAdListener {


    public EarnFragment() {
        // Required empty public constructor
    }

    TextView emailTextView,todayTextView,timerTextView;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,appReference;
    UserInfo userInfo;
    Button pointButton4,pointButton1,pointButtonTest;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean timerBoolean = true;
    boolean runningFetching = true;
    InstallApp installApp;
    List<InstallApp> appArray = new ArrayList<>();
    ListView appListView;
    AppAdapter appAdapter;
    LinearLayout emptyView;
    ScrollView scroll;
    boolean isFinished = false;


    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd mInterstitialAd,mInterstitialAd1;
    private AdView mAdView;


    String appKey;


    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_earn, container, false);

        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        installApp = new InstallApp("af",2,1,"fds","fsd");

        appListView = (ListView) view.findViewById(R.id.app_installList);
        emptyView = (LinearLayout) view.findViewById(R.id.emptyView);
        //setListViewHeightBasedOnChildren(appListView);
        timerTextView = (TextView) view.findViewById(R.id.timerView);
        emailTextView = (TextView) view.findViewById(R.id.emailtextView);
        todayTextView = (TextView) view.findViewById(R.id.toDaytextView);
        pointButton4 = (Button) view.findViewById(R.id.pointtButton4);
        pointButton1 = (Button) view.findViewById(R.id.pointtButton1);
        pointButtonTest = (Button) view.findViewById(R.id.pointtButtonTest);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        scroll = (ScrollView) view.findViewById(R.id.scroll);


        //  FirebaseCrash.logcat(Log.ERROR, TAG, "Start Timing");
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Getting Data From Server...");
        dialog.setCancelable(false);
        dialog.show();
//        userInfo = new UserInfo();
//        installApp = new InstallApp("hfg",0001,"tfht","tbs",0);
//        appArray.add(installApp);
//        installApp = new InstallApp("hfg",0001,"tfhdgdgt","tbs",0);
//        appArray.add(installApp);
//        appAdapter = new AppAdapter(getActivity(),appArray,userInfo);
//        appListView.setAdapter(appAdapter);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        appReference = FirebaseDatabase.getInstance().getReference("apss");

        appListView.setFocusable(false);

//        Date dt = new Date();
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, 1);
//        dt = c.getTime();
//
//        if(dt-dt == 0)

        todayTextView.setVisibility(View.GONE);
        timerTextView.setVisibility(View.GONE);
//        pointButton1.setVisibility(View.GONE);
//        pointButton4.setVisibility(View.GONE);


        final CountDownTimer tim = new CountDownTimer(1000*15,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getActivity(),"Make sure your Internet is on.",Toast.LENGTH_SHORT).show();
                this.start();
            }
        }.start();




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.child("users").child(firebaseUser.getUid()).getValue(UserInfo.class);
                //  progressBar.setVisibility(View.GONE);
//                String paymetProof = new String();
//                if(userInfo.paymentProof == 0){
//                    paymetProof = "You didn't request for payment.";
//                }else if(userInfo.paymentProof == 1){
//                    paymetProof = "Request Pending.";
//                }else {
//                    paymetProof = "Paid.";
//                }

                emailTextView.setText("Name: "+userInfo.name+"\n"+"Point: "+userInfo.point+" Royal"+"\n"+"Ref Point: "+userInfo.refPoint+" Royal");


                todayTextView.setVisibility(View.VISIBLE);
                runningFetching = false;
//                pointButton1.setVisibility(View.VISIBLE);
//                pointButton4.setVisibility(View.VISIBLE);

                emailTextView.setText("Name: "+userInfo.name+"\n"+"Point: "+userInfo.point+" Royal"+"\n"+"Ref Point: "+userInfo.refPoint+" Royal");

                appArray.clear();
                for(DataSnapshot appSnapshot : dataSnapshot.child("apss").getChildren()){
                    installApp = appSnapshot.getValue(InstallApp.class);
                    int id = installApp.id;
                    ArrayList<Integer> viewArray = userInfo.viewdApps;
                    if(viewArray.size()!=0){
                        appArray.add(installApp);
                        for (int i = 0;i<viewArray.size();i++){
                            if(viewArray.get(i)==id){
                                appArray.remove(appArray.size()-1);
                                //FirebaseCrash.logcat(Log.ERROR, TAG, "Removing apps");
                                break;
                            }
                        }
                    }else {
                        // FirebaseCrash.logcat(Log.ERROR, TAG, "Adding apps");
                        appArray.add(installApp);
                    }

                }
                //  FirebaseCrash.logcat(Log.ERROR, TAG, "Befor adapter");

                dialog.dismiss();
                tim.cancel();
                isFinished = true;
                progressBar.setVisibility(View.GONE);

                if(getActivity()!=null){
                    appAdapter = new AppAdapter(getActivity(),appArray,userInfo);
                    appListView.setAdapter(appAdapter);
                }


                appListView.setFocusable(false);

//                scroll.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        scroll.scrollTo(0, scroll.getTop());
//                    }
//                });

                appListView.setFocusable(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final CountDownTimer tim1 = new CountDownTimer(1000*15,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinished){


                    Date lastDate = new Date(sharedPreferences.getLong("appTime",0));
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(lastDate);
                    cal.add(Calendar.DATE, 1);
                    lastDate = cal.getTime();
                    Date toDay = new Date(System.currentTimeMillis());
                    if(lastDate.equals(toDay) || lastDate.before(toDay)){
                        if(sharedPreferences.getInt("appPoint",0) > 0){
                            userInfo.point += sharedPreferences.getInt("appPoint",0);
                            userInfo.totalPoint += sharedPreferences.getInt("appPoint",0);
                            databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                            Toast.makeText(getActivity(),sharedPreferences.getInt("appPoint",0)+" Royal is added!",Toast.LENGTH_SHORT).show();
                            editor.putInt("appPoint",0);
                            editor.commit();
                        }
                    }



//                    Date lastDate1 = new Date(sharedPreferences.getLong("webTime",0));
//                    Calendar cal1 = Calendar.getInstance();
//                    cal1.setTime(lastDate1);
//                    cal1.add(Calendar.DATE, 1);
//                    lastDate1 = cal1.getTime();
//                    Date toDay1 = new Date(System.currentTimeMillis());
//                    if(lastDate1.equals(toDay1) || lastDate1.before(toDay1)){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                userInfo.point += 2;
//                                userInfo.totalPoint += 2;
//                                databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
//                                Toast.makeText(getActivity(),"You get 2 Royal Bonus!!",Toast.LENGTH_SHORT).show();
//                                Date date = new Date(System.currentTimeMillis());
//                                long time = date.getTime();
//                                editor.putLong("webTime",time);
//                                editor.commit();
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bdtechmaster.com/"));
//                                startActivity(intent);
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.setMessage("অনুগ্রহ করে আমাদের ওয়েবসাইট এর মধ্যে ২মিনিট ব্রাউজ করে বোনাস 2 Royal আয় করুন।");
//                        AlertDialog alertDialogWeb = builder.create();
//                        alertDialogWeb.setCancelable(false);
//                        alertDialogWeb.show();
//                        TextView textView = (TextView) alertDialogWeb.findViewById(android.R.id.message);
//                        textView.setTextSize(25);

                //    }


                    if (userInfo.wrongAttempt>=10){
                        final AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                        builde.setMessage("সম্মানিত গ্রাহক !!\n" +
                                "আপনার বারবার ভূলের কারণে আপনার একাউন্ট সাসপেন্ড করা হয়েছে।আপনার একাউন্ট আবার চালু করতে আমাদের ফেসবুক বা ইমেইল এ আপনার A/C দিয়ে যোগাযোগ করুন।\n" +
                                "Your A/C:-");
                        final EditText input = new EditText(getActivity());
                        input.setText(firebaseUser.getUid());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builde.setView(input);
                        AlertDialog alertDialog = builde.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                        textView.setTextSize(20);
                    }


                    this.cancel();
                }
            }

            @Override
            public void onFinish() {
                this.start();
            }
        }.start();



        int today = sharedPreferences.getInt("dPoint",0);
        int last = sharedPreferences.getInt("hPoint",0);
        todayTextView.setText("Earn Today: "+today+"/200 Royal\nEarn last: "+last+"/30+(10 Bonus) Royal");

        timerBoolean = sharedPreferences.getBoolean("bTimer",true);
        if (!timerBoolean) {
            pointButton4.setVisibility(View.GONE);
            pointButton1.setVisibility(View.GONE);
            if (sharedPreferences.getInt("whatTime", 0) == 0) {
                long pastTime = sharedPreferences.getLong("hTime", 0);
                Calendar c = Calendar.getInstance();
                Date date = c.getTime();
                long time = date.getTime();
                long finalTime = (pastTime + 7200*1000) - time;//1000*60*60*2
                timer(finalTime, sharedPreferences.getInt("dPoint", 0));
            } else {
                long pastTime = sharedPreferences.getLong("hTime", 0);
                Calendar c = Calendar.getInstance();
                Date date = c.getTime();
                long time = date.getTime();
                long finalTime = (pastTime + 18000000 ) - time;//64800000
                timer(finalTime, sharedPreferences.getInt("dPoint", 0));
            }
        }


//        timer(7200*1000,40+1);//1000*60*60
//        timerBoolean = false;
//        editor.putBoolean("bTimer",timerBoolean);
//        editor.commit();



//        appReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//               // FirebaseCrash.logcat(Log.ERROR, TAG, "After apps");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        pointButtonTest.setVisibility(View.GONE);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        MobileAds.initialize(getActivity(),"ca-app-pub-1176560929770024~9675395184");//ca-app-pub-9902826666790951~8542088289

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-1176560929770024/8685750719");//ca-app-pub-3940256099942544/1033173712
        mInterstitialAd1 = new InterstitialAd(getActivity());
        mInterstitialAd1.setAdUnitId("ca-app-pub-1176560929770024/1560959229");


        pointButton4.setEnabled(false);
        pointButton1.setEnabled(false);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());


//        appKey = "f942955b9e8c209e6dc759190846c456af8459a7629a504a";
//        Appodeal.disableLocationPermissionCheck();
//        Appodeal.setBannerViewId(R.id.appodealBannerView);
//        Appodeal.initialize(getActivity(), appKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
//        Appodeal.show(getActivity(), Appodeal.BANNER_VIEW);
//
//
//        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
//            @Override
//            public void onInterstitialLoaded(boolean isPrecache) {
//                Log.d("Appodeal", "onInterstitialLoaded");
//                pointButton1.setEnabled(true);
//            }
//            @Override
//            public void onInterstitialFailedToLoad() {
//                Log.d("Appodeal", "onInterstitialFailedToLoad");
//                //Appodeal.initialize(getActivity(), appKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
//            }
//            @Override
//            public void onInterstitialShown() {
//                Log.d("Appodeal", "onInterstitialShown");
//                pointButton1.setEnabled(false);
//                int nowh = sharedPreferences.getInt("hPoint",0);
//                int nowd = sharedPreferences.getInt("dPoint",0);
//                if(nowh <= 29 && nowd <= 199){
//                    userInfo.point += 1;
//                    userInfo.totalPoint += 1;
//                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
//                    todayTextView.setText("Earn Today: "+(nowd+1)+"/200 Royal\nEarn last: "+(nowh+1)+"/30+(10 Bonus) Royal");
//                    editor.putInt("hPoint",nowh+1);
//                    editor.putInt("dPoint",nowd+1);
//                    editor.commit();
//                    if(nowd == 199){
//                        Calendar c = Calendar.getInstance();
//                        Date date = c.getTime();
//                        c.set(Calendar.HOUR_OF_DAY,date.getHours());
//                        date = c.getTime();
//                        long time = date.getTime();
//                        editor.putLong("hTime",time);
//                        editor.putInt("whatTime",1);
//                        editor.commit();
//
//                        if(timerBoolean){
//                            timer(86400000,0);//64800000
//                            timerBoolean = false;
//                            editor.putBoolean("bTimer",timerBoolean);
//                            editor.commit();
//                        }
//                        Toast.makeText(getActivity(),"Blocked for 1 day.",Toast.LENGTH_SHORT).show();
//                        pointButton4.setVisibility(View.GONE);
//                        pointButton1.setVisibility(View.GONE);
//                    }else if (nowd>=195){
//                        if (nowd>=196){
//                            pointButton4.setVisibility(View.GONE);
//                        }
//                    }
//                    else if(nowh==29){
//                        Calendar c = Calendar.getInstance();
//                        Date date = c.getTime();
//                        long time = date.getTime();
//                        editor.putLong("hTime",time);
//                        editor.putInt("whatTime",0);
//                        editor.commit();
//
//                        if(timerBoolean){
//                            timer(7200*1000,nowd+1);//1000*60*60
//                            timerBoolean = false;
//                            editor.putBoolean("bTimer",timerBoolean);
//                            editor.commit();
//                        }
//                        pointButton4.setVisibility(View.GONE);
//                        pointButton1.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"Blocked for 2 hour.",Toast.LENGTH_SHORT).show();
//                    }else if (nowh>=25){
//                        if (nowh>=26){
//                            pointButton4.setVisibility(View.GONE);
//                        }
//                    }
//                }
//
//                if(sharedPreferences.getInt("dPoint",0)>=186){
//                    editor.putInt("adClick",1);
//                }else {
////                    if(sharedPreferences.getInt("adClick",0)==0){
////                        Toast.makeText(getActivity(), "বোনাস !!", Toast.LENGTH_SHORT).show();
////                    }else {
////                        Toast.makeText(getActivity(), "Don't Click", Toast.LENGTH_SHORT).show();
////                    }
//                }
//
//                Toast.makeText(getActivity(), "You earn 1 Royal!", Toast.LENGTH_SHORT).show();
//
//
//                //Appodeal.initialize(getActivity(), appKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
//
//
//            }
//            @Override
//            public void onInterstitialClicked() {
//                Log.d("Appodeal", "onInterstitialClicked");
//                if(sharedPreferences.getInt("adClick",0)==0){
//                    int nowh = sharedPreferences.getInt("hPoint",0);
//                    int nowd = sharedPreferences.getInt("dPoint",0);
//                    userInfo.point += 10;
//                    userInfo.totalPoint += 10;
//                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
//                    todayTextView.setText("Earn Today: "+(nowd+10)+"/200 Royal\nEarn last: "+(nowh)+"/30+(10 Bonus) Royal");
//                    editor.putInt("adClick",1);
//                    editor.putInt("dPoint",nowd+10);
//                    editor.commit();
//                    Toast.makeText(getActivity(), "You get 10 Royal Bonus!!", Toast.LENGTH_SHORT).show();
//                }else {
//                    userInfo.point -= 20;
//                    userInfo.totalPoint -= 20;
//                    userInfo.wrongAttempt++;
//                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
//                    if (userInfo.wrongAttempt>=10){
//                        final AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
//                        builde.setMessage("সম্মানিত গ্রাহক !!\n" +
//                                "আপনার বারবার ভূলের কারণে আপনার একাউন্ট সাসপেন্ড করা হয়েছে।আপনার একাউন্ট আবার চালু করতে আমাদের ফেসবুক বা ইমেইল এ আপনার A/C দিয়ে যোগাযোগ করুন।\n" +
//                                "Your A/C:-");
//                        final EditText input = new EditText(getActivity());
//                        input.setText(firebaseUser.getUid());
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                        input.setLayoutParams(lp);
//                        builde.setView(input);
//                        AlertDialog alertDialog = builde.create();
//                        alertDialog.setCancelable(false);
//                        alertDialog.show();
//                        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
//                        textView.setTextSize(20);
//                    }
//                    Toast.makeText(getActivity(), "You lost 20 Royal For Clicking the AD!!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//            @Override
//            public void onInterstitialClosed() {
//                Log.d("Appodeal", "onInterstitialClosed");
//                //Appodeal.initialize(getActivity(), appKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
//            }
//        });


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                pointButton1.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                //Toast.makeText(getActivity(),"Google Ads",Toast.LENGTH_SHORT);

                int nowh = sharedPreferences.getInt("hPoint",0);
                int nowd = sharedPreferences.getInt("dPoint",0);
                if(nowh <= 29 && nowd <= 199){
                    userInfo.point += 1;
                    userInfo.totalPoint += 1;
                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                    todayTextView.setText("Earn Today: "+(nowd+1)+"/200 Royal\nEarn last: "+(nowh+1)+"/30+(10 Bonus) Royal");
                    editor.putInt("hPoint",nowh+1);
                    editor.putInt("dPoint",nowd+1);
                    editor.commit();
                    if(nowd == 199){
                        Calendar c = Calendar.getInstance();
                        Date date = c.getTime();
                        c.set(Calendar.HOUR_OF_DAY,date.getHours());
                        date = c.getTime();
                        long time = date.getTime();
                        editor.putLong("hTime",time);
                        editor.putInt("whatTime",1);
                        editor.commit();

                        if(timerBoolean){
                            timer(18000000,0);//64800000
                            timerBoolean = false;
                            editor.putBoolean("bTimer",timerBoolean);
                            editor.commit();
                        }
                        Toast.makeText(getActivity(),"Blocked for 1 day.",Toast.LENGTH_SHORT).show();
                        pointButton4.setVisibility(View.GONE);
                        pointButton1.setVisibility(View.GONE);
                    }else if (nowd>=195){
                        if (nowd>=196){
                            //pointButton4.setVisibility(View.GONE);
                        }
                    }
                    else if(nowh==29){
                        Calendar c = Calendar.getInstance();
                        Date date = c.getTime();
                        long time = date.getTime();
                        editor.putLong("hTime",time);
                        editor.putInt("whatTime",0);
                        editor.commit();

                        if(timerBoolean){
                            timer(7200*1000,nowd+1);//1000*60*60
                            timerBoolean = false;
                            editor.putBoolean("bTimer",timerBoolean);
                            editor.commit();
                        }
                        pointButton4.setVisibility(View.GONE);
                        pointButton1.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Blocked for 2 hour.",Toast.LENGTH_SHORT).show();
                    }else if (nowh>=25){
                        if (nowh>=26){
                           // pointButton4.setVisibility(View.GONE);
                        }
                    }
                }

                if(sharedPreferences.getInt("dPoint",0)>=186){
                    editor.putInt("adClick",1);
                }else {
                    if(sharedPreferences.getInt("adClick",0)==0){
                        Toast.makeText(getActivity(),"Google Ads",Toast.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(getActivity(), "Don't Click", Toast.LENGTH_SHORT).show();
                    }
                }

                //Toast.makeText(getActivity(), "You earn 1 Royal!", Toast.LENGTH_SHORT).show();

                //Toast.makeText(getActivity(),"Google Ads",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                if(sharedPreferences.getInt("adClick",0)==0){
                    int nowh = sharedPreferences.getInt("hPoint",0);
                    int nowd = sharedPreferences.getInt("dPoint",0);
                    userInfo.point += 10;
                    userInfo.totalPoint += 10;
                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                    todayTextView.setText("Earn Today: "+(nowd+10)+"/200 Royal\nEarn last: "+(nowh)+"/30+(10 Bonus) Royal");
                    editor.putInt("adClick",1);
                    editor.putInt("dPoint",nowd+10);
                    editor.commit();
                    Toast.makeText(getActivity(), "আপনি ১০ Royal আয় করেছেন!!", Toast.LENGTH_SHORT).show();
                }else {
                    userInfo.point -= 20;
                    userInfo.totalPoint -= 20;
                    userInfo.wrongAttempt++;
                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                    if (userInfo.wrongAttempt>=10){
                        final AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                        builde.setMessage("সম্মানিত গ্রাহক !!\n" +
                                "আপনার বারবার ভূলের কারণে আপনার একাউন্ট সাসপেন্ড করা হয়েছে।আপনার একাউন্ট আবার চালু করতে আমাদের ফেসবুক বা ইমেইল এ আপনার A/C দিয়ে যোগাযোগ করুন।\n" +
                                "Your A/C:-");
                        final EditText input = new EditText(getActivity());
                        input.setText(firebaseUser.getUid());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builde.setView(input);
                        AlertDialog alertDialog = builde.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                        textView.setTextSize(20);
                    }
                    Toast.makeText(getActivity(), "ভূল কাজের জন্য -২০ Royal কাটা হল!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                pointButton1.setEnabled(false);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });



//        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
//            @Override
//            public void onRewardedVideoLoaded() {
//                Log.d("Appodeal", "onRewardedVideoLoaded");
//                pointButton4.setEnabled(true);
//            }
//            @Override
//            public void onRewardedVideoFailedToLoad() {
//                Log.d("Appodeal", "onRewardedVideoFailedToLoad");
//            }
//            @Override
//            public void onRewardedVideoShown() {
//                Log.d("Appodeal", "onRewardedVideoShown");
//                pointButton4.setEnabled(false);
//            }
//            @Override
//            public void onRewardedVideoFinished(int amount, String name) {
//                Log.d("Appodeal", "onRewardedVideoFinished");
//                int nowh = sharedPreferences.getInt("hPoint",0);
//                int nowd = sharedPreferences.getInt("dPoint",0);
//                if(nowh <= 26 && nowd <= 196){
//                    userInfo.point += 4;
//                    userInfo.totalPoint += 4;
//                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
//                    todayTextView.setText("Earn Today: "+(nowd+4)+"/200 Royal\nEarn last: "+(nowh+4)+"/30+(10 Bonus) Royal");
//                    editor.putInt("hPoint",nowh+4);
//                    editor.putInt("dPoint",nowd+4);
//                    editor.commit();
//                    if (nowd>=193&&nowd<196){
//                        pointButton4.setVisibility(View.GONE);
//                    } else if(nowd == 196){
//                        Calendar c = Calendar.getInstance();
//                        Date date = c.getTime();
//                        c.set(Calendar.HOUR_OF_DAY,date.getHours());
//                        long time = date.getTime();
//                        editor.putLong("hTime",time);
//                        editor.putInt("whatTime",1);
//                        editor.commit();
//
//                        if(timerBoolean){
//                            timer(86400000,0);//64800000
//                            timerBoolean = false;
//                            editor.putBoolean("bTimer",timerBoolean);
//                            editor.commit();
//                        }
//                        pointButton4.setVisibility(View.GONE);
//                        pointButton1.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"Blocked for 1 day.",Toast.LENGTH_SHORT).show();
//                    }else if(nowh==26){
//                        Calendar c = Calendar.getInstance();
//                        Date date = c.getTime();
//                        long time = date.getTime();
//                        editor.putLong("hTime",time);
//                        editor.putInt("whatTime",0);
//                        editor.commit();
//
//                        if(timerBoolean){
//                            timer(7200*1000,nowd+1);//1000*60*60
//                            timerBoolean = false;
//                            editor.putBoolean("bTimer",timerBoolean);
//                            editor.commit();
//                        }
//                        pointButton4.setVisibility(View.GONE);
//                        pointButton1.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"Blocked for 2 hour.",Toast.LENGTH_SHORT).show();
//                    }else if (nowh>=23){
//                        pointButton4.setVisibility(View.GONE);
//                    }
//                }else {
//                    pointButton4.setVisibility(View.GONE);
//                }
//
//                Toast.makeText(getActivity(), "You earn 4 Royal!", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onRewardedVideoClosed(boolean finished) {
//                Log.d("Appodeal", "onRewardedVideoClosed");
//                //pointButton4.setEnabled(false);
//            }
//        });

        mInterstitialAd1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                pointButton4.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.

                //pointButton4.setEnabled(false);




                int nowh = sharedPreferences.getInt("hPoint",0);
                int nowd = sharedPreferences.getInt("dPoint",0);
                if(nowh <= 29 && nowd <= 199){
                    userInfo.point += 1;
                    userInfo.totalPoint += 1;
                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                    todayTextView.setText("Earn Today: "+(nowd+1)+"/200 Royal\nEarn last: "+(nowh+1)+"/30+(10 Bonus) Royal");
                    editor.putInt("hPoint",nowh+1);
                    editor.putInt("dPoint",nowd+1);
                    editor.commit();
                    if(nowd == 199){
                        Calendar c = Calendar.getInstance();
                        Date date = c.getTime();
                        c.set(Calendar.HOUR_OF_DAY,date.getHours());
                        date = c.getTime();
                        long time = date.getTime();
                        editor.putLong("hTime",time);
                        editor.putInt("whatTime",1);
                        editor.commit();

                        if(timerBoolean){
                            timer(18000000,0);//64800000
                            timerBoolean = false;
                            editor.putBoolean("bTimer",timerBoolean);
                            editor.commit();
                        }
                        Toast.makeText(getActivity(),"Blocked for 1 day.",Toast.LENGTH_SHORT).show();
                        pointButton4.setVisibility(View.GONE);
                        pointButton1.setVisibility(View.GONE);
                    }else if (nowd>=195){
                        if (nowd>=196){
                            //pointButton4.setVisibility(View.GONE);
                        }
                    }
                    else if(nowh==29){
                        Calendar c = Calendar.getInstance();
                        Date date = c.getTime();
                        long time = date.getTime();
                        editor.putLong("hTime",time);
                        editor.putInt("whatTime",0);
                        editor.commit();

                        if(timerBoolean){
                            timer(7200*1000,nowd+1);//1000*60*60
                            timerBoolean = false;
                            editor.putBoolean("bTimer",timerBoolean);
                            editor.commit();
                        }
                        pointButton4.setVisibility(View.GONE);
                        pointButton1.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Blocked for 2 hour.",Toast.LENGTH_SHORT).show();
                    }else if (nowh>=25){
                        if (nowh>=26){
                            //pointButton4.setVisibility(View.GONE);
                        }
                    }
                }

                if(sharedPreferences.getInt("dPoint",0)>=186){
                    editor.putInt("adClick",1);
                }else {
                    if(sharedPreferences.getInt("adClick",0)==0){
                        Toast.makeText(getActivity(),"Google Ads",Toast.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(getActivity(), "Don't Click", Toast.LENGTH_SHORT).show();
                    }
                }

                //Toast.makeText(getActivity(), "You earn 1 Royal!", Toast.LENGTH_SHORT).show();





//                int nowh = sharedPreferences.getInt("hPoint",0);
//                int nowd = sharedPreferences.getInt("dPoint",0);
//                if(nowh <= 26 && nowd <= 196){
//                    userInfo.point += 4;
//                    userInfo.totalPoint += 4;
//                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
//                    todayTextView.setText("Earn Today: "+(nowd+4)+"/200 Royal\nEarn last: "+(nowh+4)+"/30+(10 Bonus) Royal");
//                    editor.putInt("hPoint",nowh+4);
//                    editor.putInt("dPoint",nowd+4);
//                    editor.commit();
//                    if (nowd>=193&&nowd<196){
//                        pointButton4.setVisibility(View.GONE);
//                    } else if(nowd == 196){
//                        Calendar c = Calendar.getInstance();
//                        Date date = c.getTime();
//                        c.set(Calendar.HOUR_OF_DAY,date.getHours());
//                        long time = date.getTime();
//                        editor.putLong("hTime",time);
//                        editor.putInt("whatTime",1);
//                        editor.commit();
//
//                        if(timerBoolean){
//                            timer(64800000,0);//64800000
//                            timerBoolean = false;
//                            editor.putBoolean("bTimer",timerBoolean);
//                            editor.commit();
//                        }
//                        pointButton4.setVisibility(View.GONE);
//                        pointButton1.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"Blocked for 1 day.",Toast.LENGTH_SHORT).show();
//                    }else if(nowh==26){
//                        Calendar c = Calendar.getInstance();
//                        Date date = c.getTime();
//                        long time = date.getTime();
//                        editor.putLong("hTime",time);
//                        editor.putInt("whatTime",0);
//                        editor.commit();
//
//                        if(timerBoolean){
//                            timer(7200*1000,nowd+1);//1000*60*60
//                            timerBoolean = false;
//                            editor.putBoolean("bTimer",timerBoolean);
//                            editor.commit();
//                        }
//                        pointButton4.setVisibility(View.GONE);
//                        pointButton1.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"Blocked for 2 hour.",Toast.LENGTH_SHORT).show();
//                    }else if (nowh>=23){
//                        pointButton4.setVisibility(View.GONE);
//                    }
//                }else {
//                    pointButton4.setVisibility(View.GONE);
//                }
//
//                Toast.makeText(getActivity(), "You earn 4 Royal!", Toast.LENGTH_SHORT).show();
//
//
//
//                if(sharedPreferences.getInt("dPoint",0)>=186){
//                    editor.putInt("adClick",1);
//                }else {
////                    if(sharedPreferences.getInt("adClick",0)==0){
////                        Toast.makeText(getActivity(), "বোনাস !!", Toast.LENGTH_SHORT).show();
////                    }else {
////                        Toast.makeText(getActivity(), "Don't Click", Toast.LENGTH_SHORT).show();
////                    }
//                }
//
//                //Toast.makeText(getActivity(), "You earn 1 Royal!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                if(sharedPreferences.getInt("adClick",0)==0){
                    int nowh = sharedPreferences.getInt("hPoint",0);
                    int nowd = sharedPreferences.getInt("dPoint",0);
                    userInfo.point += 10;
                    userInfo.totalPoint += 10;
                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                    todayTextView.setText("Earn Today: "+(nowd+10)+"/200 Royal\nEarn last: "+(nowh)+"/30+(10 Bonus) Royal");
                    editor.putInt("adClick",1);
                    editor.putInt("dPoint",nowd+10);
                    editor.commit();
                    Toast.makeText(getActivity(), "আপনি ১০ Royal আয় করেছেন!!", Toast.LENGTH_SHORT).show();
                }else {
                    userInfo.point -= 20;
                    userInfo.totalPoint -= 20;
                    userInfo.wrongAttempt++;
                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                    if (userInfo.wrongAttempt>=10){
                        final AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                        builde.setMessage("সম্মানিত গ্রাহক !!\n" +
                                "আপনার বারবার ভূলের কারণে আপনার একাউন্ট সাসপেন্ড করা হয়েছে।আপনার একাউন্ট আবার চালু করতে আমাদের ফেসবুক বা ইমেইল এ আপনার A/C দিয়ে যোগাযোগ করুন।\n" +
                                "Your A/C:-");
                        final EditText input = new EditText(getActivity());
                        input.setText(firebaseUser.getUid());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builde.setView(input);
                        AlertDialog alertDialog = builde.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                        textView.setTextSize(20);
                    }
                    Toast.makeText(getActivity(), "ভূল কাজের জন্য -২০ Royal কাটা হল!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                pointButton4.setEnabled(false);
                mInterstitialAd1.loadAd(new AdRequest.Builder().build());
            }
        });


        pointButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                }
            }
        });

        pointButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

//        }else {
//            timerTextView.setVisibility(View.GONE);
//            long pastTime = sharedPreferences.getLong("hTime",0);
//            Date date = new Date(System.currentTimeMillis());
//            long time = date.getTime();
//            long finalTime = pastTime-time;
//            if(finalTime <= 0){
//                editor.putInt("hPoint",0);
//                editor.commit();
//                if(sharedPreferences.getInt("whatTime",0)==1){
//                    editor.putInt("dPoint",0);
//                    editor.commit();
//                }
//                timerBoolean = true;
//                todayTextView.setText("Earn Today: "+sharedPreferences.getInt("dPoint",0)+"/200 Royal\nEarn last: "+sharedPreferences.getInt("hPoint",0)+"/50 Royal");
//                editor.putBoolean("bTimer",timerBoolean);
//                editor.commit();
//                pointButton4.setVisibility(View.VISIBLE);
//                pointButton1.setVisibility(View.VISIBLE);
//            }
//        }

        appListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InstallApp app = appArray.get(position);
                app.clicked += 1;
                int appid = app.id;
                databaseReference.child("apss").child(String.valueOf(appid)).setValue(app);
                userInfo.viewdApps.add(appid);
                appArray.remove(position);
                appAdapter.notifyDataSetChanged();
                final String appPackageName = app.appurl;
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                int now = sharedPreferences.getInt("appPoint",0);
                Date date = new Date(System.currentTimeMillis());
                long time = date.getTime();
                databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInfo);
                editor.putInt("appPoint",now+50);
                editor.putLong("appTime",time);
                editor.commit();
                //Toast.makeText(getActivity(),"সম্মানিত গ্রাহক  !!\n" +
                      //  "আপনি যদি অ্যাপ ইন্সটল করে ২৪ঘন্টা আপনার ফোনে রাখেন তাহলে আপনি 50 Royal পাবেন - তা না হলে Royal পাবেন না।",Toast.LENGTH_SHORT).show();
            }
        });

        appListView.setEmptyView(emptyView);

        appListView.setFocusable(false);

//        scroll.post(new Runnable() {
//            @Override
//            public void run() {
//                scroll.scrollTo(0, scroll.getTop());
//            }
//        });

        return view;
    }

//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

//    private void checkD(int i) {
//        int now = sharedPreferences.getInt("dPoint",0)+i;
//        if(now <= 200){
//            checkH(i,now);
//        }else {
//
//            Date date = new Date(System.currentTimeMillis());
//            long time = date.getTime();
//            editor.putLong("hTime",time);
//            editor.putInt("whatTime",1);
//            editor.commit();
//
//            if(timerBoolean){
//                timer(15000,0);
//                timerBoolean = false;
//                editor.putBoolean("bTimer",timerBoolean);
//                editor.commit();
//            }
//            Toast.makeText(getActivity(),"Blocked for 1 day.",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void checkH(int i,int j) {
//        int now = sharedPreferences.getInt("hPoint",0)+i;
//        if(now <= 50){
//            userInfo.point += i;
//            databaseReference.child(firebaseUser.getUid()).setValue(userInfo);
//            todayTextView.setText("Earn Today: "+j+"/200 Royal\nEarn last: "+now+"/50 Royal");
//            editor.putInt("hPoint",now);
//            editor.putInt("dPoint",j);
//            editor.commit();
//        }else {
//
//            Date date = new Date(System.currentTimeMillis());
//            long time = date.getTime();
//            editor.putLong("hTime",time);
//            editor.putInt("whatTime",0);
//            editor.commit();
//
//            if(timerBoolean){
//                timer(10000,j);
//                timerBoolean = false;
//                editor.putBoolean("bTimer",timerBoolean);
//                editor.commit();
//            }
//
//            Toast.makeText(getActivity(),"Blocked for 1 hour.\n"+now,Toast.LENGTH_SHORT).show();
//        }
//    }

    public void timer(long time, final int day){
        CountDownTimer timer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                if(sharedPreferences.getInt("whatTime",0)==1){
                    timerTextView.setText("Great work আপনি অনেক Royal আয় করেছেন। " +
                            "অনুগ্রহ করে " + sdf.format(new Date(millisUntilFinished)) + " Hours পর আবার চেস্টা করুন।");
                }else {
                    timerTextView.setText("Great work আপনি অনেক Royal আয় করেছেন। " +
                            "অনুগ্রহ করে "+ sdf.format(new Date(millisUntilFinished)) +" Hours পর আবার চেস্টা করুন।");
                }
            }

            @Override
            public void onFinish() {
                timerTextView.setVisibility(View.GONE);
                long pastTime = sharedPreferences.getLong("hTime",0);
                Date date = new Date(System.currentTimeMillis());
                long time = date.getTime();
                long finalTime = pastTime-time;
                if(finalTime <= 0){
                    editor.putInt("hPoint",0);
                    editor.commit();
                    if(sharedPreferences.getInt("whatTime",0)==1){
                        editor.putInt("dPoint",0);
                        editor.commit();
                    }
                    timerBoolean = true;
                    todayTextView.setText("Earn Today: "+sharedPreferences.getInt("dPoint",0)+"/200 Royal\nEarn last: "+0+"/30+(10 Bonus) Royal");
                    editor.putBoolean("bTimer",timerBoolean);
                    editor.putInt("adClick",0);
                    editor.commit();
                    pointButton4.setVisibility(View.VISIBLE);
                    pointButton1.setVisibility(View.VISIBLE);
                }
            }
        };
        timer.start();
    }





    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(getActivity());
        //Log.v("Destroy","Done");
        //onCreate(null);
        super.onDestroy();
        //onCreateView(inflater,container,savedInstanceState);

    }

    @Override
    public void onResume() {
        //Log.v("Resume","Done");
        //Appodeal.onResume(getActivity(), Appodeal.BANNER);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-1176560929770024/2123856576", new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        pointButtonTest.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        pointButtonTest.setVisibility(View.GONE);
    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAd();
    }
}
