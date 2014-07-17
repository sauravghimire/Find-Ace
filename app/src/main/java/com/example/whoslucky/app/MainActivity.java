package com.example.whoslucky.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    GridView gridView;
    Adapter adapter;
    ObjectAnimator objectAnimator;
    ImageView imageView1, imageView2;
    TextView cardId, life, score;
    Button use;
    Random random = new Random();
    int randomInt = random.nextInt(8), lifeCount = 3, scoreCount = 0, aceCounter = 0;
    int[] imageArray = {R.drawable.club, R.drawable.spade, R.drawable.diamond, R.drawable.heartace, R.drawable.jack, R.drawable.spadejack, R.drawable.queen, R.drawable.king, R.drawable.joker};
    List<DTO> imageList1 = new ArrayList<DTO>();
    List<CheckDTO> checkList = new ArrayList<CheckDTO>();
    DTO dto;
    CheckDTO checkDTO;
    AnimatorSet animatorSet;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_main);
        life = (TextView) findViewById(R.id.life);
        score = (TextView) findViewById(R.id.coin);
        use = (Button) findViewById(R.id.use);
        use.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("SavingScore",0);
        if(sharedPreferences.contains("Score")){
            scoreCount = sharedPreferences.getInt("Score",0);
        }else{
            scoreCount = 0;
        }
        if(sharedPreferences.contains("Life")){
            lifeCount = sharedPreferences.getInt("Life",0);
        }else{
            lifeCount = 3;
        }
        for (int i = 0; i < imageArray.length; i++) {
            dto = new DTO(imageArray[i], i);
            imageList1.add(dto);
        }

        Collections.shuffle(imageList1);
        for (int j = 0; j < imageList1.size(); j++) {
            if (imageList1.get(j).getId() == 0 || imageList1.get(j).getId() == 1 || imageList1.get(j).getId() == 2 || imageList1.get(j).getId() == 3) {
                checkDTO = new CheckDTO(true, false, imageList1.get(j).getId());
            } else {
                checkDTO = new CheckDTO(false, false, imageList1.get(j).getId());
            }
            checkList.add(checkDTO);
        }
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new Adapter(this, R.layout.single, imageList1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        life.setText(""+lifeCount);
        score.setText(""+scoreCount);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        cardId = (TextView) view.findViewById(R.id.cardid);
        checkList.get(i).setTurnOver(true);
        objectAnimator = ObjectAnimator.ofFloat(imageView1, "rotationY", 0f, 360f);
        objectAnimator = ObjectAnimator.ofFloat(imageView2, "rotationY", 0f, 360f);
        animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {


            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imageView2.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        if (imageList1.get(i).getId() == 0 || imageList1.get(i).getId() == 1 || imageList1.get(i).getId() == 2 || imageList1.get(i).getId() == 3) {
            aceCounter++;
            scoreCount = scoreCount + 10;
            score.setText("" + scoreCount);
            checkDTO = new CheckDTO(true, true, imageList1.get(i).getId());
            checkList.add(checkDTO);
            if (aceCounter == 4) {
                AppUtils.saveLifeAndScore(MainActivity.this,scoreCount,lifeCount);
                AppUtils.holdAndStart(MainActivity.this,MainActivity.this);
                Toast.makeText(this, "Congratulations", Toast.LENGTH_LONG).show();
            }
        }
        if (imageList1.get(i).getId() == 8) {
            Toast.makeText(this, "JOKER", Toast.LENGTH_LONG).show();
            lifeCount--;
            if(lifeCount!=0){
                        AppUtils.saveLifeAndScore(MainActivity.this,scoreCount,lifeCount);
                AppUtils.holdAndStart(MainActivity.this,MainActivity.this);
            }else{
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent refresh = new Intent(MainActivity.this,GameOver.class);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        startActivity(refresh);
                        finish();
                    }
                }, 2000);

            }
            /*final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(refresh);
                    finish();
                }
            }, 2000);*/
            aceCounter = 0;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use:
                if (scoreCount < 20) {
                    Toast.makeText(this, "Not Enough Coin", Toast.LENGTH_LONG).show();
                } else {
                    scoreCount = scoreCount - 20;
                    score.setText("" + scoreCount);
                    for (int k = 0; k < checkList.size(); k++) {
                        if (checkList.get(k).isAce() && !checkList.get(k).isTurnOver()) {
                            View view1 = gridView.getChildAt(k);
                            AppUtils.turnOver(view1, checkList.get(k));
                            aceCounter++;
                            break;
                        }
                    }
                }
                break;
        }
    }

}
