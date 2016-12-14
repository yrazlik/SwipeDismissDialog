package customview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * @author kakajika
 * @since 15/08/15.
 */
public class SwipeableFrameLayout extends FrameLayout {

    private SwipeDismissTouchListener mTouchListener;
    private boolean swipeDismissEnabled = false;
    private View scrollableView;

    public SwipeableFrameLayout(Context context) {
        super(context);
    }

    public void setSwipeDismissTouchListener(SwipeDismissTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    public void setScrollableView(View v) {
        scrollableView = v;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(scrollableView == null) {
            return true;
        } else if(scrollableView instanceof ListView) {
            ListView lv = (ListView) scrollableView;
            lv.getChildAt(0); //TODO: güzeeeel devam et. lv.getChildAt(0).getTop() == 0 mı diye falan bak!!

        }
        return false;
      /*  if(swipeDismissEnabled) {
            return true;
        }*/
        /*if (mTouchListener != null) {
            if (mTouchListener.onTouch(this, ev)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);*/
    }

    public void enableSwipeDismissBehavior(boolean enabled) {
        swipeDismissEnabled = enabled;
    }

}
