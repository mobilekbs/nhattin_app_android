package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BillDetail;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.ItemAdapterVMs.ItemOrderVM;
import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderDetailActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ItemOrderBinding;


/**
 * Created by Zanty on 15/06/2016.
 */
public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.DataObjectHolder> {
    Context context;
    ArrayList<Bill> mList;
    BaseFragment fragment;
    boolean mAllEnabled = false;
    int status;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    public ItemOrderAdapter(Context context,
                            ArrayList<Bill> mList,
                            BaseFragment fragment,
                            int status) {
        this.context = context;
        this.mList = mList;
        this.fragment = fragment;
        this.status = status;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public DataObjectHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bindObject(Bill item, int position) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemOrderVM(context, item, position));
            } else {
                binding.getViewModel().setItem(item, position);
                /**
                 * Dòng code thần thánh
                 * Nếu không có nó, thế giới sẽ bị diệt vong.
                 * Xạo thôi, thật ra không có nó thì scroll lag lag.
                 * Để lại cho hậu thế
                 *                          - Tân -
                 */
                binding.executePendingBindings();
            }
        }
    }

    @Override
    public ItemOrderAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_order, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final ItemOrderAdapter.DataObjectHolder holder, final int position) {
        Bill bill = mList.get(position);

        holder.bindObject(bill, position);

        //setAnimation(holder.main, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewAttachedToWindow(DataObjectHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.itemView.setEnabled(isAllItemsEnabled());
        holder.itemView.setClickable(isAllItemsEnabled());
    }

    private void callAPIOrderDetail(Bill bill, int position, BaseFragment fragment){
        try {
            OrderDetailActivity.startIntentActivity(context,
                    new BillDetail(),
                    bill,
                    8,
                    position);
            /*JSOrderDetail data = new JSOrderDetail(context);
            data.setShippingCode(order.getShippingCode().toString());
            data.setStatusId(status + "");
            data.setJobType(order.getJobType() + "");

            String json = new Gson().toJson(data);
            new OrderDetailAPI(
                    context,
                    json,
                    order,
                    status,
                    position,
                    false,
                    fragment
            ).execute();*/
        } catch (Exception e) {
            Log.d("RecyclerViewAdapter", "callButtonReceieOrder -------------");
            e.printStackTrace();
        }
    }

    @Override
    public void onViewDetachedFromWindow(DataObjectHolder holder) {
        //holder.clearAnimation();
    }


    /*public void showUpdateButton() {
        int a = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isCheck())
                a++;
        }
        if (a == mList.size())
            fragment.showControls(true, 1);
        else if (a > 0 && a < mList.size())
            fragment.showControls(true, 0);
        else
            fragment.showControls(false, 0);
    }*/

    public boolean isAllItemsEnabled() {
        return mAllEnabled;
    }


    public boolean getItemEnabled(int position) {
        return true;
    }

    public void setAllItemsEnabled(boolean enable) {
        mAllEnabled = enable;
        notifyItemRangeChanged(0, getItemCount());
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
