package com.example.kapnakis_proodos;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        // Load fade in animation for background image
        Animation fadeInAnimation = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.fade_in
        );
        // Load slide in animation for text
        ScrollView scroll_view = findViewById(R.id.scroller);
        // Start animation on the scroll view to make it less opaque
        scroll_view.startAnimation(fadeInAnimation);
        LinearLayout text_layout = findViewById(R.id.text_layout);
        View view;
        int start_offset = 1000;
        for(int i = 0; i < text_layout.getChildCount(); i++){
            view = text_layout.getChildAt(i);
            Animation slideInAnimation = AnimationUtils.loadAnimation(
                    getApplicationContext(),
                    R.anim.slide_in
            );
            // Add offset time so that text appears after fade in animation stops
            slideInAnimation.setStartOffset(start_offset);
            start_offset += 50;
            view.startAnimation(slideInAnimation);
            // TODO: WHY THE FUCK DOES THIS PIECE OF SHIT SET TEXT OPACITY TO 0.6
        }
        TextView link_text = findViewById(R.id.about_text_3);
        link_text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
