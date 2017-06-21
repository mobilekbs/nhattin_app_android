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
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SJob;
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
        //final Order order = order;
        holder.binding.header.setHeaderService(
                SJob.compareServiceShip(bill.getJobType()),
                0
        );

        holder.binding.header.getTvTitle().setText(bill.getBillID());

        /*if (bill.getTotalDistance() > 0) {
            holder.lnAmoutDistance.setVisibility(View.VISIBLE);
            long dis = bill.getTotalDistance();
            holder.tvDistanceHub.setText(bill.getTotalDistanceText());
        } else*/
            //holder.lnAmoutDistance.setVisibility(View.GONE);

        //Don hang thuong
        holder.binding.tvCongViec.setText(bill.getJobType()+"");
        holder.binding.tvName.setText(bill.getSenderName());
        holder.binding.tvPhone.setText(bill.getSenderNumberPhone());
        holder.binding.tvLocationItemList.setText(bill.getSenderAddress());
        if(bill.getSenderNode().length() > 0){
            holder.binding.lnNoteItem.setVisibility(View.VISIBLE);
            holder.binding.tvNoteItem.setText(bill.getSenderNode());
        }
        else {
            holder.binding.lnNoteItem.setVisibility(View.GONE);
        }
        /*if (bill.getOrderType() == Constants.TYPE_SHIP_CARGO) { //Don hang thuong = 0
            //holder.lnMainStopPlace.setVisibility(View.GONE);
            holder.lnAdvanceItem.setVisibility(View.VISIBLE);
            holder.lnCODItem.setVisibility(View.VISIBLE);
            holder.tvShipFeeItem.setText(context.getString(R.string.fee_ship));
            holder.ivIconLocationItem.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_location));
            if (bill.getSourceAddress() != null && bill.getSourceAddress().length() > 0) {
                holder.lnLocationItemList.setVisibility(View.VISIBLE);
                String address = bill.getSourceAddress() + " -> " + bill.getDestinationAddress();
                holder.tvLocationItemList.setText(address);
            } else {
                holder.lnLocationItemList.setVisibility(View.GONE);
            }
        } else if (bill.getOrderType() == 1) { //Don hang sieu toc vs 1shipU
            //holder.lnMainStopPlace.setVisibility(View.GONE);
            holder.lnAdvanceItem.setVisibility(View.VISIBLE);
            holder.lnCODItem.setVisibility(View.VISIBLE);
            holder.tvShipFeeItem.setText(context.getString(R.string.fee_ship));
            holder.ivIconLocationItem.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_location));
            if (bill.getSourceAddress() != null && bill.getSourceAddress().length() > 0) {
                holder.lnLocationItemList.setVisibility(View.VISIBLE);
                //String address = LocationCommon.cutHeaderAddress(order.getSourceAddress()) + " -> " + LocationCommon.cutHeaderAddress(order.getDestinationAddress());
                holder.tvLocationItemList.setText(bill.getSourceAddress());
            } else {
                holder.lnLocationItemList.setVisibility(View.GONE);
            }
        } *//*else if (order.getOrderType() == 2) { //Don hang HUB
            holder.tvShipFeeItem.setText(context.getString(R.string.fee_ship));
            holder.ivIconLocationItem.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_location));
            holder.lnLocationItemList.setVisibility(View.GONE);
            holder.lnMainStopPlace.setVisibility(View.VISIBLE);
            holder.lnAdvanceItem.setVisibility(View.VISIBLE);
            holder.lnCODItem.setVisibility(View.VISIBLE);
            //Create Hub
            createItemHub(holder, order);
        }*//* *//*else if (order.getOrderType() == Constants.TYPE_SHIP_K
                && order.getJobType() == Constants.JOB_SHIP_K) { //Mua hang sieu thi = 3
            holder.tvShipFeeItem.setText(context.getString(R.string.total_fee_shipk));
            holder.lnAdvanceItem.setVisibility(View.GONE);
            holder.lnCODItem.setVisibility(View.GONE);
            holder.lnMainStopPlace.setVisibility(View.GONE);
            holder.ivIconLocationItem.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_shop_gray));
            if (order.getSourceAddress() != null && order.getSourceAddress().length() > 0) {
                holder.lnLocationItemList.setVisibility(View.VISIBLE);
                String address = order.getSourceAddress();
                holder.tvLocationItemList.setText(address);
            } else {
                holder.lnLocationItemList.setVisibility(View.GONE);
            }
        }*//*
        else {
            //holder.lnMainStopPlace.setVisibility(View.GONE);
            holder.lnAdvanceItem.setVisibility(View.VISIBLE);
            holder.lnCODItem.setVisibility(View.VISIBLE);
            holder.tvShipFeeItem.setText(context.getString(R.string.fee_ship));
            holder.ivIconLocationItem.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_location));
            if (bill.getSourceAddress() != null && bill.getSourceAddress().length() > 0) {
                holder.lnLocationItemList.setVisibility(View.VISIBLE);
                String address = bill.getSourceAddress() + " -> " + bill.getDestinationAddress();
                holder.tvLocationItemList.setText(address);
            } else {
                holder.lnLocationItemList.setVisibility(View.GONE);
            }
        }*/

        /*if (SJob.compareServiceShip(bill.getJobType()) == EServiceShip.SHIP_U) {
            long stamp = Long.parseLong(bill.getAssignDatetime());
            holder.tvDay.setText(Commons.timeStampToDateNotYear(stamp));
            holder.tvTime.setText(Commons.timeStampToTime(stamp));
        } else if (SJob.compareServiceShip(bill.getJobType()) == EServiceShip.SHIP_CARGO) {
            long stamp = Long.parseLong(bill.getAssignDatetime());
            holder.tvDay.setText(Commons.timeStampToDateNotYear(stamp));
            holder.tvTime.setText(bill.getCategoryTitle());
        }
        if (bill.getCodAmount() == 0) { //Nếu COD = 0 thì cho hiện advance fee dù cho nó bằng 0
            holder.lnCODItem.setVisibility(View.GONE);
            holder.lnAdvanceItem.setVisibility(View.VISIBLE);
            holder.tvAdvanceItem.setText(Commons.DinhDangChuoiTien(bill.getAdvanceCodAmount()) + context.getString(R.string.unit));
        } else if (bill.getCodAmount() > 0) { // nếu COD > 0 thì cho hiện và advanceFee sẽ hiện nếu > 0
            holder.lnCODItem.setVisibility(View.VISIBLE);
            holder.tvCODItem.setText(Commons.DinhDangChuoiTien(bill.getCodAmount()) + context.getString(R.string.unit));
            if (bill.getAdvanceCodAmount() > 0) {
                holder.lnAdvanceItem.setVisibility(View.VISIBLE);
                holder.tvAdvanceItem.setText(Commons.DinhDangChuoiTien(bill.getAdvanceCodAmount()) + context.getString(R.string.unit));
            } else {
                holder.lnAdvanceItem.setVisibility(View.GONE);
            }
        }
        holder.tvStop.setText(Commons.DinhDangChuoiTien(bill.getShipperAmount()) + context.getString(R.string.unit));
        //holder.tvCODItem.setText(Utils.DinhDangChuoiTien(order.getAdvanceCodAmount()) + context.getString(R.string.unit));

        String job = SJob.getJobNameById(context, bill.getJobType());
        holder.tvCongViec.setText(job);

        *//*int stopCount = Integer.parseInt(order.getStopCount());
        String showStopCount = "";
        if(stopCount > 0){
            showStopCount = " ("+ stopCount+" "+context.getResources().getString(R.string.stop)+")";
        }
        holder.tvDiaDiem.setText(order.getSourceAddress() + " -> " + order.getDestinationAddress()
                +showStopCount);*//*
        holder.iTag = position;*/
        //TODO: set action check checkbox
        if (bill.isCheck()) {
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
        });

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

    /*private void setupButtonReceieOrder(final DataObjectHolder holder, final Order order) {
        holder.lnBtnReceiveItem.setVisibility(View.GONE);

        holder.btnReceiveOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabledButton(v);
                final CustomDialog dialog = new CustomDialog((Activity) context);
                dialog.setTextTitle(context.getResources().getString(R.string.note_dialog));
                if(order.getStringNode().length() > 0) {
                    dialog.setNoteDialog(order.getLstNote());
                }
                dialog.setTitleMessage(context.getResources().getString(R.string.dialog_receive_button));
                dialog.setShow(true);
                dialog.setTextButton(context.getResources().getString(R.string.yes), context.getResources().getString(R.string.no));
                dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                    @Override
                    public void onClickOk() {
                        callButtonReceieOrder(order,holder.iTag,fragment);
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }*/

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

    //TODO: Create item
    /*public void createItemHub(RecyclerViewAdapter.DataObjectHolder holder, Order order) {
        //long stamp = Long.parseLong(order.getAssignDatetime());
        //holder.tvTimeHub.setText(Utils.timeStampToTime(stamp) + " " +Utils.timeStampToDate(stamp));
        holder.lnStopPlaceHub.removeAllViews();
        List<String> mList = order.getLstDistrictCode();

        //Diem bat dau
        LayoutItemStopPlaceHub item1 = drawItemHub();
        item1.setTextNumber("A");
        item1.setImageHub(R.mipmap.ic_bg_number_green);
        item1.setTextAddress(mList.get(0));
        holder.lnStopPlaceHub.addView(item1);

        for (int i = 1; i < mList.size() - 1; i++) {
            LayoutItemStopPlaceHub item = drawItemHub();
            item.setTextNumber(i + "");
            item.setTextAddress(mList.get(i));
            holder.lnStopPlaceHub.addView(item);
        }

        //Diem ket thuc
        LayoutItemStopPlaceHub item2 = drawItemHub();
        item2.setTextNumber("B");
        item2.setHideViewHub1(true);
        item2.setImageHub(R.mipmap.ic_bg_number_red);
        item2.setTextAddress(mList.get(mList.size() - 1));
        holder.lnStopPlaceHub.addView(item2);
    }

    private LayoutItemStopPlaceHub drawItemHub() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutItemStopPlaceHub reveicer = new LayoutItemStopPlaceHub(context);
        reveicer.setLayoutParams(params);
        return reveicer;
    }*/

    @Override
    public void onViewDetachedFromWindow(DataObjectHolder holder) {
        //holder.clearAnimation();
    }


    public void showUpdateButton() {
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
    }

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
