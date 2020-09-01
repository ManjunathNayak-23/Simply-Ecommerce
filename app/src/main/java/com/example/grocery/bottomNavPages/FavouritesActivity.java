package com.example.grocery.bottomNavPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.Dashboard;
import com.example.grocery.R;
import com.example.grocery.helperclass.ProductModel;
import com.example.grocery.tabapp.ProductRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    RecyclerView favouritesRecycler;

    DatabaseReference reference;
    ArrayList<ProductModel> favModel;
    TextView noFavAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_favourites);
        favouritesRecycler = findViewById(R.id.favRecycler);
        favouritesRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        favouritesRecycler.setHasFixedSize(true);
        noFavAdded=findViewById(R.id.NoFav);
      FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
        final String userId = firebaseAuth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Favourites").child(userId);
        favModel=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favModel.clear();

                for(DataSnapshot postSNapShot:dataSnapshot.getChildren()){
                    String key=postSNapShot.getKey();
                    ProductModel productModel=postSNapShot.getValue(ProductModel.class);
                    productModel.setKey(key);
                    favModel.add(productModel);
                }
                if(favModel.isEmpty()){
                    noFavAdded.setVisibility(View.VISIBLE);
                }

                ProductRecyclerView adapter=new ProductRecyclerView(favModel,getApplicationContext());

                favouritesRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.heart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.heart:
                        return true;
                    case R.id.cart:
                        Intent intent1 = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.profileicon:
                        Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.address:
                        Intent intent3 = new Intent(getApplicationContext(), YourOrders.class);
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }

        });
    }
}