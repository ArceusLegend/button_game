package com.example.kapnakis_proodos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

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
        // Get screen dimensions
        screenWidth = getWindowManager().getCurrentWindowMetrics().getBounds().width();
        screenHeight = getWindowManager().getCurrentWindowMetrics().getBounds().height();
        TextView timer_text = findViewById(R.id.timer);
        timer_text.setText(R.string.click_any_of_the_breads_to_start);
        CountDownTimer timer = new CountDownTimer(30000, 1000) {
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
            }
        };
        TextView scoreText = findViewById(R.id.score);
        scoreText.setText(MessageFormat.format(
                "{0}{1}",
                getString(R.string.current_score),
                score
                )
        );
        Button plusButton = findViewById(R.id.plus_button);
        Button minusButton = findViewById(R.id.minus_button);
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
        List<Integer> coordsList = new ArrayList<>();
        int index = 0;
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
            coordsList.add(index, randomX);
            index++;
            coordsList.add(index, randomY);
            index++;
            button.setX(randomX);
            button.setY(randomY);
        }
    }
}
