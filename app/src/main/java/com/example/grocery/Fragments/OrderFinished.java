package com.example.grocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.grocery.Dashboard;
import com.example.grocery.R;


public class OrderFinished extends Fragment {


    public OrderFinished() {
        // Required empty public constructor
    }

Button home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_order_finished, container, false);
        home=v.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Dashboard.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}