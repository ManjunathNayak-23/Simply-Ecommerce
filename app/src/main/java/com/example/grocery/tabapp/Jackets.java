package com.example.grocery.tabapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.helperclass.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Jackets extends Fragment {
    DatabaseReference reference;
    ArrayList<ProductModel> jacketModel;
    ValueEventListener listener;


    public Jackets() {
        // Required empty public constructor
    }


    RecyclerView jacketRecycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_jackets,container,false);
        jacketRecycler=view.findViewById(R.id.jacketRecycler);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        jacketRecycler.setLayoutManager(manager);
        jacketRecycler.setHasFixedSize(true);

        jacketModel=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Jackets");
       listener= reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jacketModel.clear();
                for(DataSnapshot postSNapShot:dataSnapshot.getChildren()){
                    String key=postSNapShot.getKey();
                    ProductModel productModel=postSNapShot.getValue(ProductModel.class);
                    productModel.setKey(key);
                    jacketModel.add(productModel);
                }

                ProductRecyclerView adapter=new ProductRecyclerView(jacketModel,getContext());

                jacketRecycler.setAdapter(adapter);
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