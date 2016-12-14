package secondway;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
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
