package vn.ntlogistics.app.shipper.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 28/07/2016.
 */
public class RecyclerViewServiceAdapter extends RecyclerView.Adapter<RecyclerViewServiceAdapter.DataObjectHolder> {

    Context context;
    List<Integer> mList;

    public RecyclerViewServiceAdapter(Context context, List<Integer> mList) {
        this.context = context;
        this.mList = mList;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        int iTag;
        public DataObjectHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public RecyclerViewServiceAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,parent,false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewServiceAdapter.DataObjectHolder holder, int position) {
        holder.iv.setImageDrawable(context.getResources().getDrawable(mList.get(position)));
        holder.iTag = position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
