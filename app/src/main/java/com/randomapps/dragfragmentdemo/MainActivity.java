package com.randomapps.dragfragmentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import secondway.MyDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogFragment bdf = new BottomDialogFragment();
                SwipeDialogFragment sdf = new SwipeDialogFragment();
                MyDialogFragment mdf = new MyDialogFragment();
                getSupportFragmentManager().beginTransaction().add(mdf, "").commit();
            }
        });
    }
}
