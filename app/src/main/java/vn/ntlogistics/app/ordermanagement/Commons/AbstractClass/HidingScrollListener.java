package vn.ntlogistics.app.ordermanagement.Commons.AbstractClass;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Zanty on 16/06/2016.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 40;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        int count = recyclerView.getAdapter().getItemCount();
        if (newState == 0) {
            //position item cuối đang hiển thị mà bằng tổng số item lst thì load
            if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() >= count - 2) {
                loadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        //show views if first item is first visible position and views are hidden
        if (firstVisibleItem == 0) {
            if(!controlsVisible) {
                onUp();
                controlsVisible = true;
            }
        } else {
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                onUp();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                onDown();
                controlsVisible = true;
                scrolledDistance = 0;
            }
        }

        if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onUp();
    public abstract void onDown();
    public abstract void loadMore();
}
