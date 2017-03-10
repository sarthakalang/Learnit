package com.android.learnit;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private MyAdapter mAdapter;
    private ViewPager mPager;
    Integer colors[] = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private void setUpColors() {

        Integer color1 = getResources().getColor(R.color.color1);
        Integer color2 = getResources().getColor(R.color.color2);
        Integer color3 = getResources().getColor(R.color.color3);
        Integer color4 = getResources().getColor(R.color.color4);

        colors = new Integer[]{color1, color2, color3, color4};
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        setUpColors();
        mPager.addOnPageChangeListener(new CustomOnPageChangeListener());

        /**
         * Remove individual page backgrounds to enable background transition
         */


        //TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        //titleIndicator.setViewPager(mPager);



        /*TextView myTextView = (TextView)findViewById(R.id.example);
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/cheddar.ttf");
        myTextView.setTypeface(typeFace);*/



    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ImageFragment();
                case 1:
                    return new DetailFragment(R.drawable.img_2);
                case 2:
                    return new Detail2Fragment(R.drawable.img_3);
                case 3:
                    return new Detail3Fragment(R.drawable.img_4);

                default:
                    return null;
            }
        }
    }

    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (position < (mAdapter.getCount() - 1) && position < (colors.length - 1)) {

                mPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));

            } else {

                // the last page color
                mPager.setBackgroundColor(colors[colors.length - 1]);

            }

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }
}
