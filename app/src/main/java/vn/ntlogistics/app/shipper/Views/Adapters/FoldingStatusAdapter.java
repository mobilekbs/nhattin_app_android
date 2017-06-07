package vn.ntlogistics.app.shipper.Views.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomLayoutManager;
import vn.ntlogistics.app.shipper.Commons.CustomViews.FoldableLayout;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.StatusBL;
import vn.ntlogistics.app.shipper.R;


/**
 * Created by Zanty on 13/08/2016.
 */
public class FoldingStatusAdapter extends RecyclerView.Adapter<FoldingStatusAdapter.DataObjectHolder> {
    private Map<Integer, Boolean> mFoldStates = new HashMap<>();
    Context context;
    List<String> mList;
    List<String> mListStatus;
    String job;
    String status;
    List<List<StatusBL>> mListList;
    RecyclerView rv;

    public FoldingStatusAdapter(Context context, List<String> mList, String job, String status, List<List<StatusBL>> mListList, RecyclerView rv, List<String> mListStatus) {
        this.context = context;
        this.mList = mList;
        this.job = job;
        this.status = status;
        this.mListList = mListList;
        this.rv = rv;
        this.mListStatus = mListStatus;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        protected FoldableLayout mFoldableLayout;
        TextView tvHeader, tvBLCode, tvJob, tvStatus, tvOrderCodeDetail;
        RecyclerView rvDetail;
        int iTag;
        public DataObjectHolder(FoldableLayout foldableLayout) {
            super(foldableLayout);
            mFoldableLayout = foldableLayout;
            foldableLayout.setupViews(R.layout.item_group_status, R.layout.detail_status, R.dimen.status_height, itemView.getContext());

            tvHeader = (TextView) foldableLayout.findViewById(R.id.tvHeader);
            tvBLCode = (TextView) foldableLayout.findViewById(R.id.tvBLCode);
            tvJob = (TextView) foldableLayout.findViewById(R.id.tvJob);
            tvStatus = (TextView) foldableLayout.findViewById(R.id.tvStatus);
            tvOrderCodeDetail = (TextView) foldableLayout.findViewById(R.id.tvOrderCodeDetail);
            rvDetail = (RecyclerView) foldableLayout.findViewById(R.id.rvDetail);
        }
    }

    @Override
    public FoldingStatusAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_status, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);*/
        return new DataObjectHolder(new FoldableLayout(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final FoldingStatusAdapter.DataObjectHolder holder, int position) {
        holder.iTag = position;

        // Bind state
        if (mFoldStates.containsKey(position)) {
            if (mFoldStates.get(position) == Boolean.TRUE) {
                if (!holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.foldWithoutAnimation();
                }
            } else if (mFoldStates.get(position) == Boolean.FALSE) {
                if (holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.unfoldWithoutAnimation();
                }
            }
        } else {
            holder.mFoldableLayout.foldWithoutAnimation();
        }

        holder.tvOrderCodeDetail.setText(mList.get(position));

        holder.tvHeader.setText("#"+(position+1));
        holder.tvBLCode.setText(mList.get(position));
        holder.tvJob.setText(context.getResources().getString(R.string.sp_job)+": " + job);
        try {
            holder.tvStatus.setText(context.getResources().getString(R.string.orderdetail_tab4)+": " + mListStatus.get(position));
        } catch (Exception e) {

        }

        setupRecyclerView(holder, position);

        holder.mFoldableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.unfoldWithAnimation();
                } else {
                    holder.mFoldableLayout.foldWithAnimation();
                }
            }
        });
        holder.mFoldableLayout.setFoldListener(new FoldableLayout.FoldListener() {
            @Override
            public void onUnFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(5);
                }
            }

            @Override
            public void onUnFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(0);
                }
                mFoldStates.put(holder. hashCode() , false);
            }

            @Override
            public void onFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(5);
                }
            }

            @Override
            public void onFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(0);
                }
                mFoldStates.put(holder.getAdapterPosition(), true);
            }
        });
        holder.mFoldableLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        // Disallow ScrollView to intercept touch events.
                        ((CustomLayoutManager)rv.getLayoutManager()).setScrollEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

    private void setupRecyclerView(FoldingStatusAdapter.DataObjectHolder holder, int position) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rvDetail.setLayoutManager(linearLayoutManager);
        holder.rvDetail.setHasFixedSize(true);
        List<StatusBL> mListSts = null;
        try {
            mListSts = mListList.get(position);
        } catch (Exception e) {
            mListSts = new ArrayList<>();
        }
        StatusAdapter adapter = new StatusAdapter(
                context,
                mListSts,
                rv
        );
        holder.rvDetail.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
