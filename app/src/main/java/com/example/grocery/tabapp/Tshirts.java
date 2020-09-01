package com.example.grocery.tabapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.helperclass.ProductModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Tshirts extends Fragment {
    DatabaseReference reference;
    ArrayList<ProductModel> tshirtmodel;
    ValueEventListener listener;


    public Tshirts() {
        // Required empty public constructor
    }

    RecyclerView tShirtRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tshirts, container, false);
        tShirtRecycler = view.findViewById(R.id.tshirtsRecycler);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        tShirtRecycler.setLayoutManager(manager);
        tShirtRecycler.setHasFixedSize(true);

        final ProgressBar progressBar = view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new RotatingCircle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        tshirtmodel = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Tshirts");
      listener=  reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tshirtmodel.clear();
                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot postSNapShot : dataSnapshot.getChildren()) {
                    String key = postSNapShot.getKey();
                    ProductModel productModel = postSNapShot.getValue(ProductModel.class);
                    productModel.setKey(key);
                    tshirtmodel.add(productModel);
                }
                progressBar.setVisibility(View.GONE);
                ProductRecyclerView adapter = new ProductRecyclerView(tshirtmodel, getContext());

                tShirtRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reference.removeEventListener(listener);
    }
}