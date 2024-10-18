package com.example.merge_block_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntrodutionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introdution);
        Button okButton=(Button)findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener()){
            public void onClick(View ){

            }
        }
    }



}