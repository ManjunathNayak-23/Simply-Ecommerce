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


public class Accesories extends Fragment {

    DatabaseReference reference;
    ArrayList<ProductModel> accesoriesModel;
    ValueEventListener listener;
    public Accesories() {
        // Required empty public constructor
    }

    RecyclerView accesoriesRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_accesories,container,false);
        accesoriesRecycler=view.findViewById(R.id.jacketRecycler);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        accesoriesRecycler.setLayoutManager(manager);
        accesoriesRecycler.setHasFixedSize(true);

        accesoriesModel=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Accessories");
       listener= reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accesoriesModel.clear();
                for(DataSnapshot postSNapShot:dataSnapshot.getChildren()){
                    String key=postSNapShot.getKey();
                    ProductModel productModel=postSNapShot.getValue(ProductModel.class);
                    productModel.setKey(key);
                    accesoriesModel.add(productModel);
                }

                ProductRecyclerView adapter=new ProductRecyclerView(accesoriesModel,getContext());

                accesoriesRecycler.setAdapter(adapter);
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