package com.sjsingh101.resumebuddy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sjsingh101.resumebuddy.R;

public class RegisterActivity extends AppCompatActivity {

    // input fields
    private TextInputEditText mDisplayName,mEmail,mPassword;
    private Button createAccountBtn;
    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;

    // firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        // input fields
        mDisplayName=findViewById(R.id.reg_display_name);
        mEmail=findViewById(R.id.reg_email);
        mPassword=findViewById(R.id.reg_password);
        createAccountBtn=findViewById(R.id.create_account_btn);

        // Toolbar configuration
        mToolBar=findViewById(R.id.register_activity_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Progress dialog
        mProgressDialog=new ProgressDialog(this);


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName=mDisplayName.getEditableText().toString();
                String email=mEmail.getEditableText().toString();
                String password=mPassword.getEditableText().toString();

                if (!TextUtils.isEmpty(displayName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    // Progress dialog customisation
                    mProgressDialog.setTitle(getString(R.string.reg_ProgressDialog_title));
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.setMessage(getString(R.string.reg_ProgressDialog_message));
                    mProgressDialog.show();

                    registerUser(displayName, email, password);
                }

                else {

                    Toast.makeText(RegisterActivity.this,getString(R.string.reg_Login_failed_message),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void registerUser(String displayName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful())
              {
                  mProgressDialog.dismiss();
                  Intent profileIntent=new Intent(RegisterActivity.this,ProfileActivity.class);
                  profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(profileIntent);
                  finish();
              }

              else
              {
                  mProgressDialog.hide();
                  Toast.makeText(RegisterActivity.this,"Failed to register try again!",Toast.LENGTH_SHORT).show();
              }
            }
        });

    }
}
