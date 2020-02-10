package com.sjsingh101.resumebuddy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sjsingh101.resumebuddy.R;

public class LoginActivity extends AppCompatActivity {

    // layout fields
    private Toolbar mToolbar;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private Button mLoginButton;
    private ProgressDialog mProgressDialog;

    // firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // layout fields
        mToolbar=findViewById(R.id.log_in_activity_toolbar);
        mEmail=findViewById(R.id.login_email);
        mPassword=findViewById(R.id.login_password);
        mLoginButton=findViewById(R.id.login_btn);

        // Toolbar setup
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // firebase
        mAuth = FirebaseAuth.getInstance();


        mProgressDialog=new ProgressDialog(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getEditableText().toString();
                String password=mPassword.getEditableText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    // progress dialog setup
                    mProgressDialog.setTitle("Logging In");
                    mProgressDialog.setMessage("Please wait while we check your credentials!");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    loginUser(email,password);
                }
            }
        });




    }

    private void loginUser(String email,String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mProgressDialog.dismiss();
                    Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                    profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(profileIntent);
                    finish();
                }

                else
                {
                    mProgressDialog.hide();
                    Toast.makeText(LoginActivity.this,"Login unsuccessful please enter correct credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
