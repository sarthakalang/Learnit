package com.android.learnit;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "hello");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_1, container, false);

        TextView title= (TextView) view.findViewById(R.id.heading_main);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cheddar.ttf");
        title.setTypeface(custom_font);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 195, 10, 10);

        ImageView img = (ImageView) view.findViewById(R.id.logo_main);
        img.setLayoutParams(params);

        TextView subhead = (TextView) view.findViewById(R.id.subheading_main);
        TextView subhead2 = (TextView) view.findViewById ( R.id.subheading2_main);

        ScaleAnimation scale = new ScaleAnimation
                (0f,1f,0f,1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        AccelerateDecelerateInterpolator acc= new AccelerateDecelerateInterpolator();

        TranslateAnimation move = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, -100);
        move.setDuration(700);
        move.setStartOffset(500);
        move.setInterpolator(acc);
        move.setFillAfter(true);

        animListener scaleListener = new animListener(img,move);
        scale.setAnimationListener(scaleListener);

        AlphaAnimation alpha1 = new AlphaAnimation(0.0f, 1.0f);
        alpha1.setDuration(1000);
        alpha1.setStartOffset(1500);
        AlphaAnimation alpha2 = new AlphaAnimation(0.0f, 1.0f);
        alpha2.setDuration(1000);
        alpha2.setStartOffset(1500);
        AlphaAnimation alpha3 = new AlphaAnimation(0.0f, 1.0f);
        alpha3.setStartOffset(5000);
        alpha3.setFillAfter(true);


        img.startAnimation(scale);
        title.startAnimation(alpha1);
        subhead.startAnimation(alpha2);
        subhead2.startAnimation(alpha3);

        return view;
    }
}