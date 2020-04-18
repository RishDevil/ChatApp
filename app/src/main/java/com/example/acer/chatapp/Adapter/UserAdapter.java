package com.example.acer.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acer.chatapp.MessageActivity;
import com.example.acer.chatapp.R;
import com.example.acer.chatapp.chat;
import com.example.acer.chatapp.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContex;
    private List<User> mUser;
    private  boolean isChat;

    public UserAdapter(Context mcontext,List<User> mUser,boolean isChat)
    {
        this.mContex=mcontext;
        this.mUser=mUser;
        this.isChat=isChat;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContex).inflate(R.layout.user_item, viewGroup,false);

        return new UserAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user=mUser.get(i);
        viewHolder.username.setText(user.getUsername());
        if(user.getImageurl().equals("Default"))
        {

           viewHolder.profile_img.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mContex).load(user.getImageurl()).into(viewHolder.profile_img);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContex,MessageActivity.class);
                intent.putExtra("user",user.getId());
             //   Toast.makeText(mContex, (CharSequence) user.getId(),Toast.LENGTH_LONG).show();
                mContex.startActivity(intent);

            }
        });


        if(isChat)
        {
            if(user.getStatus().equals("online"))
            {
                viewHolder.userStatuson.setVisibility(View.VISIBLE);
                viewHolder.userStatusoff.setVisibility(View.GONE);
            }
            else{
                viewHolder.userStatusoff.setVisibility(View.VISIBLE);
                viewHolder.userStatuson.setVisibility(View.GONE);



            }
        }

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
          public ImageView profile_img;
          public CircleImageView userStatuson;
        public CircleImageView userStatusoff;

        public ViewHolder(View itemView){
            super(itemView);
            username=itemView.findViewById(R.id.username);
            profile_img=itemView.findViewById(R.id.profile_img);
            userStatuson=itemView.findViewById(R.id.statuson);
            userStatusoff=itemView.findViewById(R.id.statusoff);

        }

    }
}
