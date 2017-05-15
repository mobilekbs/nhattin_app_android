package vn.ntlogistics.app.shipper.Views.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.shipper.Commons.CustomViews.SwipeLayout.SwipeLayout;
import vn.ntlogistics.app.shipper.Commons.CustomViews.SwipeLayout.adapters.RecyclerSwipeAdapter;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.Notify;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;

/**
 * Created by Zanty on 15/07/2016.
 */
public class RecyclerViewNotifyAdapter extends RecyclerSwipeAdapter<RecyclerViewNotifyAdapter.DataObjectHolder> {

    List<Notify> mList;
    Context context;
    BaseFragment fragment;
    SqliteManager db;

    public RecyclerViewNotifyAdapter(Context context, List<Notify> mList, BaseFragment fragment) {
        this.mList = mList;
        this.context = context;
        this.fragment = fragment;
        db = new SqliteManager(context);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        ImageView iv;
        TextView tvTitle, tvContent;
        int iTag;
        View btnDelete,lnBody;
        public DataObjectHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            lnBody = itemView.findViewById(R.id.lnBody);
        }
    }

    @Override
    public RecyclerViewNotifyAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewNotifyAdapter.DataObjectHolder holder, int position) {
        holder.iTag = position;
        // Drag From Right
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));

        if(mList.get(position).isSeen()){
            holder.iv.setVisibility(View.INVISIBLE);
        }
        else {
            holder.iv.setVisibility(View.VISIBLE);
            holder.iv.setImageDrawable(ContextCompat.getDrawable(
                    context, R.mipmap.ic_notification
            ));
        }
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvContent.setText(mList.get(position).getContent());
        holder.lnBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.get(holder.iTag).setSeen(true);
                db.updateNotification(mList.get(holder.iTag).getId());
                if(mList.get(holder.iTag).getUrl() != null) {
                    fragment.showControls(false, holder.iTag);
                }
                else {
                    final CustomDialog dialog = new CustomDialog((Activity) context);
                    dialog.setTextTitle(mList.get(holder.iTag).getTitle());
                    dialog.setTitleMessage(mList.get(holder.iTag).getContent());
                    dialog.setShow(true);
                    dialog.setShow1Button(true,context.getResources().getString(R.string.turn_off));
                    //dialog.setTextButton(context.getResources().getString(R.string.yes),context.getResources().getString(R.string.no));
                    dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                        @Override
                        public void onClickOk() {
                            fragment.showControls(false, -1);
                            dialog.dismiss();
                        }

                        @Override
                        public void onClickCancel() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mItemManger.removeShownLayouts(holder.swipeLayout);
                    db.deleteNotification(mList.get(holder.iTag).getId());
                    mList.remove(holder.iTag);
                    notifyItemRemoved(holder.iTag);
                    notifyItemRangeChanged(holder.iTag, mList.size());
                    ((MainActivity)context).setBadgeNotification(countNotifucationNotSeen(mList));
                    mItemManger.closeAllItems();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bind(holder.itemView, position);
    }

    private Integer countNotifucationNotSeen(List<Notify> mListNotify) {
        int count = 0;
        for (int i = 0; i < mListNotify.size(); i++) {
            if(!mListNotify.get(i).isSeen())
                count++;
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
