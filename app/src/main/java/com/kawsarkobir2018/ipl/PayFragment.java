package com.kawsarkobir2018.ipl;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {


    public PayFragment() {
        // Required empty public constructor
    }

    TextView paymetnTextView;
    Button cashOutButton;
    FloatingActionButton payFab;
    ListView historyListView;
    TextView historyTextView;
    LinearLayout emptyViewP;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    DatabaseReference requsetReference;

    UserInfo userInfo;

    int point =0;

    String[] title;
    String spinner_item;
    SpinnerAdapter adapter;

    String sdollar,staka,sdate;

    PaymentAdapter paymentAdapter;
    PaymentRequest r;

    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, container, false);

//        String appKey = "f942955b9e8c209e6dc759190846c456af8459a7629a504a";
//        Appodeal.disableLocationPermissionCheck();
//        Appodeal.setBannerViewId(R.id.appodealBannerView2);
//        Appodeal.initialize(getActivity(), appKey, Appodeal.BANNER);
//        Appodeal.show(getActivity(), Appodeal.BANNER_VIEW);

        mAdView = view.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        title = getResources().getStringArray(R.array.titles);
        adapter=new SpinnerAdapter(getActivity());

        paymetnTextView = (TextView) view.findViewById(R.id.paymentInfoTextView);
        cashOutButton = (Button) view.findViewById(R.id.cashOutButton);
        cashOutButton.setVisibility(View.GONE);
        payFab = (FloatingActionButton) view.findViewById(R.id.payQusfab);
        historyListView = (ListView) view.findViewById(R.id.PayListView);
        historyTextView = (TextView) view.findViewById(R.id.paymentHistoryTextView);
        emptyViewP = (LinearLayout) view.findViewById(R.id.emptyViewPay);

        userInfo = new UserInfo();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
      //  requsetReference = FirebaseDatabase.getInstance().getReference("requests");

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                userInfo = dataSnapshot.child("users").child(user.getUid()).getValue(UserInfo.class);
//                point = userInfo.point + userInfo.refPoint;
//                start();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        start();

        payFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setMessage("★★টাকা উঠানোর মাধ্যম।!! \n" +
                        "১.bKash\n" +
                        "২.Rocket\n" +
                        "২.Recharge BD\n" +
                        "৩.Payza\n" +
                        "৪.Paypal\n" +
                        "\n" +
                        "★★সর্বনিম্ন ৩০০০ Royal হলে পেমেন্ট রিকুয়েস্ট করতে পারবেন।\n" +
                        "★★১তারিখ থেকে ৭তারিখ পেমেন্ট এর জন্য রিকুয়েস্ট করার যাবে।\n" +
                        "★★ পেমেন্ট রিকুয়েস্ট করার পর ১২০ ঘন্টার ভিতর পেমেন্ট হাতে পেয়ে যাবেন।\n" +
                        "\n" +
                        "★যে কোন সমস্যার সমাধান বা সাহায্য পেতে আমাদের ফেসবুক গুরুফে জয়েন করুন। @ধন্যবাদ");
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(true);
                alertDialog.show();
                TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                textView.setTextSize(20);
            }
        });

        cashOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_spinner);
                dialog.setCancelable(false);

                final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner1);
                final EditText edittext = (EditText) dialog.findViewById(R.id.editText1);
                final EditText edittext1 = (EditText) dialog.findViewById(R.id.editTextpass);
                Button button = (Button) dialog.findViewById(R.id.button1);
                Button cButton = (Button) dialog.findViewById(R.id.button2);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner_item = title[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String paymentMethod = spinner_item;
                        String paymentAmount;
                        String paymentInput = edittext.getText().toString().trim();
                        String paymentInput1 = edittext1.getText().toString().trim();
                        String paymentRoyal = String.valueOf(point);
                        String paymentDate = sdate;
                        String userName = userInfo.name;
                        String phone = userInfo.phone;
                        if(paymentMethod.equals("Payza")||paymentMethod.equals("Paypal")){
                            paymentAmount = sdollar + " Dollar";
                        }else {
                            paymentAmount = staka + " Taka";
                        }
                        PaymentRequest request = new PaymentRequest(paymentMethod,paymentAmount,paymentInput,paymentRoyal,paymentDate,userName,phone);
                        PaymentHistory history = new PaymentHistory(paymentMethod,paymentAmount,paymentInput,paymentRoyal,paymentDate,0);
                        if(!paymentInput.isEmpty()&&!paymentInput1.isEmpty()){
                            if(paymentInput1.equals(userInfo.pass)){
                                databaseReference.child("requests").child(user.getUid()).setValue(request);
                                userInfo.point = 0;
                                userInfo.refPoint = 0;
                                ArrayList<PaymentHistory> reversed = userInfo.paymentHistory;
                                int size = reversed.size();
                                for (int i = 0; i < size / 2; i++) {
                                    final PaymentHistory ph = reversed.get(i);
                                    reversed.set(i, reversed.get(size - i - 1)); // swap
                                    reversed.set(size - i - 1, ph); // swap
                                }
                                userInfo.paymentHistory = reversed;
                                userInfo.paymentHistory.add(history);
                                databaseReference.child("users").child(user.getUid()).setValue(userInfo);
                                point = 0;
                                start();
                                Toast.makeText(getActivity(), "Congregation  !! আপনার পেমেন্ট রিকুয়েস্ট সফল ভাবে করা হয়েছে. আশা করি 72 ঘন্টার ভিতর পেমেন্ট পেয়ে যাবেন। @ধন্যবাদ", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }else {
                                edittext1.setText("");
                                Toast.makeText(getActivity(), "পাসওয়ার্ড ভূল !! \n" +
                                        "আপনার পাসওয়ার্ড ভুলে গেলে আমাদের ফেসবুক গুরুপে পোষ্ট করুন।", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Don't keep anything empty!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                cButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int revp = (historyListView.getCount()-1)-position;
                PaymentHistory paymentHist = userInfo.paymentHistory.get(position);
                int j = paymentHist.paymentProof;
                final int positio = position;
                if (j==0){
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.row_spinner);
                    dialog.setCancelable(false);

                    final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner1);
                    final EditText edittext = (EditText) dialog.findViewById(R.id.editText1);
                    final EditText edittext1 = (EditText) dialog.findViewById(R.id.editTextpass);
                    Button button = (Button) dialog.findViewById(R.id.button1);
                    Button cButton = (Button) dialog.findViewById(R.id.button2);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinner_item = title[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            final String paymentMethod = spinner_item;
                            final String paymentInput = edittext.getText().toString().trim();
                            final String paymentInput1 = edittext1.getText().toString().trim();
                            if(!paymentInput.isEmpty()&&!paymentInput1.isEmpty()){
                                if(paymentInput1.equals(userInfo.pass)){
                                    r.paymentMethod = paymentMethod;
                                    r.paymentInput = paymentInput;
                                    databaseReference.child("requests").child(user.getUid()).setValue(r);

                                    PaymentHistory paymentHist = userInfo.paymentHistory.get(positio);
                                    paymentHist.paymentMethod = paymentMethod;
                                    paymentHist.paymentInput = paymentInput;
                                    userInfo.paymentHistory.set(positio,paymentHist);
                                    ArrayList<PaymentHistory> reversed = userInfo.paymentHistory;
                                    int size = reversed.size();
                                    for (int i = 0; i < size / 2; i++) {
                                        final PaymentHistory ph = reversed.get(i);
                                        reversed.set(i, reversed.get(size - i - 1)); // swap
                                        reversed.set(size - i - 1, ph); // swap
                                    }
                                    userInfo.paymentHistory = reversed;
                                    databaseReference.child("users").child(user.getUid()).setValue(userInfo);

//                                databaseReference.child("requests").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
                                    Toast.makeText(getActivity(), "Update Success!", Toast.LENGTH_LONG).show();
                                    start();
                                    dialog.dismiss();
                                }else {
                                    edittext1.setText("");
                                    Toast.makeText(getActivity(), "পাসওয়ার্ড ভূল !! \n" +
                                            "আপনার পাসওয়ার্ড ভুলে গেলে আমাদের ফেসবুক গুরুপে পোষ্ট করুন।", Toast.LENGTH_LONG).show();                                }
                            }else{
                                Toast.makeText(getActivity(), "Don't keep anything empty!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    cButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        return view;
    }

    public void start(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.child("users").child(user.getUid()).getValue(UserInfo.class);
                point = userInfo.point + userInfo.refPoint;
                r = dataSnapshot.child("requests").child(user.getUid()).getValue(PaymentRequest.class);

                double dollerRate = Double.valueOf(dataSnapshot.child("dollerRate").getValue().toString());
                double takaRate =  Double.valueOf(dataSnapshot.child("takaRate").getValue().toString());

                int startDate = Integer.valueOf(dataSnapshot.child("startDate").getValue().toString());
                int endDate = Integer.valueOf(dataSnapshot.child("endDate").getValue().toString());

                if(point >= 3000){
                    Calendar cal = Calendar.getInstance();
                    Date d = cal.getTime();
                    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
                    String date = String.valueOf(sdf.format(d));
                    sdate = date;
                    if((dayOfMonth>=startDate && dayOfMonth<=endDate)){
                        int taka = (int) (point*(takaRate/1000));
                        double dollar = (point*(dollerRate/1000));
                        DecimalFormat df = new DecimalFormat("#.##");
                        dollar = Double.valueOf(df.format(dollar));
                        sdollar = String.valueOf(dollar);
                        staka = String.valueOf(taka);
                        paymetnTextView.setText("Your Total Royal: "+point+" Royal\nAmount in Taka: "+taka+" Taka\nAmount in Dollar: "+dollar+" Dollar\nTaka Rate: "+takaRate+" Taka/1000 Royal\nDollar Rate: "+dollerRate+" Dollar/1000 Royal");
                        cashOutButton.setVisibility(View.VISIBLE);

                    }else{
                        paymetnTextView.setText("সম্মানিত ডিজিটাল ক্যাশ গ্রাহক !!" +
                                "আপনার অবগতির জন্য জানাচ্ছি যে ডিজিটাল ক্যাশ প্রতি মাসের "+startDate+" - "+endDate+" তারিখের ভিতর পেমেন্ট পরিশোধ করে। আজকে "+date+" তারিখ অনুগ্রহ করে "+startDate+" তারিখ পর্যন্ত অপেক্ষা করুন।");
                        paymetnTextView.setTextColor(Color.parseColor("#008000"));
                        cashOutButton.setVisibility(View.GONE);
                    }
                }else {
                    paymetnTextView.setText("সম্মানিত ডিজিটাল ক্যাশ গ্রাহক !!" +
                            "পেমেন্ট রিকুয়েস্ট এর জন্য 3000 Royal প্রয়োজন।  বর্তমানে আপনার আছে "+point+" Royal. " +
                            "অনুগ্রহ করে 3000 Royal আয় করার পর রিকুয়েস্ট করুন।");
                    paymetnTextView.setTextColor(Color.parseColor("#ff0000"));
                    cashOutButton.setVisibility(View.GONE);
                }
                int tt = 0;
                for (int i = 0;i<userInfo.paymentHistory.size();i++){
                    PaymentHistory pp = userInfo.paymentHistory.get(i);
                    tt += Integer.parseInt(pp.paymentRoyal);
                }
                historyTextView.setText("Total Cash out Royal: "+tt+" Royal.\nTotal Cash out Time: "+userInfo.paymentHistory.size()+" Times.");
                ArrayList<PaymentHistory> reversed = userInfo.paymentHistory;
                int size = reversed.size();
                for (int i = 0; i < size / 2; i++) {
                    final PaymentHistory ph = reversed.get(i);
                    reversed.set(i, reversed.get(size - i - 1)); // swap
                    reversed.set(size - i - 1, ph); // swap
                }
                paymentAdapter = new PaymentAdapter(getActivity(), reversed);
                historyListView.setAdapter(paymentAdapter);
                historyListView.setEmptyView(emptyViewP);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public class SpinnerAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;

        public SpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.row_textview, null);
                holder = new ListContent();
                holder.text = (TextView) v.findViewById(R.id.textView1);
                holder.imageView = (ImageView) v.findViewById(R.id.imageof);

                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }

            holder.text.setText(title[position]);
            switch (position){
                case 0:
                    holder.imageView.setImageResource(R.drawable.bkash);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.roket);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.flexiload);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.paypal);
                    break;
                case 4:
                    holder.imageView.setImageResource(R.drawable.payza);
                    break;
            }

            return v;
        }
    }

    static class ListContent {
        TextView text;
        ImageView imageView;
    }

    @Override
    public void onResume() {
        //Log.v("Resume","Done");
        //Appodeal.onResume(getActivity(), Appodeal.BANNER);
        super.onResume();
    }
}