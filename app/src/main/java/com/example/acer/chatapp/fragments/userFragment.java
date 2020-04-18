package com.example.acer.chatapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.acer.chatapp.Adapter.UserAdapter;
import com.example.acer.chatapp.R;
import com.example.acer.chatapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class userFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUser;
    FirebaseAuth auth;
    private EditText search;
    public ImageView imagev;
    int c=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view=    inflater.inflate(R.layout.fragment_user, container, false);
   recyclerView=(RecyclerView) view.findViewById(R.id.recycle);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      auth=FirebaseAuth.getInstance();
      search=view.findViewById(R.id.search);
      imagev=view.findViewById(R.id.imageView);





      mUser= new ArrayList<>();
      imagev.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(c==1){
              search.setVisibility(View.VISIBLE);

              c=2;}
              else {
                  search.setVisibility(View.GONE);
                  c=1;
              }

          }
      });

      readUser();
      search.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              search(charSequence.toString());

          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });


    return view;
    }

    private void search(String s)
    {
        final FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference("user").orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    User user=snapshot.getValue(User.class);
                    if(!user.getId().equals(fuser.getUid()))
                    {
                         mUser.add(user);

                    }

                }
                userAdapter=new UserAdapter(getContext(),mUser,false);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUser(){
       final FirebaseUser fireUser=auth.getCurrentUser();
       final String userid=fireUser.getUid();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search.getText().toString().equals("")) {
                    mUser.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        assert userid != null;
                        if (!user.getId().equals(userid)) {
                            mUser.add(user);
                        }
                    }

                    userAdapter = new UserAdapter(getContext(), mUser, false);
                    recyclerView.setAdapter(userAdapter);
                }
            }
              @Override
            public  void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    }



