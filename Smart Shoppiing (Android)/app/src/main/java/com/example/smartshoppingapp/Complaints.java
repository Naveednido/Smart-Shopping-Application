package com.example.smartshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Complaints extends AppCompatActivity {

    private TextInputLayout too , subject , message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);


        Toolbar toolbar = findViewById(R.id.complaint_appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Complaints & Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        too = findViewById(R.id.email_compl);
        subject = findViewById(R.id.complaint_subject);
        message = findViewById(R.id.complain_msg);
        Button btn_send = findViewById(R.id.btn_sendcomplaint);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });


    }

    private void sendMail() {

        String reciepientlist = too.getEditText().getText().toString();
        String recipients [] = reciepientlist.split(",");
        String subjct = subject.getEditText().getText().toString();
        String body = message.getEditText().getText().toString();


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL , recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT , subjct);
        intent.putExtra(Intent.EXTRA_TEXT , body);

        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent , "Choose only Email type"));
    }
}
