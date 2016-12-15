package customview;

import android.content.Context;
import android.util.Log;
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

    private enum DIRECTION {
        UP,
        DOWN
    }

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
        scrollableView.setOnTouchListener(childScrollableViewTouchListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
     /*   if(scrollableView == null) {
            return true;
        } else if(scrollableView instanceof ListView) {
            ListView lv = (ListView) scrollableView;
            if((lv.getChildCount() == 0 || lv.getChildAt(0).getTop() == 0)) {
                return true;
            }
        }*/
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

    private OnTouchListener childScrollableViewTouchListener = new OnTouchListener() {

        private DIRECTION direction = DIRECTION.DOWN;
        private float prevY, currentY;
        private boolean mDragEventStarted = false;
        private boolean resetDragEvent = false;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    prevY = motionEvent.getRawY();
                    currentY = motionEvent.getRawY();
                    Log.d("TOUCH_EVENT", "");
                    break;
                case MotionEvent.ACTION_UP:
                    resetDragEvent = true;
                    prevY = 0;
                    currentY = 0;
                    Log.d("TOUCH_EVENT", "");
                    break;
                case MotionEvent.ACTION_CANCEL:
                    resetDragEvent = true;
                    prevY = 0;
                    currentY = 0;
                    Log.d("TOUCH_EVENT", "");
                    break;
                case MotionEvent.ACTION_MOVE:
                    prevY = currentY;
                    currentY = motionEvent.getRawY();
                    if(currentY - prevY < 0) {
                        direction = DIRECTION.UP;
                    } else {
                        direction = DIRECTION.DOWN;
                    }
                    Log.d("TOUCH_EVENT", direction.toString());
                    break;
            }

            if(view instanceof ListView) {
                ListView lv = (ListView) view;
                if(((lv.getChildCount() == 0 || lv.getChildAt(0).getTop() == 0) && direction == DIRECTION.DOWN) || mDragEventStarted) {
                    mDragEventStarted = true;
                    mTouchListener.onTouch(SwipeableFrameLayout.this, motionEvent);
                }
            }

            if(resetDragEvent) {
                resetDragEvent = false;
                mDragEventStarted = false;
            }

            return false;
        }
    };

}
