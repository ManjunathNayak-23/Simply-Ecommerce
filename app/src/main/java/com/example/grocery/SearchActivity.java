package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocery.helperclass.CartAdapter;
import com.example.grocery.helperclass.CartModel;
import com.example.grocery.helperclass.ProductModel;
import com.example.grocery.tabapp.ProductRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
EditText searchbox;
String searchText;
DatabaseReference reference;
RecyclerView searchrecycler;
Button button;
ArrayList<ProductModel> model=new ArrayList<>();
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        searchbox=findViewById(R.id.searchedit);
        button=findViewById(R.id.button2);
        textView=findViewById(R.id.textview23);
        searchrecycler=findViewById(R.id.searchrecycler);
        searchrecycler.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= searchbox.getText().toString();
                Fetch(name);
            }
        });




    }

    private void Fetch(String text) {
        model.clear();
        Log.i("entered","enter");
        reference= FirebaseDatabase.getInstance().getReference("AllItems").child("AllItems");
        Query firebaseQuery=reference.orderByChild("brandName").startAt(text).endAt(text + "\uf8ff");
        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    textView.setVisibility(View.INVISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {


                        ProductModel modelitem = snapshot1.getValue(ProductModel.class);

                        model.add(modelitem);


                    }
                    ProductRecyclerView adapter = new ProductRecyclerView(model, getApplicationContext());
                    searchrecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    textView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}