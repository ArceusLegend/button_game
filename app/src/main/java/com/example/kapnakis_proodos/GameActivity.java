package com.example.kapnakis_proodos;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.topToTop = ConstraintSet.PARENT_ID;
        layoutParams.topMargin = (int) (screenHeight * 0.10);
        timer_text.setLayoutParams(layoutParams);
        timer_text.setTextSize(40);
        timer_text.setText(R.string.click_any_of_the_breads_to_start);
        CountDownTimer timer = new CountDownTimer(100, 1000) {
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
                retryButton.setText("VVVINUJBHJB");
                layout.addView(retryButton);
            }
        };
        TextView scoreText = findViewById(R.id.score);
        scoreText.setText(MessageFormat.format(
                "{0}{1}",
                getString(R.string.current_score),
                score
                )
        );
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
        });
    }

    private void randomise_position(Button button1, Button button2) {
        List<Button> buttons = Arrays.asList(button1, button2);
        int randomX;
        int randomY;
        for (Button button : buttons) {
            int max_width = screenWidth - button.getWidth();
            int max_height = screenHeight - button.getHeight();
            randomX = random.nextInt(max_width);
            randomY = random.nextInt(max_height);
            // Ensure the button remains within the visible area
            randomX = Math.max(randomX, button.getWidth() + buttonPadding);
            randomX = Math.min(randomX, max_width - buttonPadding);
            randomY = Math.max(randomY, button.getHeight() + buttonPadding);
            randomY = Math.min(randomY, max_height - buttonPadding);
            button.setX(randomX);
            button.setY(randomY);
        }
    }
}
