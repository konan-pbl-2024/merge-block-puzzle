package com.example.merge_block_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntrodutionActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introdution);

        soundPlayer = new SoundPlayer(this);

        Button okButton=(Button)findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(IntrodutionActivity.this,MainActivity.class);
                startActivity(intent);
                soundPlayer.playButtonSound();

            }
        });
    }



}