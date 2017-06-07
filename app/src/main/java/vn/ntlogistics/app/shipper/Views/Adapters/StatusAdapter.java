package vn.ntlogistics.app.shipper.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomLayoutManager;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.StatusBL;
import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 12/07/2016.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.DataObjectHolder> {

    Context context;
    List<StatusBL> mList;
    RecyclerView rv;

    public StatusAdapter(Context context, List<StatusBL> mList, RecyclerView rv) {
        this.context = context;
        this.mList = mList;
        this.rv = rv;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvCongViec, tvResult, tvDay, tvTime;
        LinearLayout mainList;
        int iTag;
        public DataObjectHolder(View itemView) {
            super(itemView);
            tvCongViec = (TextView) itemView.findViewById(R.id.tvCongViec);
            tvResult = (TextView) itemView.findViewById(R.id.tvResult);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mainList = (LinearLayout) itemView.findViewById(R.id.mainList);
        }
    }

    @Override
    public StatusAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(StatusAdapter.DataObjectHolder holder, int position) {
        holder.iTag = position;
        holder.tvCongViec.setText(mList.get(position).getJobName());
        holder.tvResult.setText(mList.get(position).getStatus());
        long stamp = Long.parseLong(mList.get(position).getInsertDate());
        holder.tvDay.setText(Commons.timeStampToDate(stamp));
        holder.tvTime.setText(Commons.timeStampToTime(stamp));
        holder.mainList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        // Disallow ScrollView to intercept touch events.
                        ((CustomLayoutManager)rv.getLayoutManager()).setScrollEnabled(false);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Allow ScrollView to intercept touch events.
                        ((CustomLayoutManager)rv.getLayoutManager()).setScrollEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
