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

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BillDetail;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderDetailActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ItemListviewBinding;

import static vn.ntlogistics.app.ordermanagement.Commons.Commons.setEnabledButton;


/**
 * Created by Zanty on 15/06/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataObjectHolder> {

    Context context;
    List<Bill> mList;
    //Order homeItem;
    BaseFragment fragment;
    boolean mAllEnabled = false;
    int status;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    public RecyclerViewAdapter(Context context, List<Bill> mList, BaseFragment fragment, int status) {
        this.context = context;
        this.mList = mList;
        this.fragment = fragment;
        this.status = status;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        ItemListviewBinding     binding;
        int iTag;

        /*TextView tvCongViec, tvTitle, tvDay, tvTime,
                tvStop, tvLocationItemList, tvCODItem,
                tvAdvanceItem, tvShipFeeItem, tvNoteItem;
        HeaderLayout header;
        int iTag;
        View main, lnLocationItemList, lnAdvanceItem, lnCODItem, lnNoteItem;
        CheckBox checkBox;
        ImageView ivIconClock, ivIconLocationItem;

        //HUB
        TextView tvDistanceHub, tvTimeHub;
        View lnAmoutDistance, lnTimeHub;
        //LinearLayout lnStopPlaceHub;*/

        public DataObjectHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            /*header = (HeaderLayout) itemView.findViewById(R.id.header);

            main = itemView.findViewById(R.id.mainList);
            tvTitle = header.getTvTitle();
            tvDay = header.getTvDay();
            tvTime = header.getTvTime();
            tvShipFeeItem = (TextView) itemView.findViewById(R.id.tvShipFeeItem);
            tvNoteItem = (TextView) itemView.findViewById(R.id.tvNoteItem);
            tvStop = (TextView) itemView.findViewById(R.id.tvStop);
            tvCongViec = (TextView) itemView.findViewById(R.id.tvCongViec);
            tvLocationItemList = (TextView) itemView.findViewById(R.id.tvLocationItemList);
            tvCODItem = (TextView) itemView.findViewById(R.id.tvCODItem);
            tvAdvanceItem = (TextView) itemView.findViewById(R.id.tvAdvanceItem);
            ivIconLocationItem = (ImageView) itemView.findViewById(R.id.ivIconLocationItem);
            lnAdvanceItem = itemView.findViewById(R.id.lnAdvanceItem);
            lnCODItem = itemView.findViewById(R.id.lnCODItem);
            lnNoteItem = itemView.findViewById(R.id.lnNoteItem);
            lnLocationItemList = itemView.findViewById(R.id.lnLocationItemList);
            //btnReceiveOrderItem = itemView.findViewById(R.id.btnReceiveOrderItem);
            //lnBtnReceiveItem = itemView.findViewById(R.id.lnBtnReceiveItem);
            ivIconClock = header.getIvIconClock();
            checkBox = header.getCheckBox();

            //HUB
            tvDistanceHub = (TextView) itemView.findViewById(R.id.tvDistanceHub);
            tvTimeHub = (TextView) itemView.findViewById(R.id.tvTimeHub);
            lnAmoutDistance = itemView.findViewById(R.id.lnAmoutDistance);
            lnTimeHub = itemView.findViewById(R.id.lnTimeHub);
            //lnMainStopPlace = itemView.findViewById(R.id.lnMainStopPlace);
            //lnStopPlaceHub = (LinearLayout) itemView.findViewById(R.id.lnStopPlaceHub);*/
        }

        /*public void clearAnimation() {
            binding.mainList.clearAnimation();
        }*/
    }

    @Override
    public RecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_listview, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.DataObjectHolder holder, final int position) {
        Bill bill = mList.get(position);
        holder.iTag = position;

        holder.binding.tvTitleItemList.setText(bill.getBillID());
        if(bill.getSendDate() != null && bill.getSendDate().length() > 0){
            try {
                String[] sendDate = bill.getSendDate().split(" ");
                holder.binding.tvDay.setText(sendDate[0]);
                holder.binding.tvTime.setText(sendDate[1]);
            } catch (Exception e) {
            }
        }


        /*if (bill.getTotalDistance() > 0) {
            holder.lnAmoutDistance.setVisibility(View.VISIBLE);
            long dis = bill.getTotalDistance();
            holder.tvDistanceHub.setText(bill.getTotalDistanceText());
        } else*/
            //holder.lnAmoutDistance.setVisibility(View.GONE);

        //Don hang thuong
        holder.binding.tvOTP.setText(bill.getOtpCode());
        holder.binding.tvCongViec.setText(bill.getJobType()+"");
        holder.binding.tvName.setText(bill.getSenderName());
        holder.binding.tvPhone.setText(bill.getSenderNumberPhone());
        holder.binding.tvLocationItemList.setText(bill.getSenderAddress());
        if(bill.getSenderNode() != null && bill.getSenderNode().length() > 0){
            holder.binding.lnNoteItem.setVisibility(View.VISIBLE);
            holder.binding.tvNoteItem.setText(bill.getSenderNode());
        }
        else {
            holder.binding.lnNoteItem.setVisibility(View.GONE);
        }

        //TODO: set action check checkbox
        /*if (bill.isCheck()) {
            holder.binding.header.getCheckBox().setChecked(true);
        }
        else
            holder.binding.header.getCheckBox().setChecked(false);

        holder.binding.header.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.get(holder.iTag).setCheck(holder.binding.header.getCheckBox().isChecked());
                showUpdateButton();
            }
        });*/

        //setupButtonReceieOrder(holder, order);

        holder.binding.mainList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabledButton(v);
                callAPIOrderDetail(mList.get(holder.iTag),holder.iTag,null);
            }
        });


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
