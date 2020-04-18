package com.example.acer.chatapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acer.chatapp.R;
import com.example.acer.chatapp.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private Context mContex;
    private List<Chat> mChat;
    private String imageUrl;
    FirebaseUser fuser;

    public MessageAdapter(Context mcontext,List<Chat> mChat,String imageUrl)
    {
        this.mContex=mcontext;
        this.mChat=mChat;
        this.imageUrl=imageUrl;
    }




    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == MSG_TYPE_RIGHT){
            View view=LayoutInflater.from(mContex).inflate(R.layout.chat_item_right, viewGroup,false);
            Toast.makeText(mContex,"message adpater",Toast.LENGTH_LONG).show();
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view=LayoutInflater.from(mContex).inflate(R.layout.chat_item_left, viewGroup,false);

            return new MessageAdapter.ViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {

Chat chat=mChat.get(i);
viewHolder.show_message.setText(chat.getMessage());
        if(imageUrl.equals("Default"))
        {

            viewHolder.profile_img.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mContex).load(imageUrl).into(viewHolder.profile_img);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_img;

        public ViewHolder(View itemView){
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
            profile_img=itemView.findViewById(R.id.profile_img);

        }

    }

    @Override
    public int getItemViewType(int position) {
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
