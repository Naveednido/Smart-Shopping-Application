package com.example.smartshoppingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser mCurrentuser;
    private DatabaseReference mDatabaseReference;


    private ViewPager viewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout tabLayout;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            startActivity(new Intent(MainActivity.this , WelcomeScreen.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        mCurrentuser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentuser.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(current_uid);


        viewPager = findViewById(R.id.tab_pager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(MainActivity.this , PermissionActivity.class));


            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.NAV);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);


        final TextView userview = headerview.findViewById(R.id.user_name_nav_header);
        final CircleImageView profileimage = headerview.findViewById(R.id.profile_image_nav_header);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this  , Profile_Settings.class));
            }
        });



        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("image")) {

                    String f = Objects.requireNonNull(dataSnapshot.child("first_name").getValue()).toString();
                    String l = Objects.requireNonNull(dataSnapshot.child("last_name").getValue()).toString();
                    String image = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    userview.setText(f + " " + l);
                    Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.profile_image_dummy).into(profileimage);
                }
                else
                {
                    String f = Objects.requireNonNull(dataSnapshot.child("first_name").getValue()).toString();
                    String l = Objects.requireNonNull(dataSnapshot.child("last_name").getValue()).toString();
                    userview.setText(f + " " + l);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mSectionPagerAdapter);
        tabLayout = findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            startActivity(new Intent(MainActivity.this , Add_to_Cart.class));
        } else if (id == R.id.nav_QR_Scanner) {
            startActivity(new Intent(MainActivity.this , QR_Scanner.class));

        } else if (id == R.id.nav_setting) {

            startActivity(new Intent(MainActivity.this , Profile_Settings.class ));

        } else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_order_history)
        {
            startActivity(new Intent(MainActivity.this , Order_History.class));

        }else if (id == R.id.nav_sales_notification)
        {
            startActivity(new Intent(MainActivity.this , Sales_Notification.class));

        }
        else if (id == R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent main = new Intent(MainActivity.this,WelcomeScreen.class);
            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);
            finish();

        }
        else if (id == R.id.nav_complaint) {
            startActivity(new Intent(MainActivity.this , Complaints.class));

        }
        else if (id == R.id.nav_like_products)
        {
            Intent main = new Intent(MainActivity.this , Favourite_Products.class);

            startActivity(main);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
