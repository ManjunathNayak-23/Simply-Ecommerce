package com.example.grocery.chatbot;

import android.view.View;

/**
 * Created by VMac on 06/01/17.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}