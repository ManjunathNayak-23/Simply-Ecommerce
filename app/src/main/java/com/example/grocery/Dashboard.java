package com.example.grocery;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.grocery.bottomNavPages.CartActivity;
import com.example.grocery.bottomNavPages.FavouritesActivity;
import com.example.grocery.bottomNavPages.ProfileActivity;
import com.example.grocery.bottomNavPages.YourOrders;
import com.example.grocery.chatbot.ChatbotMainActivity;
import com.example.grocery.faq.About;
import com.example.grocery.faq.Faq;
import com.example.grocery.helperclass.NoSwipePageViewer;
import com.example.grocery.tabapp.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    PagerAdapter pagerAdapter;
    static int nameOfPage;
    DrawerLayout drawerLayout;
    FloatingActionButton back;
    DatabaseReference reference;
    String userid;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        NavigationView navigationView = findViewById(R.id.navgationview);

        navigationView.setItemIconTintList(null);
        final TextView nametextview = navigationView.getHeaderView(0).findViewById(R.id.usernamenav);
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Log.i("snap", String.valueOf(snapshot1.getValue()));
                    if (snapshot1.getKey().equals("username")) {
                        name = snapshot1.getValue(String.class);
                        Log.i("name", String.valueOf(name));
                    } else {
                        name = "User";
                    }
                    nametextview.setText(name);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        drawerLayout = findViewById(R.id.drawer);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.navicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabItem tabTshirts = findViewById(R.id.tshirts);
        TabItem tabHoodie = findViewById(R.id.hoodie);
        TabItem tabJackets = findViewById(R.id.jackets);
        TabItem tabAccesories = findViewById(R.id.Accesories);
        final NoSwipePageViewer viewPager = findViewById(R.id.viewPager);

        viewPager.animate().alpha(1).setDuration(500);
        viewPager.disableScroll(true);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        nameOfPage = 0;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:

                        return true;


                    case R.id.heart:
                        Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.cart:
                        Intent intent3 = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.profileicon:
                        Intent intent1 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.address:
                        Intent intent2 = new Intent(getApplicationContext(), YourOrders.class);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }

        });

        pagerAdapter = new

                PagerAdapter(getSupportFragmentManager(), tabLayout.

                getTabCount());


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.i("position", String.valueOf(tab.getPosition()));
                nameOfPage = tab.getPosition();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setAdapter(pagerAdapter);
        //  tabLayout.setupWithViewPager(viewPager);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
               startActivity(intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.help:
                Intent intent = new Intent(getApplicationContext(), ChatbotMainActivity.class);
                startActivity(intent);
                return true;
            case R.id.logoutnav:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                finish();
                return true;
            case R.id.ratethisapp:
                final Dialog rankDialog = new Dialog(Dashboard.this);
                rankDialog.setContentView(R.layout.ranklayout);
                rankDialog.setCancelable(true);
                final RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
                SharedPreferences settings = getSharedPreferences("Ratings", 0);
                int rate = settings.getInt("userRate", 2);
                ratingBar.setRating(rate);
                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings = getSharedPreferences("Ratings", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("userRate", ratingBar.getNumStars());
                        editor.commit();
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
                return true;
            case R.id.faqnav:
                startActivity(new Intent(getApplicationContext(), Faq.class));
                return true;
            case R.id.about:
                startActivity(new Intent(getApplicationContext(), About.class));
                return true;

        }
        return false;
    }


}