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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class register extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference reference;
    EditText username,email,pss;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.editText);
        email=(EditText)findViewById(R.id.editText2);
        pss=(EditText)findViewById(R.id.editText3);
        reg=(Button)findViewById(R.id.button3);
        auth=FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString().trim();
                String u=username.getText().toString().trim();
                String p=pss.getText().toString().trim();
                if(!e.isEmpty() || !u.isEmpty() || !p.isEmpty()) {

                    registered(u, e, p);

                }
                else{
                    Toast.makeText(register.this,"invalid input",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void registered(final String username, final String email, String pss){

        auth.createUserWithEmailAndPassword(email,pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user=auth.getCurrentUser();
                    String userid=user.getUid();
                    reference=FirebaseDatabase.getInstance().getReference("user").child(userid);
                    HashMap<String,String> hashmap=new HashMap<>();
                    hashmap.put("id",userid);
                    hashmap.put("username",username);
                    hashmap.put("imageurl","Default");

                    reference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {Intent i=new Intent(register.this,login.class);
                            i.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(i);

                            }

                            else
                            {
                                Toast.makeText(register.this,"not reg",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

