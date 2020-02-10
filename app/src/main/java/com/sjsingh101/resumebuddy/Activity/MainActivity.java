package com.sjsingh101.resumebuddy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sjsingh101.resumebuddy.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {



    private RelativeLayout fbLoginTextbtn, googleLoginTextbtn,signUpWithMail;
    private TextView signInWithMailTv;
    private FirebaseAuth mAuth;

    CallbackManager callbackManager;
    public static final int REQ_CODE =9001;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions signInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        fbLoginTextbtn=findViewById(R.id.facebook_login);
        googleLoginTextbtn=findViewById(R.id.google_login);
        signUpWithMail=findViewById(R.id.register_user_email);
        signInWithMailTv=findViewById(R.id.sign_in_user_email);

        mAuth = FirebaseAuth.getInstance();

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new  GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        callbackManager=CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        fbLoginTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));
            }
        });


        googleLoginTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

        signUpWithMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent (MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);

            }
        });





    }

    public void logInWithEmail(View view) {
      Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
      startActivity(loginIntent);
      finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            Intent profileIntent=new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(profileIntent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(REQ_CODE==requestCode){ // for google login
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
        else{ // for facebook login
            callbackManager.onActivityResult(requestCode,resultCode,data);}
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    void signin(){

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }

    void handleResult(GoogleSignInResult googleSignInResult){

        if(googleSignInResult.isSuccess()){
            GoogleSignInAccount googleSignInAccount= googleSignInResult.getSignInAccount();
            Toast.makeText(getApplicationContext(),googleSignInAccount.getDisplayName(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));

        }
    }

}



