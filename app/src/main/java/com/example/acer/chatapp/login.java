package com.example.acer.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText email,pss;
    FirebaseAuth auth;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.editText4);
        pss=(EditText)findViewById(R.id.editText5);
        login=(Button)findViewById(R.id.button4);
        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString().trim();
                String p=pss.getText().toString().trim();
                if(!e.isEmpty() || !p.isEmpty()) {
                    setLogin(e, p);
                }
                else{
                    Toast.makeText(login.this,"invalid email or password",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    public void setLogin(String email,String pss){

        auth.signInWithEmailAndPassword(email,pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i=new Intent(login.this,chat.class);
                    i.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(i);
                }

            }
        });
    }
}
