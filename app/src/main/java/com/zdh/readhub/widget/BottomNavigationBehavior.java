package com.zdh.readhub.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zdh on 2018/6/5.
 * 自定义行为
 * 实现知乎一样，上滑隐藏导航栏
 */

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView>{
    private ObjectAnimator outAnimator, inAnimator;

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                                 @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target,
                                                 int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View target, int dx, int dy, int[] consumed) {
        if (target instanceof NestedScrollView) {
            return;
        }
        if (dy > 0) {
            // 隐藏
            hideBottomNavigationView(child);
        } else if (dy < 0) {
            showBottomNavigation(child);
        }
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        if (outAnimator == null) {
            outAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight());
            outAnimator.setDuration(200);
        }
        if (!outAnimator.isRunning() && view.getTranslationY() <= 0) {
            outAnimator.start();
        }
    }

    private void showBottomNavigation(BottomNavigationView view) {
        if (inAnimator == null) {
            inAnimator = ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0);
        }
        if (!inAnimator.isRunning() && view.getTranslationY() >= view.getHeight()) {
            inAnimator.start();
        }
    }
}
