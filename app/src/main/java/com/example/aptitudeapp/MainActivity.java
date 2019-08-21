package com.example.aptitudeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String Message = "MESSAGE";
    private static final int REQUEST_CODE = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";

    private TextView textViewHighScore;
    private int highScore;

    Button hp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHighScore = findViewById(R.id.text_view_high_score);
        loadHighScore();

        hp_button = findViewById(R.id.hp_button);
        hp_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent  = new Intent(MainActivity.this,QuizActivity.class);
//        intent.putExtra(Message,"Welcome to Quiz Page");
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if (score > highScore) {
                    updateHighScore(score);
                }
            }
        }
    }

    private void loadHighScore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE , 0);
        textViewHighScore.setText("Highest Score: " + highScore);
    }

    private void updateHighScore(int newHighScore) {
        highScore = newHighScore;
        textViewHighScore.setText("Highest Score: " + highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE , highScore);
        editor.apply();
    }
}
