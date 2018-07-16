package com.kawsarkobir2018.ipl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UIReg extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    FirebaseUser user ;
    Button fb,google;
    FirebaseAuth auth;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uireg);

//        fb = (Button)findViewById(R.id.fb);
//        google = (Button)findViewById(R.id.google);

//        fb.setVisibility(View.GONE);
//        google.setVisibility(View.GONE);

        auth  = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        providers = new ArrayList<>();

       if(user!=null){
           startActivity(new Intent(UIReg.this,Main2Activity.class));
           finish();
       }else{
           providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
           startActivityForResult(
                   AuthUI.getInstance()
                           .createSignInIntentBuilder()
                           .setProviders(providers)
                           .setTheme(R.style.FullscreenTheme)
                           .build(),
                   RC_SIGN_IN);
//           fb.setVisibility(View.VISIBLE);
//           google.setVisibility(View.VISIBLE);
       }

       final String fbProvider = FacebookAuthProvider.PROVIDER_ID;


//       fb.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               auth.signInWithCredential(FacebookAuthProvider.getCredential("f"));
//           }
//       });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            final FirebaseUser finalUser = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean reg = true;
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        String id = snapshot.getKey();
                        if (finalUser.getUid().equals(id)){
                            reg = false;
                            break;
                        }
                    }
                    if(reg){
                        startActivity(new Intent(UIReg.this,RegisterActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(UIReg.this,Main2Activity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            } else {
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT);
            }
        }
    }


}
