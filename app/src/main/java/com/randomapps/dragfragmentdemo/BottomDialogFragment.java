package com.randomapps.dragfragmentdemo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yrazlik on 24/11/16.
 */

public class BottomDialogFragment extends DialogFragment{

    float initialXPos;
    float initialYPos;
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


        bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int x = (int) motionEvent.getRawX();
                final int y = (int) motionEvent.getRawY();

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("MOTIONEVENT", "ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("MOTIONEVENT", "ACTION_UP");
                        dismiss();
                        break;
                    case MotionEvent.ACTION_MOVE:
                      //  getDialog().getWindow().getDecorView().setX(x);
                        getDialog().getWindow().getDecorView().setY(y);
                        getDialog().getWindow().getDecorView().invalidate();
                        break;
                }

                return true;
            }
        });

        return d;

    }


    @Override
    public void onResume() {
        super.onResume();
        initialXPos = getDialog().getWindow().getDecorView().getX();
        initialYPos = getDialog().getWindow().getDecorView().getY();
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
