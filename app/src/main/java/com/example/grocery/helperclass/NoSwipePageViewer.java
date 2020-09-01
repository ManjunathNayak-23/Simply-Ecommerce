package com.example.grocery.helperclass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NoSwipePageViewer extends ViewPager {

        private Boolean disable = false;
        public NoSwipePageViewer(Context context) {
            super(context);
        }
        public NoSwipePageViewer(Context context, AttributeSet attrs){
            super(context,attrs);
        }
        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            return !disable && super.onInterceptTouchEvent(event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return !disable && super.onTouchEvent(event);
        }

        public void disableScroll(Boolean disable){
            //When disable = true not work the scroll and when disble = false work the scroll
            this.disable = disable;
        }

}