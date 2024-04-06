package com.example.kapnakis_proodos;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends BaseActivity {
    Button playButton;
    Button scoreButton;
    Button aboutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bars
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        //TODO: Figure out an alternative that doesn't produce warnings
        //WindowInsetsController controller = getWindow().getInsetsController();
        //if(controller != null){
        //    controller.hide(WindowInsets.Type.statusBars());
        //}
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Button style defined in res/drawable/button_style.xml
        // Applied using style defined in res/values/themes.xml, in the tag with name="button_style"
        // Play button functionality
        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, GameActivity.class
                )
        ));
        // Score button functionality
        scoreButton = findViewById(R.id.score_button);
        // About button functionality
        aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(v -> startActivity(
                new Intent(
                    MainActivity.this, AboutActivity.class
                )
        ));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}