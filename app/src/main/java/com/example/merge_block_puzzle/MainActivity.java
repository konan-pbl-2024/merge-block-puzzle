package com.example.merge_block_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button s_button = (Button)findViewById(R.id.start_button);
        s_button.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               Intent intent = new Intent(MainActivity.this,IntrodutionActivity.class);
               startActivity(intent);
           }
        });
    }
}
