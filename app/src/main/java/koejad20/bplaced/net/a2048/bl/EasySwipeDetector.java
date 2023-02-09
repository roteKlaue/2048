package koejad20.bplaced.net.a2048.bl;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class EasySwipeDetector {
    private final GestureDetector gestureDetector;

    public EasySwipeDetector(Context context, int minDistance, int minVelocity) {
        gestureDetector = new GestureDetector(context, new GestureListener(minDistance, minVelocity));
    }

    public abstract void onSwipeLeft(float distanceX, float distanceY);
    public abstract void onSwipeRight(float distanceX, float distanceY);
    public abstract void onSwipeDown(float distanceX, float distanceY);
    public abstract void onSwipeUp(float distanceX, float distanceY);

    public boolean onTouch(View ignoredV, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int SWIPE_DISTANCE_THRESHOLD;
        private final int SWIPE_VELOCITY_THRESHOLD;

        public GestureListener(int minDistance, int minVelocity) {
            SWIPE_DISTANCE_THRESHOLD = minDistance;
            SWIPE_VELOCITY_THRESHOLD = minVelocity;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight(distanceX, distanceY);
                else
                    onSwipeLeft(distanceX, distanceY);
                return true;
            } else if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(distanceX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceY > 0)
                    onSwipeDown(distanceX, distanceY);
                else
                    onSwipeUp(distanceX, distanceY);
                return true;
            }
            return false;
        }
    }
}
