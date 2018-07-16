package com.kawsarkobir2018.ipl;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class RegisterActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseAuth mAuth;
    EditText fname,lname,temail,tpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder1.setMessage("★আগে সবকিছু জানুন তারপর কাজ করুন - আপনার একাউন্ট সুরিক্ষিত রাখতে এবং নতুন ব্যবহারকারী হলে প্রথমেই User Guide টি পড়ে নিন। \n" +
                "কোন ধরনের অভিযোগ বা সাহায্য প্রয়োজন হলে আমাদের ফেসবুক গুরুফে জয়েন করুন।  @ধন্যবাদ");
        builder1.setTitle("Warning");
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.setCancelable(false);
        alertDialog1.show();
        TextView textView1 = (TextView) alertDialog1.findViewById(android.R.id.message);
        textView1.setTextSize(20);

        mAuth = FirebaseAuth.getInstance();

        fname = (EditText)findViewById(R.id.firstnameEditText);
        lname = (EditText)findViewById(R.id.lastnameEditText);
        temail = (EditText)findViewById(R.id.emailEditText);
        tpass = (EditText)findViewById(R.id.passEditText);
    }

    public void register(View view){
        String tname = fname.getText().toString().trim()+" "+lname.getText().toString().trim();
        String mEmail = temail.getText().toString().trim();
        String mPass = tpass.getText().toString().trim();
        if(!tname.isEmpty()&&!mEmail.isEmpty()&&!mPass.isEmpty()){
            final String ip = getLocalIpAddress();
            final DatabaseReference ipref = FirebaseDatabase.getInstance().getReference("ip");
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Register is in Progress...");
            dialog.setCancelable(false);
            dialog.show();
            ipref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean hasIp = false;
//                    IpClass ourIpClass = dataSnapshot.getValue(IpClass.class);
                    ArrayList<String> ourIp = new ArrayList<String>();
//                    for(int i = 0;ourIp.size()>i;i++){
//                        String lastip = ourIp.get(i);
//                        if(lastip.equals(ip)){
//                            hasIp = true;
//                            break;
//                        }
//                    }
                    for(DataSnapshot mySnapshot: dataSnapshot.child("ip").getChildren()){
                        String lastip = mySnapshot.getValue().toString();
                        ourIp.add(lastip);
                        if(lastip.equals(ip)){
                            hasIp = true;
                            break;
                        }
                    }
                    if (hasIp){
                        mAuth.getCurrentUser().delete();
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("Failed");
                        builder.setMessage("এই ডিভাইসে অন্য একটি অ্যাকাউন্ট আছে!\nOk তে ক্লিক করুন তারপর পুনরায় অ্যাপটি চালু করে আগের অ্যাকাউন্টে Login এর চেষ্টা করুন!");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //mAuth.getInstance().signOut();
                                AuthUI.getInstance()
                                        .signOut(RegisterActivity.this)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {
//                                                File cacheDirectory = getCacheDir();
//                                                File applicationDirectory = new File(cacheDirectory.getParent());
//                                                if (applicationDirectory.exists()) {
//                                                    String[] fileNames = applicationDirectory.list();
//                                                    for (String fileName : fileNames) {
//                                                        if (!fileName.equals("lib")) {
//                                                            deleteFile(String.valueOf(new File(applicationDirectory,fileName)));
//                                                        }
//                                                    }
//                                                }
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                    ((ActivityManager)RegisterActivity.this.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                                                }
                                                startActivity(new Intent(RegisterActivity.this,UIReg.class));
                                                finish();
                                            }
                                        });
                            }
                        });
                        AlertDialog dialog1 = builder.create();
                        dialog1.show();
                    }else {
                        String tname1 = fname.getText().toString().trim()+" "+lname.getText().toString().trim();
                        String mEmail1 = temail.getText().toString().trim();
                        String mPass1 = tpass.getText().toString().trim();
                        user = mAuth.getCurrentUser();
                        String email = user.getEmail();
                        String phone = mEmail1;
                        String pass = mPass1;
                        String name = tname1;
                        Integer point = 50;
                        Integer refPoint = 0;
                        boolean isEverRef = false;
                        ArrayList<String> whoRefered = new ArrayList<String>();
                        ArrayList<Integer> viewApps = new ArrayList<Integer>();
                        ArrayList<PaymentHistory> paymentHistory = new ArrayList<>();
                        Integer tp = 50;
                        Integer wa = 0;
                        UserInfo userInfo;
                        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference("users");
                        userInfo = new UserInfo(name,email,point,tp,refPoint,isEverRef,whoRefered,viewApps,paymentHistory,phone,pass,wa);
                        firebaseReference.child(user.getUid()).setValue(userInfo);
                        ourIp.add(ip);
                        IpClass ipc = new IpClass(ourIp);
                        ipref.setValue(ipc);
                        SharedPreferences sharedPreferences;
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("hPoint",0);
                        editor.putInt("dPoint",0);
                        dialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this,Main2Activity.class));
                        finish();
                        Toast.makeText(RegisterActivity.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(RegisterActivity.this,"Register Failed!",Toast.LENGTH_SHORT).show();
                }
            });
        }else {

            Toast.makeText(this,"Register Failed!",Toast.LENGTH_SHORT).show();
        }
    }
    public String getLocalIpAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }
}
