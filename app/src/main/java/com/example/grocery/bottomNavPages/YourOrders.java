package com.example.grocery.bottomNavPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.Dashboard;
import com.example.grocery.R;
import com.example.grocery.helperclass.CartModel;
import com.example.grocery.helperclass.YourOrderAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourOrders extends AppCompatActivity {
    RecyclerView orderRecycler;
    DatabaseReference reference;
    ArrayList<CartModel> items = new ArrayList<>();
TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_your_orders);
        orderRecycler = findViewById(R.id.orderrecycler);
        orderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderRecycler.setHasFixedSize(true);
        text=findViewById(R.id.text);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("YourOrder").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    text.setVisibility(View.GONE);


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CartModel model = snapshot.getValue(CartModel.class);
                        items.add(model);

                    }
                }
                else{
                    text.setVisibility(View.VISIBLE);

                }
                YourOrderAdapter adapter = new YourOrderAdapter(items, getApplicationContext());
                orderRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.address);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent2);
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

                        return true;


                }
                return false;
            }

        });



    }
}