package com.kawsarkobir2018.ipl;


import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RefFragment extends Fragment {


    public RefFragment() {
        // Required empty public constructor
    }

    FirebaseUser user;
    DatabaseReference reference;
    FirebaseAuth auth;
    UserInfo userInfo;

    TextView refPointTextView,userIdTextView;
    ListView refererListView;
    LinearLayout emptyView;
    Button copyButton;
    FloatingActionButton infoFab,giftFab;

    String uId = null;

    ReferAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ref, container, false);

        refPointTextView = (TextView) view.findViewById(R.id.refPointTextView);
        userIdTextView = (TextView) view.findViewById(R.id.refIdTextView);
        refererListView = (ListView) view.findViewById(R.id.refListView);
        emptyView = (LinearLayout) view.findViewById(R.id.emptyViewRef);
        copyButton = (Button) view.findViewById(R.id.refCopyButton);
        infoFab = (FloatingActionButton) view.findViewById(R.id.refQusfab);
        giftFab = (FloatingActionButton) view.findViewById(R.id.refGiftfab);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        user = auth.getCurrentUser();

        uId = user.getUid();

        userInfo = new UserInfo();

        giftFab.setVisibility(View.GONE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInfo = dataSnapshot.child(uId).getValue(UserInfo.class);

                refPointTextView.setText("Your Ref Point: "+userInfo.refPoint+" Royal\nReferred by: "+userInfo.refedPersons.size()+" Peoples");
                userIdTextView.setText(uId);
                if(!userInfo.isEverRef){
                    giftFab.setVisibility(View.VISIBLE);
                }
                adapter = new ReferAdapter(getActivity(),userInfo.refedPersons);
                refererListView.setAdapter(adapter);
                refererListView.setEmptyView(emptyView);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        userInfo.refedPersons.add("Talha Jubayer");
//        userInfo.refedPersons.add("Talha Tj");
//        userInfo.refedPersons.add("Talha 1");
//        userInfo.refedPersons.add("Talha 2");
//        userInfo.refedPersons.add("Talha 3");
//        userInfo.refedPersons.add("Talha 4");
//        userInfo.refedPersons.add("Talha 5");

        adapter = new ReferAdapter(getActivity(),userInfo.refedPersons);
        refererListView.setAdapter(adapter);
        refererListView.setEmptyView(emptyView);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(uId);
                Toast.makeText(getActivity(),"Copied!",Toast.LENGTH_SHORT).show();
            }
        });

        infoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setMessage("★★Referral to Eran !! \n" +
                        "\n" +
                        "★Referral অর্থ হল আমন্ত্রণ করা - রেফার করার দ্বারা অতিরিক্ত Royal আয় করা যাবে। প্রতি Referral দ্বারা আপনাকে ২০০ Royal অতিরিক্ত বোনাস প্রদান করা হবে। যদি কোন ব্যাক্তি আপনার Referral\u200B ID তার রেফার বক্সে Submit করে তাহলে আপনি পাবেন ২০০ Royal এবং যিনি Referral Code Submit করবেন তিনি ৫০ Royal বোনাস পাবেন।\n" +
                        "*নোট: - আপনে যদি স্প্যাম বা বিভ্রান্তিকর Referral করার চেষ্টা করেন তাহলে আপনার অ্যাকাউন্ট অবিলম্বে স্থগিত করা হবে। আমাদের সার্ভার অটো Check করে। @ধন্যবাদ");
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(true);
                alertDialog.show();
                TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                textView.setTextSize(20);
            }
        });

        giftFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                builde.setMessage("Gift your friend 200 Royal & gat 50 Royal..!\nPaste friend's RefId..");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builde.setView(input);
                builde.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builde.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final String fuId = input.getText().toString();
                        //dialog.dismiss();
                        if(fuId.equals(uId)){
                            Toast.makeText(getActivity(),"Failed!!",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        UserInfo fuserInfo = dataSnapshot.child(fuId).getValue(UserInfo.class);
                                        fuserInfo.refPoint += 200;
                                        fuserInfo.totalPoint += 200;
                                        fuserInfo.refedPersons.add(userInfo.name);
                                        userInfo.isEverRef = true;
                                        userInfo.refPoint += 50;
                                        userInfo.totalPoint += 50;
                                        reference.child(fuId).setValue(fuserInfo);
                                        reference.child(uId).setValue(userInfo);
                                        Toast.makeText(getActivity(),"Successfully Sent",Toast.LENGTH_SHORT).show();
                                        giftFab.setVisibility(View.GONE);
                                        refPointTextView.setText("Your Ref Point: "+userInfo.refPoint+" Royal\nReferred by: "+userInfo.refedPersons.size()+" Peoples");
                                        dialog.dismiss();
                                    }catch (Exception e){
                                        Toast.makeText(getActivity(),"Failed..Try again.",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(getActivity(),"Failed..Try again.",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
                AlertDialog alertDialog = builde.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                textView.setTextSize(20);
            }
        });

        return view;
    }

}
