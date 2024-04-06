package com.example.kapnakis_proodos;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

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
        Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        ScrollView scroll_view = findViewById(R.id.scroller);
        scroll_view.startAnimation(fadeInAnimation);
    }
}
