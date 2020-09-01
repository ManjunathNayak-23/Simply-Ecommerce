package com.example.grocery.faq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.grocery.R;
import com.example.grocery.helperclass.FaqModel;

import java.util.ArrayList;

public class Faq extends AppCompatActivity {
RecyclerView faq;
ArrayList<FaqModel> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_faq);
        faq=findViewById(R.id.faq_recycler);
        faq.setLayoutManager(new LinearLayoutManager(this));
        faq.setHasFixedSize(true);
        FaqData();
        FaqAdapter adapter=new FaqAdapter(items);
        faq.setAdapter(adapter);



    }

    private void FaqData() {
        items.add(new FaqModel("\u2022 What is Simply Shop","SIMPLY SHOP is online fashion shopping app, that can help you to explore and shopping on your smartphone easily."));
        items.add(new FaqModel("\u2022 What are the Payments methods available","We Accept only GooglePay For now. We will add other payment gateways soon.!"));
        items.add(new FaqModel("\u2022 The delivery of my order is delayed. What should I do?","On the rare occasion that your order is delayed, please check your email & messages for updates. A new delivery timeframe will be shared with you and you can also track its status by visiting My Orders."));
        items.add(new FaqModel("\u2022 If I request for a replacement, when will I get it?","In most locations, the replacement item is delivered to you at the time of pick-up. In all other areas, the replacement is initiated after the originally delivered item is picked up. Please check the email we send you for your replacement request for more details."));
        items.add(new FaqModel("\u2022 Is it necessary to have an account to shop on Simply Shop?","Yes, it's necessary to log into your SimplyShop account to shop. Shopping as a logged-in user is fast & convenient and also provides extra security.\n" +
                "\n" +
                "You'll have access to a personalised shopping experience including recommendations and quicker check-out."));
    }
}