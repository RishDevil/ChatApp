package com.example.acer.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.acer.chatapp.fragments.chatFragment;
import com.example.acer.chatapp.fragments.profileFrgment;
import com.example.acer.chatapp.fragments.userFragment;
import com.example.acer.chatapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat extends AppCompatActivity {
    CircleImageView crv;
    TextView tx;
    FirebaseUser user;
    DatabaseReference ref;
    android.support.v7.widget.Toolbar toolbar;
    TabLayout tl;
    ViewPager vp;
    DatabaseReference reference;
    FirebaseUser fuser;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        crv=(CircleImageView)findViewById(R.id.crv);
        tx=(TextView)findViewById(R.id.txt);
        user=FirebaseAuth.getInstance().getCurrentUser();
        ref=FirebaseDatabase.getInstance().getReference("user").child(user.getUid());
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                tx.setText(user.getUsername());
                if(user.getImageurl().equals("D efault"))
                {
                    crv.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Glide.with(chat.this).load(user.getImageurl()).into(crv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    TabLayout tl=findViewById(R.id.tl);
    vp=(ViewPager)findViewById(R.id.vp);
    ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
    Fragment chat= new chatFragment();
    Fragment user=new userFragment();
    Fragment profile=new profileFrgment();

   viewPageAdapter.addFragment(chat,"Chats");
    viewPageAdapter.addFragment(user,"Users");
    viewPageAdapter.addFragment(profile,"Profile");
    vp.setAdapter(viewPageAdapter);




    tl.setupWithViewPager(vp);
    tl.setTabTextColors(ContextCompat.getColorStateList(this,R.color.colorPrimaryDark));

    tl.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorPrimary));
    tl.setTabRippleColor(ContextCompat.getColorStateList(this,R.color.colorPrimaryDark));
    tl.setTabGravity(tl.INDICATOR_GRAVITY_BOTTOM);

    }
    class ViewPageAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        ViewPageAdapter(FragmentManager fm){
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void status(String status){

        reference=FirebaseDatabase.getInstance().getReference("user").child(fuser.getUid());
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);

    }




    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }
}
