package com.example.merge_block_puzzle;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PuzzleActivity extends AppCompatActivity {


    private PuzzleBoard puzzleBoard;
    private TextView scoreTextView;
    private TextView clicksRemainingTextView;

    //com.example.merge_block_puzzle.PuzzleBoard puzzleBoard;
    //public static int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        puzzleBoard = findViewById(R.id.puzzleBoard);
        scoreTextView = findViewById(R.id.scoreTextView);
        clicksRemainingTextView = findViewById(R.id.clicksRemainingTextView);

        // 初期の残りクリック回数を表示（20回）
        clicksRemainingTextView.setText("残り操作回数: 20");

        // スコア変更時のリスナーを設定
        puzzleBoard.setOnScoreChangeListener(new PuzzleBoard.OnScoreChangeListener() {
            @Override
            public void onScoreChanged(int newScore) {
                scoreTextView.setText("スコア: " + newScore);
            }
        });

        // 残りクリック数のリスナーを設定
        puzzleBoard.setOnClicksRemainingListener(new PuzzleBoard.OnClicksRemainingListener() {
            @Override
            public void onClicksRemaining(int clicksRemaining) {
                clicksRemainingTextView.setText("残り操作回数: " + clicksRemaining);
                if (clicksRemaining <= 0) {
                    // ゲームオーバーの処理（必要に応じて実装）
                }
            }
        });

        // システムバーのインセットを適用
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.puzzle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public int getScore() {
        return puzzleBoard.getScore();
    }
}

