package com.kawsarkobir2018.ipl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HowToFragment extends Fragment {


    public HowToFragment() {
        // Required empty public constructor
    }

    private AdView mAdView;
    TextView htw ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_how_to, container, false);

        htw = view.findViewById(R.id.guide);

        mAdView = view.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        String appKey = "f942955b9e8c209e6dc759190846c456af8459a7629a504a";
//        Appodeal.disableLocationPermissionCheck();
//        Appodeal.setBannerViewId(R.id.appodealBannerView1);
//        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
//        Appodeal.show(getActivity(), Appodeal.BANNER_VIEW);


        htw.setText("আপনি কিভাবে কাজ করবেন - নিচের লিখাগুলো একটু সময় নিয়ে মনোযোগ সহকারে পড়ুন.....!! \n" +
                " \n" +
                "★★ আমাদের বিশেষ অনুরুধ.....\n" +
                "\n" +
                "*সবকিছু ভালোভাবে বুঝার জন্য আমাদের ফেসবুক গুরুফে জয়েন করুন।\n" +
                "বিস্তারিত ফেসবুক গুরুফের  পিন পোস্টে দেওয়া আছে। এই পোস্টে এর নিয়ম - কানুন মেনে কাজ করতে হবে।\n" +
                "**এটা বাধ্যতামূলক\n" +
                "\n" +
                "*এখানে সংক্ষিপ্ত ভাবে উল্লেখ্য করা হল । \n" +
                "\n" +
                "★★Free Earn !! \n" +
                "\n" +
                "আপনি যখনি Free Earn বাটনে এ ক্লিক করবেন তখন ২টি 1 Royal বাটন দেখতে পারবেন তারপর এই বাটনগুলোতে ক্লিক করে অ্যাড দেখে দেখে Royal উপার্জন করতে পারবেন এবং তা আপনার মূল অ্যাকাউন্টে Royal হিসাবে Add হবে।\n" +
                "\n" +
                "\n" +
                "*আমি দিনে কত Royal আয় করতে পারব Free Eran থেকে ??\n" +
                "*Free Earn থেকে আপনি প্রতিদিন ২০০ Royal আয় করতে পারবেন।\n" +
                "তবে শর্ত হল প্রত্যেক ২ঘন্টা পর পর ৪০ Royal আয় করতে হবে.....\n" +
                "আরো কিছু নিয়ম আছে তা বিস্তারিত ফেসবুক গুরুফে !! \n" +
                "\n" +
                "★★Apps install To Earn !! \n" +
                "\n" +
                " আপনি অ্যাপ ইন্সটল করে প্রতিদিন আনলিমিটেড Royal আয় করতে পারবেন। যখন কোন Apps advertisement আসবে তখনই আপনি Apps Install অবশনে দেখতে পারবেন - এবং app install করে Royal আয় করতে পারবেন ★শর্ত হল Apps install করে ২৪ ঘন্টা ফোনে রাখতে হবে তাহলে আপনি Royal পাবেন।\n" +
                "\n" +
                "★★Referral to Eran !! \n" +
                "\n" +
                "*Referral অর্থ হল আমন্ত্রণ করা - রেফার করার দ্বারা অতিরিক্ত Royal আয় করা যাবে। প্রতি Referral দ্বারা আপনাকে ২০০ Royal অতিরিক্ত বোনাস প্রদান করা হবে। যদি কোন ব্যক্তি আপনার Referral\u200B ID তার রেফার বক্সে Submit করে তাহলে আপনি পাবেন ২০০ Royal এবং যিনি Referral Code Submit করবেন তিনি ৫০ Royal বোনাস পাবেন।\n" +
                "*নোট: - আপনে যদি স্প্যাম বা বিভ্রান্তিকর Referral করার চেষ্টা করেন তাহলে আপনার অ্যাকাউন্ট অবিলম্বে স্থগিত করা হবে। আমাদের সার্ভার অটো Check করে।\n" +
                "\n" +
                "★★ How To active Suspended Account !!\n" +
                "\n" +
                "* অ্যাকাউন্টে স্থগিত হলে কি করব ??\n" +
                "একবার আপনার অ্যাকাউন্ট সাসপেন্ড হয়ে গেলে, আমাদের ফেসবুক গুরুফে আপনার সব তথ্য দিয়ে পোস্ট করুন আপনার নাম,আপনার মোবাইল নাম্বার,\n" +
                "এবং অ্যাকাউন্ট আইডি- AC-ID.\n" +
                "\n" +
                "★★ How To Safe My Account !! \n" +
                "\n" +
                "*কি কি কারণে আপনার একাউন্ট স্থগিত হতে পারে ??\n" +
                "*Digital Cash এর কাজ করার প্রতিটি পদ্ধতি ধাপে ধাপে  নিয়ম কানুন দেওয়া আছে তা মানতে হবে এবং ফেসবুক গুরুফের পিন পোস্ট অনুযায়ী কাজ করতে হবে তা না হলে একাউন্ট \n" +
                "ডিজেবল হবে !!\n" +
                "এবং স্প্যামিং বা প্রতারণা করলে কোন\n" +
                "ধরনের সতর্কতা ছাড়া ব্যান করা হবে। এবং আপনি যদি Apps install করে ২৪ঘন্টা না রাখেন তাহলেও একাউন্ট স্থগিত করা হবে\n" +
                "* বিস্তারিত ফেসবুক গুরুফে।\n" +
                "\n" +
                "★★ How To Withdraw Money !! \n" +
                "\n" +
                "*মূল্যপরিশোধ পদ্ধতি.\n" +
                "(1) bKash/বিকাশ\n" +
                "(2) Rocket/রকেট\n" +
                "(3)Moblie Recharge (BD)/রির্চাজ\n" +
                "(4) PayPal/পেপাল\n" +
                "(5) Payza/পেইজা\n" +
                "\n" +
                "*কত Royal হলে পেমেন্ট এর জন্য রিকুয়েস্ট করা যাবে  ??\n" +
                "আপনার কাছে যদি সর্বনিম্ন ৩০০০ Royal থাকে তাহলে আপনি পেমেন্টের এর জন্য অনুরোধ করতে পারেন।\n" +
                "\n" +
                "* ১০০০ Royal এর দাম কত ?? \n" +
                "আপনি ১০০০ Royal এ ৪০ টাকা থেকে ১০০ টাকা পেতে পারেন।\n" +
                "* 1000 Royal = $0.4 - $2।\n" +
                "কাজের উপর নির্ভর করে।\n" +
                "\n" +
                "★★ How To Request Withdraw !! \n" +
                "\n" +
                "*কিভাবে পেমেন্ট এর জন্য অনুরুধ করব...??\n" +
                "Digital Cash মেনুতে যান এবং Withdraw Payment বাটনে ক্লিক করে তারপরে ৫টি মাধ্যম থেকে যে কোন একটি মাধ্যম নিবার্চন করুন এবং আপনার পাসওয়ার্ড টি বসিয়ে সঠিকভাবে \"অনুরোধ করুন।\n" +
                "* মনে রাখবেন পেমেন্ট এর জন্য সার্ভিস চার্জ্য প্রযোজ্য হবে।\n" +
                "\n" +
                "** পেমেন্ট রিকুয়েস্ট করার সময় নাম্বার/Email অথবা Method ভূল হলে কি করব... ?? \n" +
                "* Payment Pending থাকা অবস্থায় নাম্বার/Email এবং Method পরিবর্তন করতে পারবেন - নাম্বার/Email বা Method এর উপর ডাবল ক্লিক করে পরিবর্তন করুন।\n" +
                "\n" +
                "* কত তারিখ পেমেন্ট রিকুয়েস্ট করতে পারব ?? *\n" +
                "আপনি প্রতি মাসে একবার পেমেন্ট রিকুয়েস্ট করতে পারবেন ✈*\n" +
                "(১) মাসের শুরুতেই ১ তারিখ থেকে ৭ তারিখ পর্যন্ত। \n" +
                "(*) তারিখ পরিবর্তন হতে পারে। কতৃপক্ষের সিদ্ধান্ত চূড়ান্ত। \n" +
                "বি: দ্র:- পেমেন্ট হাতে পাবেন - রিকুয়েস্ট করার ১২০ ঘন্টার ভিতর। \n" +
                "\n" +
                "N.B:- all day payment request  option Free coming soon !!\n" +
                "\n" +
                "★★ Forget Password  !!\n" +
                "\n" +
                "* পাসওয়ার্ড ভূলেগেলে কি করব?? \n" +
                "যদি আপনি আপনার পাসওয়ার্ড ভুলে যান তাহলে আপনার একাউন্ট আইডি টি কপি করে আমাদের ফেসবুকে গুরুফে পোস্ট করুন,সমাধান করে দেওয়া হবে ২৪ঘন্টার ভিতর। মনে রাখবেন নতুন পাসওয়ার্ড এর জন্য আপনার কোন Royal চার্জ হবে না - শুধুমাত্র সব Details ভালোভাবে দিতে হবে।\n" +
                "\n" +
                "★★Our Teams and Condition !! \n" +
                "\n" +
                "*টাকা পাওয়ার জন্য পাচঁটি শর্ত *\n" +
                "(১) আমাদের ফেসবুক গুরুফে জয়েন হতে হবে...!! \n" +
                "(২) ফেসবুক গুরুফের পিন পোষ্ট অনুযায়ী কাজ করতে হবে...!!\n" +
                "(৩) ডিজিট ক্যাশ অ্যাপ কে ফেসবুক অথবা যে কোন Social Media তে বেশী বেশী শেয়ার করতে হবে..!!\n" +
                "(৪) আমাদের অ্যাপ কে ৫* স্টার রেটিং দিতে হবে...!!\n" +
                "(৫) ডিজিটাল ক্যাশ এর প্রতিটি নিয়ম মেনে কাজ করতে হবে...!!\n" +
                "\n" +
                "Digital Cash কে কোন রকম স্প্যাম বা বিদ্ভ্রান্তি করার চেস্টা করলে অথবা ©Copyright করার চেস্টা করলে ডিজিটাল ক্যাশ সরকারি আইনের সহায়তা নিতে বাধ্য থাকবে। অথবা যে কোন আইনের সহায়তা নিতে পারে।\n" +
                "Digital Cash কতৃপক্ষের সিদ্ধান্ত চূড়ান্ত। কতৃপক্ষ যেকোনো সময় তাদের সিদ্ধান্ত পরিবর্তন করতে পারে।\n" +
                "\n" +
                "ইউটিউবে আমাদের অ্যাপ কে হ্যাকিং বা অন্য কোন স্প্যাম করার ভিডিও আপলোড করলে সাথে সাথে Video কে রিমুব [©Copyright] করার জন্য ইউটিউব কতৃপক্ষের সাহায্য নেওয়া হবে।\n" +
                "\n" +
                "♥ ধন্যবাদ ♥\n" +
                "ডিজিটাল ক্যাশ\n" +
                "বিশ্বস্ততার প্রতিক।");

//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                Toast.makeText(getActivity(),"AdLoaded",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                Toast.makeText(getActivity(),"AdFailedToLoad",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//                Toast.makeText(getActivity(),"AdOpened",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//                Toast.makeText(getActivity(),"AdLeftApplication",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the user is about to return
//                // to the app after tapping on an ad.
//                Toast.makeText(getActivity(),"AdClosed",Toast.LENGTH_SHORT).show();
//            }
//        });



        return view;
    }


    @Override
    public void onResume() {
        //Log.v("Resume","Done");
        //Appodeal.onResume(getActivity(), Appodeal.BANNER);
        super.onResume();
    }
}
