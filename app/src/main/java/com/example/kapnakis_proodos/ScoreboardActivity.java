package com.example.kapnakis_proodos;

import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;

public class ScoreboardActivity extends BaseActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bars
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scoreboard);
        // Initialize ListView
        ListView scoreView = findViewById(R.id.score_list);
        // Scores saved in the device.
        List<Map<String, String>> scoreList;
    }
}
