package com.example.grocery.tabapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocery.DetailActivity;
import com.example.grocery.R;
import com.example.grocery.helperclass.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductRecyclerView extends RecyclerView.Adapter<ProductRecyclerView.productRecyclerViewHolder> {
    ArrayList<ProductModel> model;
    Context context;
    DatabaseReference firebaseDatabase;
    int number;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final String userId = firebaseAuth.getCurrentUser().getUid();
    DatabaseReference favourietesreference = FirebaseDatabase.getInstance().getReference("Favourites").child(userId);
    String path;
    private ArrayList<String> values = new ArrayList<>();


    public ProductRecyclerView(ArrayList<ProductModel> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public productRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new ProductRecyclerView.productRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final productRecyclerViewHolder holder, int position) {
        final ProductModel Assign = model.get(position);
        Glide.with(context).load(Assign.getImage()).skipMemoryCache(false).placeholder(R.drawable.tshirt).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.productImage);
        holder.Brand.setText(Assign.getBrandName());
        holder.model.setText(Assign.getModelName());
        holder.price.setText(String.valueOf(Assign.getPrice()));
        number = PagerAdapter.hello;
        CheckFavourites(Assign.getKey(), holder.likeButton);
        Log.i("page", String.valueOf(number));
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
                HashMap<String, Object> map = new HashMap<>();
                // map.put("key",key);
                map.put("type", Assign.getBrandName());
                map.put("brandName", Assign.getBrandName());
                map.put("modelName", Assign.getModelName());
                map.put("price", Assign.getPrice());
                map.put("image", Assign.getImage());
                map.put("productColor", Assign.getProductColor());
                map.put("productSize", Assign.getProductSize());
              //  map.put("category", Assign.get);
                map.put("description", Assign.getDescription());
                favourietesreference.child(Assign.getKey()).setValue(map);


            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                favourietesreference.child(Assign.getKey()).removeValue();


            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 0) {
                    path = "Tshirts";
                }
                if (number == 1) {
                    path = "Hoodie";
                }
                if (number == 2) {
                    path = "Jackets";
                }
                if (number == 3) {
                    path = "Accessories";
                }


                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                firebaseDatabase = FirebaseDatabase.getInstance().getReference(path);
                String key = Assign.getKey();
                intent.putExtra("key", key);

                v.getContext().startActivity(intent);
            }
        });

    }

    private void CheckFavourites(final String key, final LikeButton likeButton) {


        favourietesreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                values.clear();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    values.add(snapshot.getKey());


                }

                for (int i = 0; i < values.size(); i++) {
                    if (values.contains(key)) {
                        likeButton.setLiked(true);

                    } else {
                        likeButton.setLiked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class productRecyclerViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView productImage;
        TextView Brand;
        TextView model;
        TextView price;
        LikeButton likeButton;

        public productRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.imagecard);
            Brand = itemView.findViewById(R.id.brand);
            model = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.priceCard);
            likeButton = itemView.findViewById(R.id.fav);
            card = itemView.findViewById(R.id.card);

        }
    }
}
