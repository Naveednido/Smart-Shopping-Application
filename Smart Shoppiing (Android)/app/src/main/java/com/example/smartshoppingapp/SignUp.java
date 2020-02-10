package com.example.smartshoppingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private TextInputLayout mfirstname , mlastname , mEmail , mPassword , mPhonenum  ;
    private Button btn_create_account;
    private Toolbar signup_toolbar;


    private ProgressDialog progressDialog;

    // Firebase Auth
    private FirebaseAuth mAuth;
    DatabaseReference mdataDatabase_ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);


        signup_toolbar = findViewById(R.id.Signup_toolbar);
        setSupportActionBar(signup_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


// Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mfirstname = findViewById(R.id.f_name);
        mlastname  = findViewById(R.id.l_name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhonenum = findViewById(R.id.phone);

        btn_create_account = findViewById(R.id.btn_create_account);


        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String f_name = (mfirstname.getEditText()).getText().toString();
                String l_name = (mlastname.getEditText()).getText().toString();
                String email = (mEmail.getEditText()).getText().toString();
                String passw = (mPassword.getEditText()).getText().toString();
                String phone = mPhonenum.getEditText().getText().toString();


                if (!TextUtils.isEmpty(f_name) && !TextUtils.isEmpty(l_name) && !TextUtils.isEmpty(email) &&
                        !TextUtils.isEmpty(passw)  ) {

                    progressDialog.setTitle("Registering User");
                    progressDialog.setMessage("Please Wait While Set Up Your Details !");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    register_user_func(f_name, l_name, email, passw , phone);
                }
                else
                {
                    if (TextUtils.isEmpty(f_name))
                    {
                        mfirstname.getEditText().setError("Required");
                    }
                    if (TextUtils.isEmpty(l_name))
                    {
                        mlastname.getEditText().setError("Required");
                    }
                    if (TextUtils.isEmpty(email))
                    {
                        mEmail.getEditText().setError("Required");
                    }
                    if (TextUtils.isEmpty(passw))
                    {
                        mPassword.getEditText().setError("Required");
                    }


                }
            }
        });

    }

    private void register_user_func(final String f_name, final String l_name, final String email, String passw , final String phone) {

        mAuth.createUserWithEmailAndPassword(email, passw).addOnCompleteListener(this ,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    final DatabaseReference rootref;
                    rootref = FirebaseDatabase.getInstance().getReference();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String U_id =user.getUid();

                    rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!(dataSnapshot.child("Users").child(U_id).exists()))
                            {
                                mdataDatabase_ref = FirebaseDatabase.getInstance().getReference().child("User").child(U_id);
                                HashMap<String, String> usermap =  new HashMap<>();
                                usermap.put("first_name",f_name);
                                usermap.put("last_name",l_name);
                                usermap.put("phone",phone);
                                usermap.put("email",email);
                                usermap.put("image","defaut");
                                usermap.put("address", "Add Address !");
                                usermap.put("postal_code", "Add Postal code" );


                                mdataDatabase_ref.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            sendEmailVerification();
                                            progressDialog.dismiss();
                                            Intent main = new Intent(SignUp.this,Login.class);
                                            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(main);
                                            finish();
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else
                {
                    progressDialog.hide();

                    Toast.makeText(SignUp.this , "Email Already Exists" , Toast.LENGTH_LONG).show();
                    Intent main = new Intent(SignUp.this,WelcomeScreen.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();

                }
            }
        });


    }
  ////////////////////////// Later///////////////////////////////
    private void sendEmailVerification() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(SignUp.this , "Please open Your Email And Verify" , Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }

}

