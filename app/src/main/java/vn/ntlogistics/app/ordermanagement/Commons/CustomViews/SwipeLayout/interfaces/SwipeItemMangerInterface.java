package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.SwipeLayout.interfaces;


import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.SwipeLayout.SwipeLayout;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.SwipeLayout.util.Attributes;


public interface SwipeItemMangerInterface {

    void openItem(int position);

    void closeItem(int position);

    void closeAllExcept(SwipeLayout layout);
    
    void closeAllItems();

    List<Integer> getOpenItems();

    List<SwipeLayout> getOpenLayouts();

    void removeShownLayouts(SwipeLayout layout);

    boolean isOpen(int position);

    Attributes.Mode getMode();

    void setMode(Attributes.Mode mode);
}
