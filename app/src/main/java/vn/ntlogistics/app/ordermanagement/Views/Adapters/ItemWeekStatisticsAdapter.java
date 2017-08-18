package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Shipper.Productivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.databinding.ItemWeekStatisticsBinding;


/**
 * Created by Zanty on 15/06/2016.
 */
public class ItemWeekStatisticsAdapter extends RecyclerView.Adapter<ItemWeekStatisticsAdapter.DataObjectHolder> {
    private Context                     context;
    private ArrayList<Productivity>     mList;
    private ViewModel                   viewModel;

    public ItemWeekStatisticsAdapter(Context context,
                                     ArrayList<Productivity> mList,
                                     ViewModel viewModel) {
        this.context = context;
        this.mList = mList;
        this.viewModel = viewModel;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        ItemWeekStatisticsBinding binding;
        int iTag;
        public DataObjectHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bindObject(Productivity item) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(item);
            } else {
                binding.setViewModel(item);
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
    public ItemWeekStatisticsAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_week_statistics, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final ItemWeekStatisticsAdapter.DataObjectHolder holder, final int position) {
        Productivity item = mList.get(position);
        holder.bindObject(item);
        holder.iTag = position;

        holder.binding.loMainItemWeekStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                viewModel.onSuccess(holder.iTag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
