package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Models.Beans.ItemSelectDay;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.databinding.ItemSelectDayBinding;


/**
 * Created by Zanty on 15/06/2016.
 */
public class ItemSelectDayAdapter extends RecyclerView.Adapter<ItemSelectDayAdapter.DataObjectHolder> {
    Context context;
    ArrayList<ItemSelectDay> mList;
    private ViewModel viewModel;

    public ItemSelectDayAdapter(Context context,
                                ArrayList<ItemSelectDay> mList,
                                ViewModel viewModel) {
        this.context = context;
        this.mList = mList;
        this.viewModel = viewModel;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        ItemSelectDayBinding binding;
        int iTag;
        public DataObjectHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    @Override
    public ItemSelectDayAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_select_day, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final ItemSelectDayAdapter.DataObjectHolder holder, final int position) {
        ItemSelectDay item = mList.get(position);
        holder.iTag = position;
        holder.binding.tvNameDay.setText(item.getNameDay());
        holder.binding.tvNumberDay.setText(item.getNumberDay());

        if(item.isSelected()){
            holder.binding.loMainItemSD.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.colorBlackGray));
        }
        else {
            holder.binding.loMainItemSD.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.colorBlackTrangsperant));
        }

        holder.binding.loDayItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                selectItem(holder.iTag);
                viewModel.onSuccess(holder.iTag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void selectItem(int position){
        for (int i = 0; i < mList.size(); i++){
            if(mList.get(i).isSelected()){
                mList.get(i).setSelected(false);
                notifyItemChanged(i);
                break;
            }
        }
        mList.get(position).setSelected(true);
        notifyItemChanged(position);
    }

}
