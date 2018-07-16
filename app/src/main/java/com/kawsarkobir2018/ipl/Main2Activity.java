package com.kawsarkobir2018.ipl;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    UserInfo userInfo = new UserInfo();
    Menu menu;
    MenuItem menuItem;
    MenuItem menuItem1;

    String msg = "";
    boolean isMsg = false;
    boolean isStart = true;
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Earn Money");
        EarnFragment earnFragment = new EarnFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fram,earnFragment, "Earn Fragment");
        fragmentTransaction.commit();

       // menuItem = (MenuItem)findViewById(R.id.msg);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

     //   menu = (Menu)findViewById(R.menu.menu);
//        onCreateOptionsMenu(menu);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msg = dataSnapshot.child("msg").getValue().toString();
                if(msg.equals("na")){
                //    menuItem.setVisible(false);
                    isMsg = false;
                }else {
                //    menuItem.setVisible(true);
                    isMsg = true;
                }

                userInfo = dataSnapshot.child("users").child(firebaseUser.getUid()).getValue(UserInfo.class);

                if(version != Integer.parseInt(dataSnapshot.child("isUpdated").getValue().toString())){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Main2Activity.this);
                    builder1.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.kawsarkobir2018.ipl")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.kawsarkobir2018.ipl")));
                            }
                        }
                    });
                    builder1.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder1.setMessage("★ এইমাত্র নতুন আপডেট হল Digital Cash - এখনি আপডেট করুন আর নতুন Features যোগ করুন। @ধন্যবাদ");
                    builder1.setTitle("New Version Released!");
                    AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.setCancelable(false);
                    alertDialog1.show();
                    TextView textView1 = (TextView) alertDialog1.findViewById(android.R.id.message);
                    textView1.setTextSize(20);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(!isMsg){
                    menuItem.setVisible(false);
                }else {
                    menuItem.setVisible(true);
                }
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Main2Activity.this);
            builder1.setPositiveButton("না", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder1.setNegativeButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder1.setMessage("আপনি কি Digital Cash থেকে বের হতে চান  ??");
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.setCancelable(true);
            alertDialog1.show();
            TextView textView1 = (TextView) alertDialog1.findViewById(android.R.id.message);
            textView1.setTextSize(20);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nev_earn_money) {
            setTitle("Earn Money");
            EarnFragment earnFragment = new EarnFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,earnFragment, "Earn Fragment");
            fragmentTransaction.commit();
        } else if (id == R.id.nev_payment) {
            setTitle("Payment Option");
            PayFragment payFragment = new PayFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,payFragment, "Pay Fragment");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_Refer) {
            setTitle("Refer Option");
            RefFragment refFragment = new RefFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,refFragment, "Ref Fragment");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_user_guide) {
            setTitle("User Guide");
            HowToFragment howToFragment = new HowToFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,howToFragment, "HowToUse Fragment");
            fragmentTransaction.commit();
        } else if (id == R.id.nev_Top) {
            setTitle("Top Earner");
            TopFragment topFragment = new TopFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,topFragment, "Top Fragment");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_fbP) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.bdtechmaster.com"));
            startActivity(intent);
        } else if (id == R.id.nav_fbG) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getFacebookGroupURL()));
            startActivity(intent);
        }else if (id == R.id.nav_contact) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setMessage("যদি আপনার কোন রকম সাহায্য  দরকার হয় তাহলে আমাদের ফেসবুক গুরুফে জয়েন করুন এবং আপনার সমস্যার সর্স্পকিত  Screenshot সহ post করুন - আমরা আপনার সমাস্যার সমাধান ৪৮ ঘন্টার ভিতর করে দিব।\n" +
                    "\n" +
                    "যদি আপনারা আমাদের সাথে App Advertising করতে চান !!\n" +
                    "তাহলে আমাদের সাথে যোগাযোগ করুন আমরা আপনার App বাংলাদেশী এবং  অন্যান্য দেশের হাজার হাজার মানুষের দ্বারা install করিয়ে দিব।\n" +
                    "\n" +
                    "আমাদের সাথে যোগাযোগ করুন দ্বিধামুক্ত - আমরা আছি আপনার\n" +
                    "পাশে সবসময়।\n" +
                    "★ডিজিটাল ক্যাশ বিশ্বস্ততার প্রতিক।\n" +
                    "\n" +
                    "\n" +
                    "---------------Our Email :-\n" +
                    "Help.DigitalCash@gmail.com\n" +
                    "\n" +
                    "\n" +
                    "If you need any help, then join our Facebook Grouped and post your problem with a screenshot - we will solve your problem within 48 hours.\n" +
                    "\n" +
                    "If you want to make App Advertising with us !!\n" +
                    "Then contact us, we will install your App Bangladeshi and other countries of people from\n" +
                    "\n" +
                    "Contact us free of charge - we are always around you.\n" +
                    "Digital cash fidelity symbol.");
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();
            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(20);
        }else if (id == R.id.nav_pvp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setMessage("★★Privacy Policy of  DigitalCash.\n" +
                    "\n" +
                    "This Application collects some Personal Data from its Users.\n" +
                    "\n" +
                    "★Types of Data collected :-\n" +
                    "\n" +
                    "Among the types of Personal Data that this Application collects, by itself or through third parties, there are: Cookies, Usage Data, Approximate location permission (non-continuous), unique device identifiers for advertising (Google Advertiser ID or IDFA, for example), various types of Data, phone number, email address, first name, last name, province, state, country, ZIP/Postal code, gender, date of birth and address.\n" +
                    "\n" +
                    "Complete details on each type of Personal Data collected are provided in the dedicated sections of this privacy policy or by specific explanation texts displayed prior to the Data collection.\n" +
                    "The Personal Data may be freely provided by the User, or, in case of Usage Data, collected automatically when using this Application.\n" +
                    "All Data requested by this Application is mandatory and failure to provide this Data may make it impossible for this Application to provide its services. In cases where this Application specifically states that some Data is not mandatory, Users are free not to communicate this Data without any consequences on the availability or the functioning of the service.\n" +
                    "Users who are uncertain about which Personal Data is mandatory are welcome to contact the Owner.\n" +
                    "Any use of Cookies – or of other tracking tools – by this Application or by the owners of third-party services used by this Application serves the purpose of providing the service required by the User, in addition to any other purposes described in the present document and in the Cookie Policy, if available.\n" +
                    "\n" +
                    "Users are responsible for any third-party Personal Data obtained, published or shared through this Application and confirm that they have the third party's consent to provide the Data to the Owner.\n" +
                    "\n" +
                    "\n" +
                    "★Device permissions for Personal Data access :- \n" +
                    "\n" +
                    "Depending on the User's specific device, this Application may request certain permissions that allow it to access the User's device Data as described below.\n" +
                    "\n" +
                    "By default, these permissions must be granted by the User before the respective information can be accessed. Once the permission has been given, it can be revoked by the User at any time. In order to revoke these permissions, Users may refer to the device settings or contact the Owner for support at the contact details provided in the present document.\n" +
                    "The exact procedure for controlling app permissions may be dependent on the User's device and software.\n" +
                    "\n" +
                    "Please note that the revoking of such permissions might impact the proper functioning of this Application.\n" +
                    "\n" +
                    "If User grants any of the permissions listed below, the respective Personal Data may be processed (i.e accessed to, modified or removed) by this Application.\n" +
                    "\n" +
                    "★We do not specifically market to children under the age of 13 years old. CAN SPAM Act\n" +
                    "The CAN-SPAM Act is a law that sets the rules for commercial email, establishes requirements for commercial messages, gives recipients the right to have emails stopped from being sent to them, and spells out tough penalties for violations.\n" +
                    "\n" +
                    "★AdMob (AdMob Google Inc.)\n" +
                    "\n" +
                    "AdMob is an advertising service provided by AdMob Google Inc.\n" +
                    "In order to understand Google's use of Data, consult Google's partner policy.\n" +
                    "\n" +
                    "Personal Data collected: Cookies, unique device identifiers for advertising (Google Advertiser ID or IDFA, for example) and Usage Data.\n" +
                    "\n" +
                    "Google AdSense (Google Inc.)\n" +
                    "Google AdSense is an advertising service provided by Google Inc. This service uses the “Doubleclick” Cookie, which tracks use of this Application and User behavior concerning ads, products and services offered.\n" +
                    "Users may decide to disable all the Doubleclick Cookies by clicking on: google.com/settings/ads/onweb/optout.\n" +
                    "\n" +
                    "Application might use Google's Interest-based advertising, 3rd-party audience data and information from the DoubleClick Cookie to extend analytics with demographics, interests and ads interaction data.\n" +
                    "\n" +
                    "★To be in accordance with Digital, We agree to the following:\n" +
                    "If at any time you would like to unsubscribe from receiving future emails, you can email us at\n" +
                    "and we will promptly remove you from all correspondence.\n" +
                    "\n" +
                    "@Thanks for understanding");
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();
            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(20);
        }else if(id==R.id.nav_share){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Copy Link", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText("https://play.google.com/store/apps/details?id=com.kawsarkobir2018.ipl");
                    dialog.dismiss();
                    Toast.makeText(Main2Activity.this,"App Link is Copied!\nNow paste it anywhere and send to your Friends!",Toast.LENGTH_SHORT).show();

                }
            });
            builder.setMessage("Click 'Copy Link' button and paste the link and Send it to your Friends!!");
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();
            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(20);
        }else if(id==R.id.nav_ratings){
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.kawsarkobir2018.ipl")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.kawsarkobir2018.ipl")));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu,menu);
        menuItem = menu.findItem(R.id.msg);
        menuItem = menu.findItem(R.id.pp);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menuItem = menu.findItem(R.id.msg);
        menuItem1 = menu.findItem(R.id.pp);
        if(!isMsg){
            menuItem.setVisible(false);
        }else {
            menuItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.msg:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.setMessage(msg);
                builder1.setTitle("New Message");
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.setCancelable(true);
                alertDialog1.show();
                TextView textView1 = (TextView) alertDialog1.findViewById(android.R.id.message);
                textView1.setTextSize(20);
                break;
            case R.id.pp:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.pp);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Get AC-Id", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(firebaseUser.getUid());
                        Toast.makeText(Main2Activity.this,"Your Account ID is Copied!\nNow paste it anywhere!",Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });
                UserInfo uo = userInfo;
                builder.setTitle("Hey "+uo.name+", Welcome!\nIts your Profile!");
                builder.setMessage(""+uo.email
                        +"\nPhone: "+uo.phone
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
                break;

        }
        return true;
    }

    public String getFacebookPageURL() {
        String FACEBOOK_URL = "https://www.facebook.com/DigitalCashApp";
        String FACEBOOK_PAGE_ID = "DigitalCashApp";
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }
    public String getFacebookGroupURL() {
        String FACEBOOK_URL = "https://www.facebook.com/groups/115897482515001";
        String FACEBOOK_PAGE_ID = "115897482515001";
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                return "fb://groups/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
