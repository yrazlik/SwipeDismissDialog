package secondway;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.randomapps.dragfragmentdemo.R;

/**
 * Created by yrazlik on 14/12/16.
 */

public class MyDialogFragment extends DialogFragment{

    RelativeLayout bar;
    Button dialogBtn;
    SwipeDismissTouchEventListener swipeDismissTouchEventListener;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, null, false);

        swipeDismissTouchEventListener = new SwipeDismissTouchEventListener(getDialog().getWindow().getDecorView(), "layout", new SwipeDismissTouchEventListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(Object token) {
                return isCancelable();
            }

            @Override
            public void onDismiss(View view, boolean toRight, Object token) {
                dismiss();
            }
        });

        lv = (ListView) v.findViewById(R.id.lv);
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
        lv.setOnTouchListener(new View.OnTouchListener() {
            String direction = "up";
            float y1 = 0, y2 = 0, y3 = 0, dy;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        y1 = motionEvent.getY();
                        break;
                    }
                /*    case MotionEvent.ACTION_UP: {
                        y2 = motionEvent.getY();
                        dy = y2 - y1;

                        // Use dx and dy to determine the direction

                        if (dy > 0)
                            direction = "down";
                        else
                            direction = "up";

                    }*/
                    case MotionEvent.ACTION_MOVE: {
                        y3 = motionEvent.getY();
                        dy = y3 - y1;
                        if(dy > 0) {
                            direction = "down";
                            Log.d("DIRECTION", direction);
                        } else {
                            direction = "up";
                            Log.d("DIRECTION", direction);
                        }
                        break;
                    }
                }

                if((lv.getChildCount() == 0 || lv.getChildAt(0).getTop() == 0) && direction.equalsIgnoreCase("down")) {
                    swipeDismissTouchEventListener.onTouch(getDialog().getWindow().getDecorView(), motionEvent);
                } else {

                }
                return false;
            }
        });



        getDialog().getWindow().getDecorView().setOnTouchListener(swipeDismissTouchEventListener);
        setDialogPreferences(getDialog(), R.style.dialogAnimation);

        return v;
    }



    protected void setDialogPreferences(Dialog d, int animation){

        d.getWindow().getAttributes().windowAnimations = animation;

        Window window = d.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = (int)(getResources().getDisplayMetrics().heightPixels*1);

        window.setLayout(width, height);

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

    }


}
