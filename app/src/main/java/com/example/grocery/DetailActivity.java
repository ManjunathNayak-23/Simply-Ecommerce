package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocery.helperclass.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {
    TextView brand, model, price, size, description;
    ImageView productImage;
    ImageView color;
    TextView wishlist;
    boolean wish = true;
    FirebaseAuth firebaseAuth;
    DatabaseReference favourietesreference, cartReference;
    int num;
    String path;
    private ArrayList<String> values = new ArrayList<>();
    private String brandName;
    private String modelName;
    private String pricec;
    private String image;
    private String productColor;
    private String productSize;
    private String category;
    private String descriptionc;
    Button addToCartBtn;
    int orderId;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        brand = findViewById(R.id.brand);
        model = findViewById(R.id.model);
        price = findViewById(R.id.priceCard);
        color = findViewById(R.id.colorCard);
        size = findViewById(R.id.sizeCard);
        description = findViewById(R.id.description);
        productImage = findViewById(R.id.img);
        wishlist = findViewById(R.id.like_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        addToCartBtn = findViewById(R.id.addCart);
        Random r = new Random();
        orderId = r.nextInt(10000 - 9000) + 9000;
        final String userId = firebaseAuth.getCurrentUser().getUid();
        favourietesreference = FirebaseDatabase.getInstance().getReference("Favourites").child(userId);
        cartReference = FirebaseDatabase.getInstance().getReference("Cart").child(userId);
        Intent intent = getIntent();
        num = Dashboard.nameOfPage;
        if (num == 0) {
            path = "Tshirts";
        }
        if (num == 1) {
            path = "Hoodie";
        }
        if (num == 2) {
            path = "Jackets";
        }
        if (num == 3) {
            path = "Accessories";
        }
        final String key = intent.getStringExtra("key");
        assert key != null;
Log.i("keyvalue",key);
Log.i("pathvalue",path);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path).child(key);
        FavouritesCheck(key);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Upload upload = dataSnapshot.getValue(Upload.class);

                assert upload != null;
                brand.setText(upload.getBrandName());
                model.setText(upload.getModelName());
                price.setText(upload.getPrice());
                size.setText(upload.getProductSize());
                description.setText(upload.getDescription());
                Glide.with(getApplicationContext()).load(upload.getImage()).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(productImage);
                Glide.with(getApplicationContext()).load(upload.getImage()).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).override(600, 200).into(color);
                brandName = upload.getBrandName();
                modelName = upload.getModelName();
                pricec = upload.getPrice();
                image = upload.getImage();
                productColor = upload.getProductColor();
                productSize = upload.getProductSize();
                category = upload.getCategory();
                descriptionc = upload.getDescription();
                cartReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String brandCheck = snapshot.child("Brand").getValue(String.class);
                            String imageURLCHECk = snapshot.child("ImageUrl").getValue(String.class);
                            assert brandCheck != null;
                            assert imageURLCHECk != null;
                            if (brandCheck.contains(brandName) && imageURLCHECk.contains(image)) {
                                addToCartBtn.setText("Added to Cart");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wish) {
                    wishlist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bookmarked, 0);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("type", path);
                    map.put("brandName", brandName);
                    map.put("modelName", modelName);
                    map.put("price", pricec);
                    map.put("image", image);
                    map.put("productColor", productColor);
                    map.put("productSize", productSize);
                    map.put("category", category);
                    map.put("description", descriptionc);
                    favourietesreference.child(key).setValue(map);
                    wish = false;


                } else {

                    wishlist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.unbookmart, 0);
                    favourietesreference.child(key).removeValue();
                    wish = true;
                    FavouritesCheck(key);

                }


            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = 1;
                Log.i("CartCheck", String.valueOf(1));
                HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("Brand", brandName);
                cartMap.put("ImageUrl", image);
                cartMap.put("Color", productColor);
                cartMap.put("Size", productSize);
                cartMap.put("Price", pricec);
                cartMap.put("Quantity", quantity);
                cartReference.child(String.valueOf(orderId)).setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DetailActivity.this, "ADDED TO CART ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "UNABLE TO ADD TO CART.!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }


    private void FavouritesCheck(final String key) {

        favourietesreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                values.clear();
                Log.i("entered", key);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    values.add(snapshot.getKey());
                    Log.i("values", values.toString());

                }

                for (int i = 0; i < values.size(); i++) {
                    if (values.contains(key)) {
                        wishlist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bookmarked, 0);
                    } else {
                        wishlist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.unbookmart, 0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}