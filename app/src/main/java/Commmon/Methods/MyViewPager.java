package Commmon.Methods;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

    private boolean isPagingEnabled = true;
    private SwipeDirection direction;
    private float initialXValue;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        direction = SwipeDirection.all;
    }

    public MyViewPager(Context context) {
        super(context);
        direction = SwipeDirection.all;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isPagingEnabled) {
            if (this.IsSwipeAllowed(event)) {
                return super.onInterceptTouchEvent(event);
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isPagingEnabled) {
            if (this.IsSwipeAllowed(event)) {
                return super.onTouchEvent(event);
            }
        }
        return false;
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        isPagingEnabled = pagingEnabled;
    }

    private boolean IsSwipeAllowed(MotionEvent event) {
        if (this.direction == SwipeDirection.all)
            return true;

        if (direction == SwipeDirection.none)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection.right) {
                    // swipe from left to right detected
                    return false;
                } else if (diffX < 0 && direction == SwipeDirection.left) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
        Log.e("SwipeDirection :-->",direction.toString());
    }
}
