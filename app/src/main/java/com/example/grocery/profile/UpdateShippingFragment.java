package com.example.grocery.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.helperclass.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.watson.assistant.v1.model.Intent;


public class UpdateShippingFragment extends Fragment {

    public UpdateShippingFragment() {
        // Required empty public constructor
    }
    EditText landmark, state, city, phoneNumber, houseNumber, name;
    Button update;
    DatabaseReference reference;
    String userid;
    View v;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_update_shipping, container, false);
        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Address").child(userid);
        landmark=v.findViewById(R.id.landmark);
        state=v.findViewById(R.id.statename);
        city=v.findViewById(R.id.cityname);
        phoneNumber=v.findViewById(R.id.phone);
        houseNumber=v.findViewById(R.id.houseno);
        name=v.findViewById(R.id.nameadd);
        update=v.findViewById(R.id.update);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    AddressModel model=snapshot.getValue(AddressModel.class);
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateName = state.getText().toString();
                String cityName = city.getText().toString();
                String phoneNum = phoneNumber.getText().toString();
                String houseNum = houseNumber.getText().toString();
                String Name = name.getText().toString();
                String Land = landmark.getText().toString();
                final String user= FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (stateName.isEmpty() || cityName.isEmpty() || phoneNum.isEmpty() || houseNum.isEmpty() || Name.isEmpty() || Land.isEmpty()) {
                    Toast.makeText(v.getContext(), "All Field Required", Toast.LENGTH_LONG).show();
                } else {
                    AddressModel model=new AddressModel(Name,cityName,stateName,Land,phoneNum,houseNum);
                 DatabaseReference   addreference= FirebaseDatabase.getInstance().getReference("Address").child(user);
                    addreference.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                update.setText("Added/Updated Address");
                                Toast.makeText(getActivity(), "Updated Address", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getActivity(), "Unable to update", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

        return v;
    }
}