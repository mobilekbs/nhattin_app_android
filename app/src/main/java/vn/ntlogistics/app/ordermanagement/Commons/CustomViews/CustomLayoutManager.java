package vn.ntlogistics.app.ordermanagement.Commons.CustomViews;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Zanty on 13/08/2016.
 */
public class CustomLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
