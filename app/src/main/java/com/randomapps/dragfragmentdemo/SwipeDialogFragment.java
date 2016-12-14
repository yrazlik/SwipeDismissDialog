package com.randomapps.dragfragmentdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import customview.SwipeDismissDialogFragment;

/**
 * Created by yrazlik on 25/11/16.
 */

public class SwipeDialogFragment extends SwipeDismissDialogFragment{

    RelativeLayout bar;
    Button dialogBtn;
    ListView lv;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = new Dialog(getActivity());
        d.setContentView(R.layout.fragment);
        bar = (RelativeLayout) d.findViewById(R.id.bar);
        d.setCanceledOnTouchOutside(true);
        d.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        dialogBtn = (Button) d.findViewById(R.id.dialogBtn);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        lv = (ListView) d.findViewById(R.id.lv);
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android Example List View","Android Example List View","Android Example List View","Android Example List View",
                "Android Example List View","Android Example List View","Android Example List View","Android Example List View","Android Example List View"
                ,"Android Example List View","Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lv.setAdapter(adapter);

        setDialogPreferences(d, R.style.dialogAnimation);

     /*   lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if(lv.getChildCount() == 0 || lv.getChildAt(0).getTop() == 0) {
                            enableSwipeDismissBehavior(true);
                        } else {
                            enableSwipeDismissBehavior(false);
                        }
                        return false;
                    default:
                        return false;
                }
            }
        });*/

        return d;

    }

    protected void setDialogPreferences(Dialog d, int animation){

        d.getWindow().getAttributes().windowAnimations = animation;

        Window window = d.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.84);

        window.setLayout(width, height);

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

    }

    @Override
    public void onStart() {
        super.onStart();
        setScrollableView(lv);
    }
}
