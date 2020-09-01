package com.example.grocery.bottomNavPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.grocery.Fragments.CartFragment;
import com.example.grocery.Dashboard;
import com.example.grocery.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);
        Fragment selected=new CartFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, selected).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent3 = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.heart:
                        Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.cart:

                        return true;
                    case R.id.address:
                        Intent intent2 = new Intent(getApplicationContext(), YourOrders.class);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profileicon:
                        Intent intent1 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }

        });

    }

        }