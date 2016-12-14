package customview;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import customview.SwipeDismissTouchListener;

/**
 * @author kakajika
 * @since 15/08/15.
 */
public class SwipeableFrameLayout extends FrameLayout {

    private SwipeDismissTouchListener mTouchListener;
    private boolean swipeDismissEnabled = false;

    public SwipeableFrameLayout(Context context) {
        super(context);
    }

    public void setSwipeDismissTouchListener(SwipeDismissTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(swipeDismissEnabled) {
            return true;
        }
        return false;
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
