package com.doublej.mathquizmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Spinner spinner = findViewById(R.id.spinner);
        Integer[] integers = {5, 10, 15, 20, 25};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, integers);
        spinner.setAdapter(adapter);

        Button additionButton = findViewById(R.id.addition_Button);
        additionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity(0, (Integer)spinner.getSelectedItem());
            }
        });

        Button subtractionButton = findViewById(R.id.subtraction_Button);
        subtractionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity(1, (Integer)spinner.getSelectedItem());
            }
        });

        Button multiplicationButton = findViewById(R.id.multiplication_Button);
        multiplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity(2, (Integer)spinner.getSelectedItem());
            }
        });

        Button divisionButton = findViewById(R.id.division_Button);
        divisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity(3, (Integer)spinner.getSelectedItem());
            }
        });

    }

    public void startGameActivity(int gameType, int numberOfQuestions) {
        Intent intent = GameActivity.newIntent(MainMenuActivity.this, gameType, numberOfQuestions);
        startActivity(intent);
        finish();
    }
}
