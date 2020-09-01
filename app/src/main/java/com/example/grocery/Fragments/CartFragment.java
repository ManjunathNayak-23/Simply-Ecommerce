package com.example.grocery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.grocery.R;
import com.example.grocery.helperclass.CartAdapter;
import com.example.grocery.helperclass.CartModel;
import com.example.grocery.profile.ShippingAddressFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    RecyclerView cartRecyclerView;
    CartAdapter Adapter;
   static ArrayList<CartModel> model = new ArrayList<>();
    DatabaseReference reference;
    // DatabaseReference priceReference;
    FirebaseAuth auth;
    private ValueEventListener mDBListener;
   static int totalAmount;
    TextView price;
    ProgressBar progressBar;
    ArrayList<Integer> amount = new ArrayList<>();
    Button addShippingBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_cart, container, false);

        addShippingBtn=v.findViewById(R.id.addCart);

        cartRecyclerView = v.findViewById(R.id.cartRecycler);
        cartRecyclerView.setHasFixedSize(true);
        auth = FirebaseAuth.getInstance();
        price = v.findViewById(R.id.priceCard);
        progressBar =v. findViewById(R.id.progress);
        String user = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Cart").child(user);


addShippingBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Fragment fragment=new ShippingAddressFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
});
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDBListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model.clear();
                amount.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    CartModel models = snapshot.getValue(CartModel.class);
                    models.setKey(snapshot.getKey());
                    model.add(models);

                }
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    totalAmount=0;
                    String value = snapshot.child("Price").getValue(String.class);

                    amount.add(Integer.parseInt(value));

                    for (int i = 0; i < amount.size(); i++) {
                        totalAmount += amount.get(i);
                        Log.i("totalAmount", String.valueOf(totalAmount));
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        double progress = (100.0 * totalAmount / 10000);
                        progressBar.setProgress((int) progress);

                        Log.i("progress", String.valueOf(progress));


                    }
                }, 200);
                price.setText(String.valueOf(totalAmount));
                Adapter = new CartAdapter(model, getContext());
                cartRecyclerView.setAdapter(Adapter);
                if(amount.isEmpty()){
                    price.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reference.removeEventListener(mDBListener);
    }
}