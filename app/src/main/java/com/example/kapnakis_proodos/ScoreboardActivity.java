package com.example.kapnakis_proodos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.widget.ArrayAdapter;

public class ScoreboardActivity extends BaseActivity {

    SharedPreferences saveData;
    ListView scoreView;
    List<String> scoreList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scoreboard);
        // Initialize ListView
        scoreView = findViewById(R.id.score_list);
        scoreList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoreList);
        scoreView.setAdapter(adapter);
        // Scores saved in the device.
        saveData = getSharedPreferences("scoreData", Context.MODE_PRIVATE);
        // Retrieve and display scoreboard data
        displayScoreboardData();
        // Button functionality
        Button backButton = findViewById(R.id.menu);
        backButton.setOnClickListener(v -> startActivity(
                new Intent(ScoreboardActivity.this, MainActivity.class)
        ));
        Button playButton = findViewById(R.id.play);
        playButton.setOnClickListener(v -> startActivity(
                new Intent(ScoreboardActivity.this, GameActivity.class)
        ));
        Button clearButton = findViewById(R.id.clear);
        clearButton.setOnClickListener(v -> {
            saveData.edit().clear().apply();
            scoreList.clear();
            adapter.notifyDataSetChanged();
        });
    }

    private void displayScoreboardData() {
        // Retrieve all keys (timestamps) from SharedPreferences
        // Each key represents a score entry
        // If there are no scores saved yet, an empty set is returned
        Set<String> scoreKeys = saveData.getAll().keySet();

        // Create a DateTimeFormatter to format the timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create a list to store all score entries
        List<String> allScores = new ArrayList<>();

        // Iterate through each key (timestamp) and retrieve the corresponding score
        for (String key : scoreKeys) {
            // Parse the timestamp key to LocalDateTime and format it
            LocalDateTime timestamp = LocalDateTime.parse(key);
            String formattedTimestamp = timestamp.format(formatter);

            // Get the score set for the current key
            Set<String> scoreSet = saveData.getStringSet(key, null);
            if (scoreSet != null) {
                // Convert the score set to a list and add it to the allScores list along with the formatted timestamp
                for (String score : scoreSet) {
                    // Concatenate the formatted timestamp and score
                    String entry = formattedTimestamp + ": " + score;
                    allScores.add(entry);
                }
            }
        }

        // Sort the allScores list based on the score value (assuming the score is a number)
        allScores.sort((score1, score2) -> {
            // Split the score entries to extract the score values
            int scoreValue1 = Integer.parseInt(score1.split(": ")[1]);
            int scoreValue2 = Integer.parseInt(score2.split(": ")[1]);
            // Sort in descending order (highest score first)
            return Integer.compare(scoreValue2, scoreValue1);
        });

        // Create a sublist containing only the top 5 scores
        List<String> top5Scores = allScores.subList(0, Math.min(allScores.size(), 5));
        // Clear the scoreList and add the top 5 scores to it
        scoreList.clear();
        scoreList.addAll(top5Scores);
        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }
}
