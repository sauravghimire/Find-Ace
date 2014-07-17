package com.example.whoslucky.app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by saurav on 7/17/14.
 */
public class GameOver extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        setContentView(R.layout.gameover);
    }
}
