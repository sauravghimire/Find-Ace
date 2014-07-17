package com.example.whoslucky.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.whoslucky.app.R;

/**
 * Created by susan on 7/17/14.
 */
public class SplashActivity extends ActionBarActivity {

    private ImageView imageViewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_activity);
        getSupportActionBar().hide();

        imageViewSplash = (ImageView) findViewById(R.id.imageViewSplash);
        imageViewSplash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animatioin));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 4000);
    }
}
