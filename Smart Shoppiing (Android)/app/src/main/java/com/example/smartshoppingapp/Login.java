package com.example.smartshoppingapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartshoppingapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    private Toolbar login_toolbar;
    private TextInputLayout email , password ;
    private Button btn_login_account , btn_forgot_pasword;
    private ProgressDialog progressDialog;
    private CheckBox checkBox_remember_me;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        login_toolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(login_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.login_email);
        btn_forgot_pasword = findViewById(R.id.forgot_password);
        password = findViewById(R.id.login_password);
        btn_login_account = findViewById(R.id.btn_login_account);
        checkBox_remember_me = findViewById(R.id.remeber_me_check);


        btn_forgot_pasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Forgot Password");
                builder.setMessage("Please Enter Your Email Link will be Sent to your Email");
                // Set up the input
                final EditText input = new EditText(Login.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("Send My Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email_input_dialog = input.getText().toString();

                        if (TextUtils.isEmpty(email_input_dialog)) {
                            Toast.makeText(Login.this , "Enter Email" , Toast.LENGTH_LONG).show();
                        }
                        else {

                            mAuth.sendPasswordResetEmail(email_input_dialog).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Login.this , "Password Reset Link is sent to your Email" , Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(Login.this , "Please Enter Correct Email" , Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        btn_login_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_txt = email.getEditText().getText().toString();
                String pass_txt = password.getEditText().getText().toString();


                if (!TextUtils.isEmpty(email_txt) && !TextUtils.isEmpty(pass_txt))
                {
                    progressDialog.setTitle("Logging In");
                    progressDialog.setMessage("Please Wait While We Check your details !");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    login(email_txt,pass_txt);
                }
                else
                {
                    if (TextUtils.isEmpty(email_txt))
                        email.getEditText().setError("Required");
                    if (TextUtils.isEmpty(pass_txt))
                        password.getEditText().setError("Required");
                }
            }
        });
    }

    private void login(String email_txt, String pass_txt) {


        if (checkBox_remember_me.isChecked())
        {
            Paper.book().write(Prevalent.UserEmailKey , email_txt);
            Paper.book().write(Prevalent.UserPasswordKey , pass_txt);
        }

        mAuth.signInWithEmailAndPassword(email_txt , pass_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                }
                else
                {
                    progressDialog.hide();
                    Toast.makeText(Login.this , "Please Check Your Password & Email !" , Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
