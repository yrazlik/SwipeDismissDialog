package customview;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.randomapps.dragfragmentdemo.SwipeDialogFragment;

import java.util.ArrayList;

/**
 * Created by yrazlik on 25/11/16.
 */

public class SwipeDismissDialogFragment extends DialogFragment{

    private boolean mSwipeable = true;
    private boolean mTiltEnabled = true;
    private boolean mSwipeLayoutGenerated = false;
    private SwipeDismissTouchListener mListener = null;
    private SwipeableFrameLayout swipeableLayout;


    /**
     * Set whether dialog can be swiped away.
     */
    public void setSwipeable(boolean swipeable) {
        mSwipeable = swipeable;
    }

    public void setScrollableView(View v) {
        if(swipeableLayout != null) {
            swipeableLayout.setScrollableView(v);
        }
    }

    /**
     * Get whether dialog can be swiped away.
     */
    public boolean isSwipeable() {
        return mSwipeable;
    }

    /**
     * Set whether tilt effect is enabled on swiping.
     */
    public void setTiltEnabled(boolean tiltEnabled) {
        mTiltEnabled = tiltEnabled;
        if (mListener != null) {
            mListener.setTiltEnabled(tiltEnabled);
        }
    }

    /**
     * Get whether tilt effect is enabled on swiping.
     */
    public boolean isTiltEnabled() {
        return mTiltEnabled;
    }

    /**
     * Called when dialog is swiped away to dismiss.
     * @return true to prevent dismissing
     */
    public boolean onSwipedAway(boolean toRight) {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mSwipeLayoutGenerated && getShowsDialog()) {
            Window window = getDialog().getWindow();
            ViewGroup decorView = (ViewGroup)window.getDecorView();
            View content = decorView.getChildAt(0);
            decorView.removeView(content);

            swipeableLayout = new SwipeableFrameLayout(getActivity());
            swipeableLayout.addView(content);
            decorView.addView(swipeableLayout);

            mListener = new SwipeDismissTouchListener(decorView, "layout", new SwipeDismissTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Object token) {
                    return isCancelable() && mSwipeable;
                }

                @Override
                public void onDismiss(View view, boolean toRight, Object token) {
                    if (!onSwipedAway(toRight)) {
                        dismiss();
                    }
                }
            });
            mListener.setTiltEnabled(mTiltEnabled);
            swipeableLayout.setSwipeDismissTouchListener(mListener);
            swipeableLayout.setOnTouchListener(mListener);
            swipeableLayout.setClickable(true);
            mSwipeLayoutGenerated = true;
        }
    }

    public void enableSwipeDismissBehavior(boolean enabled) {
        swipeableLayout.enableSwipeDismissBehavior(enabled);
    }
}
