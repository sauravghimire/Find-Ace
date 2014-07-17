package com.example.whoslucky.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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
    List<Integer> imagelist = new ArrayList<Integer>();
    List<Integer> checkList = new ArrayList<Integer>();
    List<DTO> imageList1 = new ArrayList<DTO>();
    DTO dto;
    AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        life = (TextView) findViewById(R.id.life);
        score = (TextView) findViewById(R.id.coin);
        use = (Button) findViewById(R.id.use);
        use.setOnClickListener(this);
        for (int i = 0; i < imageArray.length; i++) {
            dto = new DTO(imageArray[i], i);
            imageList1.add(dto);
        }
        /*for(int i= 0;i<9;i++){
            checkList.add(i);
        }
        for(int i= 0;i<checkList.size();i++){
            Log.i("Check Before:::",""+checkList.get(i));
        }
        Collections.shuffle(checkList);
        for(int i= 0;i<checkList.size();i++){
            Log.i("Check After:::",""+checkList.get(i));
        }*/
        Collections.shuffle(imageList1);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new Adapter(this, R.layout.single, imageList1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        cardId = (TextView) view.findViewById(R.id.cardid);

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
        if (imageList1.get(i).getId() == 8) {
            Toast.makeText(this, "JOKER", Toast.LENGTH_LONG).show();
            lifeCount--;
            life.setText("" + lifeCount);
            gridView.setAdapter(adapter);
        }
        if (imageList1.get(i).getId() == 0 || imageList1.get(i).getId() == 1 || imageList1.get(i).getId() == 2 || imageList1.get(i).getId() == 3) {
            aceCounter++;
            if (aceCounter == 4) {
                Toast.makeText(this, "Congratulations", Toast.LENGTH_LONG).show();
            }
            scoreCount = scoreCount + 10;
            score.setText("" + scoreCount);
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
                    score.setText(""+scoreCount);
                }
                break;
        }
    }
}
