package com.example.grocery.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocery.Fragments.GpayFragment;
import com.example.grocery.R;
import com.example.grocery.helperclass.AddressModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ShippingAddressFragment extends Fragment {
    EditText landmark, state, city, phoneNumber, houseNumber, name;
    Button checkout;
   DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shipping_address, container, false);

        state = v.findViewById(R.id.statename);
        city = v.findViewById(R.id.cityname);
        phoneNumber = v.findViewById(R.id.phone);
        houseNumber = v.findViewById(R.id.houseno);
        name = v.findViewById(R.id.nameadd);
        checkout = v.findViewById(R.id.checkout);
        landmark = v.findViewById(R.id.landmark);
        final String user= FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Address").child(user);
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        if (snapshot.exists()) {
            AddressModel model = snapshot.getValue(AddressModel.class);
            assert model != null;
            landmark.setText(model.getLandMark());
            state.setText(model.getStateName());
            city.setText(model.getCityName());
            phoneNumber.setText(model.getPhoneNumber());
            houseNumber.setText(model.getHouseNumber());
            name.setText(model.getName());
        }else{

            landmark.setText("");
            state.setText("");
            city.setText("");
            phoneNumber.setText("");
            houseNumber.setText("");
            name.setText("");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



            checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateName = state.getText().toString();
                String cityName = city.getText().toString();
                String phoneNum = phoneNumber.getText().toString();
                String houseNum = houseNumber.getText().toString();
                String Name = name.getText().toString();
                String Land = landmark.getText().toString();
                if (stateName.isEmpty() || cityName.isEmpty() || phoneNum.isEmpty() || houseNum.isEmpty() || Name.isEmpty() || Land.isEmpty()) {
                    Toast.makeText(v.getContext(), "All Field Required", Toast.LENGTH_LONG).show();

                } else {
                    AddressModel model=new AddressModel(Name,cityName,stateName,Land,phoneNum,houseNum);

                    reference.setValue(model);
                    Fragment fragment=new GpayFragment();
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }
        });


        return v;
    }
}