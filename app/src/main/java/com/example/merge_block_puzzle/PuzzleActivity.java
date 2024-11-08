package com.example.merge_block_puzzle;

import android.os.Bundle;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        GridLayout gridLayout = findViewById(R.id.puzzleArea);
        com.example.merge_block_puzzle.PuzzleBoard puzzleBoard = new com.example.merge_block_puzzle.PuzzleBoard(this);
        gridLayout.addView(puzzleBoard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.puzzle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}