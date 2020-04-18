package com.example.acer.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button log,reg;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg=(Button)findViewById(R.id.button2);
        log=(Button)findViewById(R.id.button);
        log.setOnClickListener(this);
        reg.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            Intent i;
            i = new Intent(MainActivity.this,chat.class);
            i.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
        }
    }

    @Override
    public void onClick(View view) {
        if(view==log){
                Intent i=new Intent(MainActivity.this,login.class);
                i.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);}

            if(view==reg){
                Intent j=new Intent(MainActivity.this,register.class);
                j.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(j);}

    }
}
