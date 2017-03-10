package com.android.learnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail3Fragment extends Fragment {
    private final int imageResourceId;
    Button b;

    public Detail3Fragment(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

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
        View view = inflater.inflate(R.layout.page_4, container, false);
        b=(Button) view.findViewById(R.id.loginButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
        TextView textView = (TextView) view.findViewById(R.id.text_41);
        TextView textView1 = (TextView) view.findViewById(R.id.text_42);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_4);
        imageView.setImageResource(imageResourceId);
        return view;
    }
}