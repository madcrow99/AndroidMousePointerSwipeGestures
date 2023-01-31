package com.example.testgest;

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GestureDetectorCompat;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;


import java.util.ArrayDeque;
import java.util.Deque;

public class GlobalActionBarService extends AccessibilityService {
    FrameLayout mLayout;
    float width= Resources.getSystem().getDisplayMetrics().widthPixels;
    float height=Resources.getSystem().getDisplayMetrics().heightPixels;
    @Override
    protected void onServiceConnected() {
        // Create an overlay and display the action bar
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayout = new FrameLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_main, mLayout);
        wm.addView(mLayout, lp);


       // configureScrollButton();
        configureSwipeButtonUp();
        configureSwipeButtonDown();
        configureSwipeButtonLeft();
        configureSwipeButtonRight();
    }




    @Override
    public void onInterrupt() {

    }

    private AccessibilityNodeInfo findScrollableNode(AccessibilityNodeInfo root) {
        Deque<AccessibilityNodeInfo> deque = new ArrayDeque<>();
        deque.add(root);
        while (!deque.isEmpty()) {
            AccessibilityNodeInfo node = deque.removeFirst();
            if (node.getActionList().contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD)) {
                return node;
            }
            for (int i = 0; i < node.getChildCount(); i++) {
                deque.addLast(node.getChild(i));
            }
        }
        return null;
    }


    private void configureSwipeButtonDown() {
        Button swipeButton = (Button) mLayout.findViewById(R.id.swipedw);

        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Path swipePath = new Path();
                swipePath.moveTo((float)((int)(width*0.5)), (float)((int)(height*0.2)));
                swipePath.lineTo((float)((int)(width*0.5)), (float)((int)(height*0.9)));
                GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
                gestureBuilder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 500));
                dispatchGesture(gestureBuilder.build(), null, null);
            }
        });
    }

    private void configureSwipeButtonUp() {
        final Button swipeButton = (Button) mLayout.findViewById(R.id.swipeup);

        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Path swipePath = new Path();
                swipePath.moveTo((float)((int)(width*0.5)), (float)((int)(height*0.8)));
                swipePath.lineTo((float)((int)(width*0.5)), (float)((int)(height*0.05)));
                GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
                gestureBuilder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 700));
                dispatchGesture(gestureBuilder.build(), null, null);
                }

            });
    }
    private void configureSwipeButtonLeft () {
        Button swipeButton = (Button) mLayout.findViewById(R.id.swipeLeft);

        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Path swipePath = new Path();
                swipePath.moveTo((float)((int)(width*0.95)), (float)((int)(height*0.5)));
                swipePath.lineTo((float)((int)(width*0.05)), (float)((int)(height*0.5)));
                GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
                gestureBuilder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 500));
                dispatchGesture(gestureBuilder.build(), null, null);
            }
        });
    } private void configureSwipeButtonRight () {
        Button swipeButton = (Button) mLayout.findViewById(R.id.swipeRight);

        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Path swipePath = new Path();
                swipePath.moveTo((float)((int)(width*0.05)), (float)((int)(height*0.5)));
                swipePath.lineTo((float)((int)(width*0.95)), (float)((int)(height*0.5)));
                GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
                gestureBuilder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 500));
                dispatchGesture(gestureBuilder.build(), null, null);
            }
        });
    }


        @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


    }
}