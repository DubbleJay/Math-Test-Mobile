package com.doublej.mathquizmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndOfGameActivity extends Activity {

    private static final String RESULTS = "com.doublej.mathquizmobile.results";
    private static final String SUMMARY = "com.doublej.mathquizmobile.summary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        final int[] resultsArray = getIntent().getIntArrayExtra(RESULTS);
        final String[] summaryArray = getIntent().getStringArrayExtra(SUMMARY);

        MediaPlayer mediaPlayer = MediaPlayer.create(EndOfGameActivity.this, R.raw.endofgamesound);

        if (savedInstanceState == null)
            mediaPlayer.start();

        final TextView totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);
        totalQuestionsTextView.append(" " + resultsArray[0]);

        final TextView correctCountTextView = findViewById(R.id.correctCountTextView);
        correctCountTextView.append(" " + resultsArray[1]);

        final TextView wrongCountTextView = findViewById(R.id.wrongCountTextView);
        wrongCountTextView.append(" " + resultsArray[2]);

        final TextView finalScoreTextView = findViewById(R.id.finalScoreTextView);
        finalScoreTextView.append(" " + resultsArray[3] + "%");

        final Button summaryButton = findViewById(R.id.goToSummaryButton);
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SummaryActivity.newIntent(EndOfGameActivity.this, summaryArray);
                startActivity(intent);
            }
        });

        final Button backToMainMenuButton = findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndOfGameActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public static Intent newIntent(Context packageContext, int[] resultsArray, String[] summaryArray) {
        Intent i = new Intent(packageContext, EndOfGameActivity.class);
        i.putExtra(RESULTS, resultsArray);
        i.putExtra(SUMMARY, summaryArray);
        return i;
    }
}
