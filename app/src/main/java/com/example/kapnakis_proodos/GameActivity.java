package com.example.kapnakis_proodos;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.text.MessageFormat;

public class GameActivity extends BaseActivity {
    int score = 0;
    boolean timer_running = false;
    int screenWidth;
    int screenHeight;
    int buttonPadding = 5; // Add some extra margin to prevent buttons from touching edge of screen
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        // Initialize buttons
        Button plusButton = findViewById(R.id.plus_button);
        ViewGroup.LayoutParams initPlusButtonParams = plusButton.getLayoutParams();
        Button minusButton = findViewById(R.id.minus_button);
        ViewGroup.LayoutParams initMinusButtonParams = minusButton.getLayoutParams();
        // Get screen dimensions
        screenWidth = getWindowManager().getCurrentWindowMetrics().getBounds().width();
        screenHeight = getWindowManager().getCurrentWindowMetrics().getBounds().height();
        TextView timer_text = findViewById(R.id.timer);
        // Configure TextView here because activity xml is not very accurate
        ConstraintLayout.LayoutParams infoTextLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        infoTextLayoutParams.endToEnd = ConstraintSet.PARENT_ID;
        infoTextLayoutParams.startToStart = ConstraintSet.PARENT_ID;
        infoTextLayoutParams.topToTop = ConstraintSet.PARENT_ID;
        infoTextLayoutParams.topMargin = (int) (screenHeight * 0.10);
        timer_text.setLayoutParams(infoTextLayoutParams);
        timer_text.setTextSize(24);
        timer_text.setText(R.string.click_any_of_the_breads_to_start);
        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_text.setText(MessageFormat.format(
                        "{0}{1}",
                        getString(R.string.time_left),
                        millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                timer_text.setText(R.string.time_up);
                // Remove bread buttons.
                ConstraintLayout layout = findViewById(R.id.layout);
                layout.removeView(plusButton);
                layout.removeView(minusButton);
                Button backButton = new Button(getApplicationContext());
                backButton.setLayoutParams(initPlusButtonParams);
                backButton.setBackgroundResource(R.drawable.button_style);
                backButton.setText(R.string.back_to_menu);
                layout.addView(backButton);
                Button retryButton = new Button(getApplicationContext());
                retryButton.setLayoutParams(initMinusButtonParams);
                retryButton.setBackgroundResource(R.drawable.button_style);
                retryButton.setText(R.string.try_again);
                layout.addView(retryButton);
                // New button functionality
                backButton.setOnClickListener(v -> startActivity(
                        new Intent(GameActivity.this, MainActivity.class)
                ));
                retryButton.setOnClickListener(v -> {
                    Intent intent = new Intent(GameActivity.this, GameActivity.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                });
            }
        };
        TextView scoreText = findViewById(R.id.score);
        scoreText.setText(MessageFormat.format(
                "{0}{1}",
                getString(R.string.current_score),
                score
                )
        );
        ConstraintLayout.LayoutParams scoreTextLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        scoreTextLayoutParams.endToEnd = ConstraintSet.PARENT_ID;
        scoreTextLayoutParams.startToStart = ConstraintSet.PARENT_ID;
        scoreTextLayoutParams.topToTop = ConstraintSet.PARENT_ID;
        scoreTextLayoutParams.topMargin = (int) (screenHeight * 0.15);
        scoreText.setLayoutParams(scoreTextLayoutParams);
        scoreText.setTextSize(22);
        plusButton.setOnClickListener(v -> {
            if(!timer_running) {
                timer.start();
                timer_running = true;
            }
            score++;
            randomise_position(plusButton, minusButton);
            scoreText.setText(MessageFormat.format(
                    "{0}{1}",
                    getString(R.string.current_score),
                    score
                    )
            );
            if(plusButton.getBackground() == AppCompatResources.getDrawable(getApplicationContext(), R.drawable.super_bread)){
                plusButton.setBackgroundResource(R.drawable.bread);
            }
            else if(random.nextFloat() <= 0.001){
                plusButton.setBackgroundResource(R.drawable.super_bread);
            }
        });
        minusButton.setOnClickListener(v -> {
            if(!timer_running) {
                timer.start();
                timer_running = true;
            }
            if(score > 0){
                score--;
            }
            randomise_position(plusButton, minusButton);
            scoreText.setText(MessageFormat.format(
                    "{0}{1}",
                    getString(R.string.current_score),
                    score
                    )
            );
            if(plusButton.getBackground() == AppCompatResources.getDrawable(getApplicationContext(), R.drawable.super_bread)){
                plusButton.setBackgroundResource(R.drawable.bread);
            }
        });
    }

    private void randomise_position(Button button1, Button button2) {
        int maxWidth = screenWidth - button1.getWidth() - buttonPadding;
        int maxHeight = screenHeight - button1.getHeight() - buttonPadding;
        int minDistance = 20;
        // Plus button random pos
        int randomXPlus = random.nextInt(maxWidth);
        int randomYPlus = random.nextInt(maxHeight);
        button1.setX(randomXPlus);
        button1.setY(randomYPlus);
        // Minus button random pos
        int randomXMinus;
        int randomYMinus;
        do {
            randomXMinus = random.nextInt(maxWidth);
            randomYMinus = random.nextInt(maxHeight);
        }while(
                (randomXMinus >= randomXPlus - minDistance && randomXMinus <= randomXPlus - button1.getWidth() + minDistance) || (randomYMinus >= randomYPlus - minDistance && randomYMinus <= randomYMinus - button1.getHeight() + minDistance));
        button2.setX(randomXMinus);
        button2.setY(randomYMinus);
    }
}
