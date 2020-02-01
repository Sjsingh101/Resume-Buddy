package com.sjsingh101.resumebuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

/*public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    SignInButton googlesignInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN=1;
    public static final String GOOGLE_SIGN_IN_KEY="Google SignIn";

    public static final String FACEBOOK_SIGN_IN_KEY="Facebook SignIn";
    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
    static LoginResult fbLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this)
        .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        googlesignInButton=findViewById(R.id.google_login);
        fbLoginButton=findViewById(R.id.fb_login);

        callbackManager=CallbackManager.Factory.create();
        //checkGoogleLoginStatus();

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
            }
        });

        googlesignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN);
            }
        });

        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));
            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==SIGN_IN) // for google login
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Intent googleIntent = new Intent(MainActivity.this, ProfileActivity.class);
                googleIntent.putExtra(GOOGLE_SIGN_IN_KEY, "Signing through google");
                startActivity(googleIntent);
                finish();
            }
        }

            else // facebook login
            {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkGoogleLoginStatus()
    {
        OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone())
        {
            Intent googleIntent=new Intent(MainActivity.this,ProfileActivity.class);
            googleIntent.putExtra(GOOGLE_SIGN_IN_KEY,"singing with Google");
            startActivity(googleIntent);
        }
    }
}*/


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {



    RelativeLayout fbLoginTextbtn, googleLoginTextbtn,signUpWithMail,alreadysignUpWithMail;

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






        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new  GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        callbackManager=CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
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



