package com.example.merge_block_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    public TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        scoreTextView = findViewById(R.id.scoreView);

        // Intentからスコアを取得してTextViewに設定
        int finalScore = getIntent().getIntExtra("FINAL_SCORE", 0);
        scoreTextView.setText("最終スコア: " + finalScore);
        System.out.println("最終スコア: " + finalScore);

        Button returnbutton = (Button)findViewById(R.id.button1);
        returnbutton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                    startActivity(intent);
                }
        });
    }
}