package com.randomapps.dragfragmentdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import customview.SwipeDismissDialogFragment;

/**
 * Created by yrazlik on 25/11/16.
 */

public class SwipeDialogFragment extends SwipeDismissDialogFragment{

    RelativeLayout bar;
    Button dialogBtn;

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

        setDialogPreferences(d, R.style.dialogAnimation);


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

}
