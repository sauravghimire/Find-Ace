package com.example.whoslucky.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.whoslucky.app.MainActivity;
import com.example.whoslucky.app.R;

/**
 * Created by saurav on 7/17/14.
 */
public class MainMenuActivity extends Activity implements View.OnClickListener {

    Button buttonPlay, buttonInstruction, buttonHighScore, buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.main_menu);
        buttonPlay = (Button) findViewById(R.id.button_play);
        buttonInstruction = (Button) findViewById(R.id.button_instruction);
        buttonHighScore = (Button) findViewById(R.id.button_high);
        buttonExit = (Button) findViewById(R.id.button_exit);
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play:
                Intent gameStart = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(gameStart);
                break;
            case R.id.button_high:
                break;
            case R.id.button_instruction:
                break;
            case R.id.button_exit:
        }
    }
}
