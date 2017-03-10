package com.android.learnit;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

public class flashcard extends AppCompatActivity {
    String[] wordlist;
    String[] deflist;
    WordsDBHelper wdb;
    int c1=2;
    int flag=0;
    int c2=3;
    String set;
    ViewPager pager;
    RelativeLayout r;
    float x,y;
    int a,b;
    public int flagg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        r=(RelativeLayout) findViewById(R.id.flashrel);
        Intent i=getIntent();
        set=i.getStringExtra("key");
        x=i.getFloatExtra("x", 0);
        y=i.getFloatExtra("y",0);
        flagg=i.getIntExtra("flag",0);
        fetchdata();

        pager = (ViewPager) findViewById(R.id.flashcard);

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CardFragment frag = new CardFragment(wordlist[position], deflist[position]);
                return frag;
            }

            @Override
            public int getCount() {
                return wordlist.length;
            }
        });
        if (savedInstanceState == null) {
            r.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = r.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();

                    }
                });
            }
        }

    }
    public void circularRevealActivity(){
        if(flag==0) {
           // int cx = r.getWidth() / 2;
           // int cy = r.getHeight() / 2;

            float finalRadius = Math.max(r.getWidth(), r.getHeight());
a=Math.round(x);
            b=Math.round(y);
            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(r, a, b, 0, finalRadius);
            circularReveal.setDuration(500);

            // make the view visible and start the animation
            r.setVisibility(View.VISIBLE);

            circularReveal.start();



            flag++;
        }
        else{
            r.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flashcard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void fetchdata(){
        wdb=new WordsDBHelper(this);
        Cursor cr;
        if(flagg==1){
       cr=wdb.getsetData(set,"pd");}
        else {
            cr=wdb.getsetData(set,"nd");
        }

        wordlist=new String[cr.getCount()];
        deflist=new String[cr.getCount()];
        if (cr.moveToFirst())
        {
            for (int i = 0; i < cr.getCount(); i++)
            {
                wordlist[i] = cr.getString(c1);
                cr.moveToNext();
            }
        }
        if (cr.moveToFirst())
        {
            for (int i = 0; i < cr.getCount(); i++)
            {
                deflist[i] = cr.getString(c2);
                cr.moveToNext();
            }
        }
        cr.close();
    }
}
