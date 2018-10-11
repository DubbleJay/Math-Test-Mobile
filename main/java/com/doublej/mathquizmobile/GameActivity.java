package com.doublej.mathquizmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private static final String GAME_TYPE = "com.doublej.mathquizmobile.game_type";
    private static final String NUMBER_OF_QUESTIONS = "com.doublej.mathquizmobile.number_of_questions";
    private static final String KEY_GAME_MODEL = "gameModel";
    private static final String KEY_BUTTONS_ENABLED = "answerEntered";
    private static final String KEY_CURRENT_INDEX= "currentIndex";
    private static final String KEY_RESULT_TEXT_VIEW_STRING = "resultTextViewString";
    private static final String KEY_CORRECT_OR_WRONG_STRING = "correctOrWrongString";
    private static final String KEY_TEXT_VIEWS_COLOR = "textViewsColor";
    private GameModel gameModel;
    private int currentIndex = 1;
    private Button nextButton;
    private Button enterButton;
    private TextView resultTextView;
    private TextView correctOrWrongTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        enterButton = findViewById(R.id.enterButton);
        nextButton = findViewById(R.id.my_Button);
        resultTextView = findViewById(R.id.resultTextView);
        correctOrWrongTextView = findViewById(R.id.correctOrWrongTextView);

        if (savedInstanceState != null) {
            gameModel = (GameModel) savedInstanceState.getSerializable(KEY_GAME_MODEL);
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            nextButton.setEnabled(savedInstanceState.getBoolean(KEY_BUTTONS_ENABLED));
            enterButton.setEnabled(!savedInstanceState.getBoolean(KEY_BUTTONS_ENABLED));
            resultTextView.setText(savedInstanceState.getCharSequence(KEY_RESULT_TEXT_VIEW_STRING));
            correctOrWrongTextView.setText(savedInstanceState.getCharSequence(KEY_CORRECT_OR_WRONG_STRING));
            resultTextView.setTextColor(savedInstanceState.getInt(KEY_TEXT_VIEWS_COLOR));
            correctOrWrongTextView.setTextColor(savedInstanceState.getInt(KEY_TEXT_VIEWS_COLOR));
        }

        else {
            gameModel = new GameModel(getIntent().getIntExtra(GAME_TYPE, 0), getIntent().getIntExtra(NUMBER_OF_QUESTIONS, 0));
            gameModel.generateCurrentQuestion();
            nextButton.setEnabled(false);
        }

        final Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity.this, MainMenuActivity.class);
                startActivity(i);
                finish();
            }
        });

        final TextView questionIndexTextView = findViewById(R.id.questionIndexTextView);
        questionIndexTextView.append(" " + currentIndex + " ");

        final TextView questionTotalTextView = findViewById(R.id.questionTotalTextView);
        questionTotalTextView.append(" " + gameModel.getNumberOfQuestions());

        final TextView textView = findViewById(R.id.textView);
        textView.setText(gameModel.getQuestionString());

        final EditText editText = findViewById(R.id.editText);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (gameModel.checkUserAnswer(Integer.parseInt(editText.getText().toString()))) {
                        Toast.makeText(GameActivity.this, R.string.correct_string, Toast.LENGTH_LONG).show();
                        resultTextView.setTextColor(Color.GREEN);
                        resultTextView.setText(" = " + editText.getText());
                        correctOrWrongTextView.setTextColor(Color.GREEN);
                        correctOrWrongTextView.setText(R.string.correct_string);
                        MediaPlayer mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.correctanswer);
                        mediaPlayer.start();
                    }

                    else {
                        Toast.makeText(GameActivity.this, R.string.wrong_string, Toast.LENGTH_LONG).show();
                        resultTextView.setTextColor(Color.RED);
                        resultTextView.setText(" != " + editText.getText());
                        correctOrWrongTextView.setTextColor(Color.RED);
                        correctOrWrongTextView.setText(R.string.wrong_string);
                        MediaPlayer mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.wronganswer);
                        mediaPlayer.start();
                    }

                    enterButton.setEnabled(false);
                    nextButton.setEnabled(true);

                } catch (NumberFormatException ex) {
                    Toast.makeText(GameActivity.this, R.string.badInput, Toast.LENGTH_LONG).show();
                }
            }
        });

        nextButton = findViewById(R.id.my_Button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameModel.isGameOver()) {
                    gameModel.generateCurrentQuestion();
                    textView.setText(gameModel.getQuestionString());
                    resultTextView.setText("");
                    correctOrWrongTextView.setText("");
                    editText.setText("");
                    nextButton.setEnabled(false);
                    enterButton.setEnabled(true);
                    currentIndex++;
                    questionIndexTextView.setText(" " + currentIndex + " ");
                }

                else {
                    Intent i = EndOfGameActivity.newIntent(GameActivity.this, new int[] {gameModel.getNumberOfQuestions(), gameModel.getCorrectAnswers(),
                            gameModel.getWrongAnswers(), (int) gameModel.getUserScore()}, gameModel.getSummary());
                    startActivity(i);
                    finish();
                }

            }
        });

    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(KEY_GAME_MODEL, gameModel);
        savedInstanceState.putInt(KEY_CURRENT_INDEX, currentIndex);
        savedInstanceState.putBoolean(KEY_BUTTONS_ENABLED, nextButton.isEnabled());
        savedInstanceState.putCharSequence(KEY_RESULT_TEXT_VIEW_STRING, resultTextView.getText());
        savedInstanceState.putCharSequence(KEY_CORRECT_OR_WRONG_STRING, correctOrWrongTextView.getText());
        savedInstanceState.putInt(KEY_TEXT_VIEWS_COLOR, resultTextView.getCurrentTextColor());
    }

    public static Intent newIntent(Context packageContext, int gameType, int numberOfQuestions) {
        Intent i = new Intent(packageContext, GameActivity.class);
        i.putExtra(GAME_TYPE, gameType);
        i.putExtra(NUMBER_OF_QUESTIONS, numberOfQuestions);
        return i;
    }
}
