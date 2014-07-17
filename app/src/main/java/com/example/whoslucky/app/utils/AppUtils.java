package com.example.whoslucky.app.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whoslucky.app.gamedto.CheckDTO;
import com.example.whoslucky.app.R;

/**
 * Created by saurav on 7/17/14.
 */
public class AppUtils {
    public static void turnOver(View view, CheckDTO checkDTO) {
        final ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        final ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        TextView cardId = (TextView) view.findViewById(R.id.cardid);
        checkDTO.setTurnOver(true);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView1, "rotationY", 0f, 360f);
        objectAnimator = ObjectAnimator.ofFloat(imageView2, "rotationY", 0f, 360f);
        AnimatorSet animatorSet = new AnimatorSet();
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
    }

    public static void holdAndStart(final Context activity1,final Context activity2 ){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent refresh = new Intent(activity1.getApplicationContext(), activity2.getClass());
                ((Activity)activity1).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                ((Activity)activity1).startActivity(refresh);
                ((Activity)activity1).finish();
            }
        }, 2000);
    }

    public static void saveLifeAndScore(Context context, int score, int life){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SavingScore", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Life",life);
        editor.putInt("Score",score);

        editor.commit();
    }

}
