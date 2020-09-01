package com.example.grocery.helperclass;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder> {
    ArrayList<CartModel> model;
    Context context;
    DatabaseReference reference;
    FirebaseAuth auth;


    int quantity;

    public CartAdapter(ArrayList<CartModel> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);

        return new CartAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapterViewHolder holder, int position) {
        auth=FirebaseAuth.getInstance();
        String userId=auth.getCurrentUser().getUid();
        reference=FirebaseDatabase.getInstance().getReference("Cart").child(userId);
        final CartModel items = model.get(position);
        holder.brand.setText(items.getBrand());
        holder.price.setText(items.getPrice());
        holder.color.setText(items.getColor());
        holder.size.setText(items.getSize());
        Glide.with(context).load(items.getImageUrl()).into(holder.image);
        holder.quantity.setText(String.valueOf(items.getQuantity()));
        Log.i("quantity",String.valueOf(quantity));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Limit is 1 For Now", Toast.LENGTH_SHORT).show();


            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Count Can't Be Less Than 1", Toast.LENGTH_SHORT).show();


            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyvalue=items.getKey();
                reference.child(keyvalue).removeValue();
            }
        });


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class CartAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView brand, price, color, size, quantity;
        ImageView image;
        TextView minus, plus;
        ImageView remove;

        public CartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.BrandCard);
            price = itemView.findViewById(R.id.priceCard);
            color = itemView.findViewById(R.id.colorCard);
            size = itemView.findViewById(R.id.sizeCard);
            image = itemView.findViewById(R.id.imagecard);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            quantity = itemView.findViewById(R.id.quantity);
            remove=itemView.findViewById(R.id.remove);


        }
    }
}
