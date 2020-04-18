package com.example.acer.chatapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.example.acer.chatapp.Adapter.UserAdapter;
import com.example.acer.chatapp.R;
import com.example.acer.chatapp.model.Chat;
import com.example.acer.chatapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class chatFragment extends Fragment {

    protected RecyclerView recyclerView;
private List<User> mUser;
FirebaseUser fuser;
DatabaseReference reference;
private List<String> userlist;
private UserAdapter userAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView=view.findViewById(R.id.recycle);


        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true);

        fuser=FirebaseAuth.getInstance().getCurrentUser();
        userlist=new ArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Chat chat =snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(fuser.getUid()) ) {
                        if(userlist.indexOf(chat.getReceiver()) <0)
                        {
                            userlist.add(chat.getReceiver());
                        }

                    }
                    if (chat.getReceiver().equals(fuser.getUid()) ) {
                        if(userlist.indexOf(chat.getSender()) <0)
                        {
                            userlist.add(chat.getSender());
                        }

                    }
                }
                readChat();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


        return view;
    }


public void readChat(){

        mUser=new CopyOnWriteArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                User user=snapshot.getValue(User.class);


                    for(String id:userlist)
                    {
                       if(user.getId().equals(id))
                        {
                           /*   if(mUser.size() !=0) {
                                for(User user1: mUser) {
                                    if(!user.getId().equals(user1.getId())){
                                        mUser.add(user);
                                    }
                                }
                            }
                            else
                            {
                                mUser.add(user);

                            }  */
                           mUser.add(user);
                        }

                    }
                }


                userAdapter=new UserAdapter(getContext(),mUser,true );
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}

}
