package com.example.grocery.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.Fragments.CartFragment;
import com.example.grocery.R;
import com.example.grocery.helperclass.CartModel;
import com.example.grocery.profile.ShippingAddressFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class GpayFragment extends Fragment {


    public GpayFragment() {
        // Required empty public constructor
    }
ImageView googlePayBtn;
    String amount,name="Simply Shop",upiId="manjunathnayak720@okicici",transactionNote="pay test",status;
    TextView price;
    Uri uri;
    DatabaseReference orderReferenceOfUser,orderReferenceOfAdmin;

    int totalamount= CartFragment.totalAmount;
    public  static final  String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    Context thiscontext;
    ArrayList<CartModel> itemlist=CartFragment.model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_gpay, container, false);
        googlePayBtn=v.findViewById(R.id.google_pay_btn);
        price=v.findViewById(R.id.price);
        thiscontext=container.getContext();
        price.setText(String.valueOf(totalamount));
        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        orderReferenceOfUser = FirebaseDatabase.getInstance().getReference("YourOrder").child(userId);
        orderReferenceOfAdmin = FirebaseDatabase.getInstance().getReference("AdminOrders").child(userId);

        googlePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=String.valueOf(totalamount);

                if(!amount.isEmpty()){
                    uri=getUpiPaymentUri(name,upiId,transactionNote,amount);
                    paWithGpay();
                    orderReferenceOfUser.setValue(itemlist);
                    orderReferenceOfAdmin.setValue(itemlist);
                }

            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void paWithGpay() {
        if (isAppInstalled(thiscontext,GOOGLE_PAY_PACKAGE_NAME)){
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            startActivityForResult(intent,GOOGLE_PAY_REQUEST_CODE);
        }else {
            Toast.makeText(thiscontext, "Gpay is not installed in your Phone", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data !=null){
            status=data.getStringExtra("status").toLowerCase();
        }
        if((Activity.RESULT_OK==resultCode)&&status.equals("success")){
            Toast.makeText(thiscontext, "Transaction Successful", Toast.LENGTH_SHORT).show();
            orderReferenceOfUser.setValue(itemlist);
            orderReferenceOfAdmin.setValue(itemlist);
            Toast.makeText(thiscontext, "your Order has been added", Toast.LENGTH_SHORT).show();
            Fragment fragment=new OrderFinished();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }else {
            Toast.makeText(thiscontext, "Transaction Failed", Toast.LENGTH_SHORT).show();

        }

    }

    private static boolean isAppInstalled(Context context, String packageName) {
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }

    private static Uri getUpiPaymentUri(String name,String upiId,String transactionNote,String amount) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",transactionNote)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR")
                .build();
    }
}