package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.CongoThongListActivity;

/**
 * Created by kindlebit on 2/13/2018.
 */

public class ItemCongoThongAdapter extends RecyclerView.Adapter<ItemCongoThongAdapter.MyViewHolder> {

    private ArrayList<HashMap<String, String>> arrayList;
    private String _type = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date, msg;
        private CardView layMain;
        private ImageView imgDelete;


        public MyViewHolder(View view) {
            super(view);
            date     = (TextView) view.findViewById(R.id.tvdate);
            msg      = (TextView) view.findViewById(R.id.tvmsg);
            layMain  = (CardView) view.findViewById(R.id.loMainItem);
            imgDelete = (ImageView) view.findViewById(R.id.img_delete);
        }
    }


    public ItemCongoThongAdapter(ArrayList<HashMap<String,String>> arrayList, String typenew) {
        this.arrayList = arrayList;
        _type = typenew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_congo_thong, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final HashMap<String, String> hashMap = arrayList.get(position);
        holder.date.setText(hashMap.get("date"));
        holder.msg.setText(hashMap.get("msg"));

        holder.layMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CongoThongListActivity.congThongListActivity.openDetail(hashMap.get("type"), hashMap.get("from"), hashMap.get("msg")
                        , hashMap.get("date"), hashMap.get("time"));
            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CongoThongListActivity.congThongListActivity.deleteItem(position, _type);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
