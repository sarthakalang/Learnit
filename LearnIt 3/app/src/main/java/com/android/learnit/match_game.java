package com.android.learnit;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class match_game extends AppCompatActivity {

    String[] wordlist;
    String[] deflist;
    String setname;
    WordsDBHelper wdb;
    int c1=2;
    int c2=3;
    public int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_game);
        Intent intent=getIntent();
       setname= intent.getStringExtra("key");
        flag=intent.getIntExtra("flag",0);

        fetchdata(setname);

        // Create adapter to set value for grid view
        final GridView gridView = (GridView) findViewById(R.id.gridview);

        ArrayList<Integer> array = new ArrayList<>();
        ArrayList<String> array2 = new ArrayList<>(8);

        for(int i=0;i<wordlist.length;i++)
            array.add(new Integer(i));

        Collections.shuffle(array);

        int limit = 4 ;
        if(array.size()<4)
           limit = array.size();

        for(int i=0;i<limit;i++) {
            array2.add(wordlist[array.get(i)]);
            array2.add(deflist[array.get(i)]);
        }

        Collections.shuffle(array2);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.gamecard_view,array2);

        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (v.getElevation() != 0) {
                    v.setElevation(0);
                    v.setBackgroundColor(getResources().getColor(R.color.cardBackground));
                }

            }

        });



        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            private int numSelected, prevSelected;

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(view.getElevation()!=0)
                    return true;

                view.setBackgroundColor(getResources().getColor(R.color.cardSelectBackground));
                view.setElevation(50f);
                numSelected++;
                if (numSelected == 1)
                    prevSelected = adapterView.getPositionForView(view);
                else if (numSelected == 2)
                    //Toast.makeText(getApplicationContext(),"2 selected", Toast.LENGTH_SHORT).show();
                    compare(prevSelected, adapterView.getPositionForView(view));
                return true;
            }

            private void compare(int SelectedView1, int SelectedView2) {
                final TextView View1 = (TextView) gridView.getChildAt(SelectedView1);
                final TextView View2 = (TextView) gridView.getChildAt(SelectedView2);
                for (int i = 0; i < wordlist.length; i++) {
                    if (View1.getText().toString() == wordlist[i])
                        if (View2.getText().toString() == deflist[i]) {

                            Correct(View1,View2);

                            numSelected = 0;
                            return;
                        }
                }


                for(int i=0;i<wordlist.length;i++){
                    if(View2.getText().toString()==wordlist[i])
                        if(View1.getText().toString()==deflist[i]) {

                            Correct(View1,View2);

                            numSelected = 0;
                            return;
                        }

                }

                Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                View1.setBackgroundColor(Color.RED);
                View2.setBackgroundColor(Color.RED);
                numSelected = 0;

            }


            private void Correct(final TextView View1, final TextView View2 ){

                int duration = 200,delay=1000;

                Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                View1.setBackgroundColor(Color.GREEN);
                View2.setBackgroundColor(Color.GREEN);
                ObjectAnimator disappearX1
                        = ObjectAnimator.ofFloat(View1, "scaleX", 1f, 0f);
                //disappearX1.setStartDelay(delay);
                //disappearX1.setDuration(duration);

                ObjectAnimator disappearX2
                        = ObjectAnimator.ofFloat(View2, "scaleX", 1f, 0f);
                //disappearX2.setStartDelay(delay);
                //disappearX2.setDuration(duration);

                ObjectAnimator disappearY1
                        = ObjectAnimator.ofFloat(View1, "scaleY", 1f, 0f);

                ObjectAnimator disappearY2
                        = ObjectAnimator.ofFloat(View2, "scaleY", 1f, 0f);

                disappearY2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        View1.setVisibility(View.GONE);
                        View2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                AnimatorSet animSet = new AnimatorSet();
                animSet.setDuration(duration);
                animSet.setStartDelay(delay);
                animSet.playTogether(disappearX1, disappearX2, disappearY1, disappearY2);
                animSet.start();

                //disappearX1.start();
                //disappearX2.start();

            }


        });


    }





    public void fetchdata(String set){
        wdb=new WordsDBHelper(this);
        Cursor cr;
        if(flag==1){
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
