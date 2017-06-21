package vn.ntlogistics.app.ordermanagement.Commons.Animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * Created by Zanty on 16/07/2016.
 */
public class MyAnimation {
    public static void setVisibilityAnimationUp(final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.0f);
            view.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //view.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            view.animate()
                    .translationY(-view.getHeight())
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }
    public static void setVisibilityAnimationDown(final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.0f);
            view.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //view.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            view.setAlpha(1.0f);
            view.animate()
                    .translationY(view.getHeight())
                    .alpha(0.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public static void setVisibilityAnimationLeft(final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.0f);
            view.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setDuration(150)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //view.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            view.setAlpha(1.0f);
            view.animate()
                    .translationX(-view.getWidth())
                    .alpha(0.0f)
                    .setDuration(150)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }
    public static void setVisibilityAnimationRight(final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.0f);
            view.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setDuration(150)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //view.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            view.setAlpha(1.0f);
            view.animate()
                    .translationX(view.getWidth())
                    .alpha(0.0f)
                    .setDuration(150)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public static void setVisibilityAnimationSlide(final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0.0f);
            view.animate()
                    //.translationX(0)
                    .alpha(1.0f)
                    .setDuration(150)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //view.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            view.setAlpha(1.0f);
            view.animate()
                    //.translationX(view.getWidth())
                    .alpha(0.0f)
                    .setDuration(150)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public static void setVisibilityAnimationSlide(final View view, boolean b, int duration) {
        if (b) {
            view.setAlpha(0.0f);
            view.setVisibility(View.VISIBLE);
            view.animate()
                    //.translationX(0)
                    .alpha(1.0f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //view.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            view.setAlpha(1.0f);
            view.animate()
                    //.translationX(view.getWidth())
                    .alpha(0.0f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public static void setVisibilityAnimationSlide(final View view, boolean b, int duration, final IAnimationCallback callback) {
        if (b) {
            view.setAlpha(0.0f);
            view.setVisibility(View.VISIBLE);
            view.animate()
                    //.translationX(0)
                    .alpha(1.0f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            callback.callback();
                        }
                    });
        } else {
            view.setAlpha(1.0f);
            view.animate()
                    //.translationX(view.getWidth())
                    .alpha(0.0f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                            callback.callback();
                        }
                    });
        }
    }
}
