package customview;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SwipeDismissTouchListener implements View.OnTouchListener {

    private long mAnimationTime;
    private float initialYPos;

    // Fixed properties
    private View mView;
    private DismissCallbacks mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

    // Transient properties
    private float mDownY;
    private Object mToken;
    private VelocityTracker mVelocityTracker;
    private float mTranslationX;


    private boolean mTiltEnabled = true;

    public interface DismissCallbacks {

        boolean canDismiss(Object token);

        void onDismiss(View view, boolean toRight, Object token);
    }

    public SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        mAnimationTime = view.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        mView = view;
        mToken = token;
        mCallbacks = callbacks;
    }

    public void setTiltEnabled(boolean tiltEnabled) {
        mTiltEnabled = tiltEnabled;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // offset because the view is translated during swipe
        motionEvent.offsetLocation(mTranslationX, 0);

        if (mViewWidth < 2) {
            mViewWidth = mView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                // TODO: ensure this is a finger, and set a flag
                initialYPos = motionEvent.getRawY();
                mDownY = motionEvent.getRawY();
                if (mCallbacks.canDismiss(mToken)) {
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                return false;
            }

            case MotionEvent.ACTION_UP: {
                initialYPos = 0;
                if (mVelocityTracker == null) {
                    break;
                }

                boolean dismiss = (mView.getY() >= mView.getHeight() / 5) ? true : false;
                if (dismiss) {
                    // dismiss
                    mView.animate()
                            .translationY(mView.getHeight())
                            .setDuration(mAnimationTime)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    performDismiss(false);
                                }
                            });
                } else {
                    // cancel
                    mView.animate()
                            .translationY(0)
                            .rotation(0)
                            .alpha(1)
                            .setDuration(mAnimationTime)
                            .setListener(null);
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mTranslationX = 0;
                mDownY = 0;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                initialYPos = 0;
                if (mVelocityTracker == null) {
                    break;
                }

                mView.animate()
                        .translationX(0)
                        .rotation(0)
                        .alpha(1)
                        .setDuration(mAnimationTime)
                        .setListener(null);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mTranslationX = 0;
                mDownY = 0;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (mVelocityTracker == null) {
                    break;
                }

                mVelocityTracker.addMovement(motionEvent);

                float deltaY = motionEvent.getRawY() - mDownY;
                if(motionEvent.getRawY() > initialYPos) {
                    mView.setTranslationY(deltaY);
                }
                break;
            }
        }
        return false;
    }

    private void performDismiss(final boolean toRight) {
        // Animate the dismissed view to zero-height and then fire the dismiss callback.
        // This triggers layout on each animation frame; in the future we may want to do something
        // smarter and more performant.

        final ViewGroup.LayoutParams lp = mView.getLayoutParams();
        final int originalHeight = mView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCallbacks.onDismiss(mView, toRight, mToken);
                // Reset view presentation
                mView.setAlpha(1f);
                mView.setTranslationX(0);
                mView.setRotation(0);
                lp.height = originalHeight;
                mView.setLayoutParams(lp);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                mView.setLayoutParams(lp);
            }
        });

        animator.start();
    }
}
