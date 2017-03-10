package com.android.learnit;

/**
 * Created by sarthakalang on 03/11/15.
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;


public class CardFragment extends Fragment{

    private String word,definition;
int flag=0;
    public CardFragment(){}

    public CardFragment(String word,String definition){
        this.word= word;
        this.definition = definition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.txt, container, false);
        final TextView frontView = (TextView) rootView.findViewById(R.id.front);
        final TextView backView = (TextView) rootView.findViewById(R.id.back);
       AlphaAnimation alpha1 = new AlphaAnimation(0.0f, 1.0f);
        alpha1.setDuration(300);
        alpha1.setStartOffset(600);
        if(flag==0) {
            frontView.startAnimation(alpha1);
            flag++;
        }
        frontView.setText(word);
        backView.setText(definition);


        frontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlipRoutine(frontView, backView);
            }
        });

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlipRoutine(backView, frontView);
            }
        });

        return rootView;
    }






    public void FlipRoutine(final View frontView, final View backView){


        ObjectAnimator flip1
                = ObjectAnimator.ofFloat(backView, "rotationY", 0.0f, 180.0f);
        flip1.setDuration(0);
        //flip1.setStartDelay(1000);


        ObjectAnimator flip2
                = ObjectAnimator.ofFloat(frontView, "rotationY", 0.0f, 90.0f);
        flip2.setDuration(150);
        flip2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                backView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                frontView.setVisibility(View.INVISIBLE);
                backView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        //flip2.setStartDelay(1000);


        ObjectAnimator flip3
                = ObjectAnimator.ofFloat(backView, "rotationY", 180.0f, 360.0f);
        flip3.setDuration(300);
        //flip3.setStartDelay(1000);
        flip3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backView.bringToFront();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        flip1.start();
        flip2.start();
        flip3.start();

    }


    public ObjectAnimator flipOnVertical(View parent, int ViewId) {
        View image = parent.findViewById(ViewId);
        ObjectAnimator anim
                = ObjectAnimator.ofFloat(image, "rotationY", 0.0f, 180.0f);
        anim.setDuration(2000);
        anim.setStartDelay(1000);
        anim.start();
        return anim;
    }

}
