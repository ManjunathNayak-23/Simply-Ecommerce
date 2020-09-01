package com.example.grocery.bottomNavPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.grocery.Dashboard;
import com.example.grocery.helperclass.NoSwipePageViewer;
import com.example.grocery.R;
import com.example.grocery.profile.ProfilePagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    ProfilePagerAdapter adapter;
    TextView Name;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        final TabLayout tabLayout=findViewById(R.id.tabLayout2);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profileicon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.heart:
                        Intent intent1 = new Intent(getApplicationContext(), FavouritesActivity.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        Intent intent3 = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.address:
                        Intent intent2 = new Intent(getApplicationContext(), YourOrders.class);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profileicon:

                        return true;

                }
                return true;
            }

        });
        Name=findViewById(R.id.nameOfUser);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if (snapshot.getKey().equals("username")) {
                        Name.setText(snapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final NoSwipePageViewer viewer=findViewById(R.id.profilePageViewer);
        adapter = new

                ProfilePagerAdapter(getSupportFragmentManager(), tabLayout.

                getTabCount());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewer.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        viewer.setAdapter(adapter);
    }
}