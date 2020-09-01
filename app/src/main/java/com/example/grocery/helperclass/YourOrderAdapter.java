package com.example.grocery.helperclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocery.R;

import java.util.ArrayList;

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.YourOrderAdapterViewHolder> {
    ArrayList<CartModel> models;
    Context context;

    public YourOrderAdapter(ArrayList<CartModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public YourOrderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.yourorderscard,parent,false);

        return new YourOrderAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourOrderAdapterViewHolder holder, int position) {
        CartModel model=models.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.image);
        holder.brand.setText(model.getBrand());
        holder.price.setText(model.getPrice());
        holder.model.setText(model.getColor());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class YourOrderAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView brand,model,price;
        public YourOrderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.orderimage);
            brand=itemView.findViewById(R.id.orderbrand);
            model=itemView.findViewById(R.id.ordermodel);
            price=itemView.findViewById(R.id.orderprices);
        }
    }
}
