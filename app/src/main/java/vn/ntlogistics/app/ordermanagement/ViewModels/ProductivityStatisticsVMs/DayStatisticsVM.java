package vn.ntlogistics.app.ordermanagement.ViewModels.ProductivityStatisticsVMs;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Models.Beans.ItemSelectDay;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.GetProductivityAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.GetProductivityInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Shipper.Productivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ProductivityStatisticsActivity;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ItemSelectDayAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ItemWeekStatisticsAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.ProductivityStatistics.DayStatisticsFragment;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentDayStatisticsBinding;

/**
 * Created by Zanty on 26/07/2017.
 */

public class DayStatisticsVM extends ViewModel {
    private Context                             context;
    private DayStatisticsFragment               fragment;
    private FragmentDayStatisticsBinding        binding;

    //TODO: Params menu day
    private ArrayList<ItemSelectDay>            mListSelectDay;
    private ItemSelectDayAdapter                adapterSelectDay;

    //TODO: Recycler View
    private ArrayList<Productivity>             mList;
    private ItemWeekStatisticsAdapter           adapter;

    public DayStatisticsVM(DayStatisticsFragment fragment, FragmentDayStatisticsBinding binding) {
        this.context = fragment.getContext();
        this.fragment = fragment;
        this.binding = binding;
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        //TODO: Select Day
        mListSelectDay = new ArrayList<>();
        binding.rvSelectDay.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapterSelectDay = new ItemSelectDayAdapter(context, mListSelectDay, this);
        binding.rvSelectDay.setAdapter(adapterSelectDay);

        //TODO: Result Productivity
        mList = new ArrayList<>();
        adapter = new ItemWeekStatisticsAdapter(context, mList, this);
        binding.rvDayStatistics.setLayoutManager(new LinearLayoutManager(context));
        binding.rvDayStatistics.setAdapter(adapter);
    }

    public void createListDay(){
        Productivity productivity = ((ProductivityStatisticsActivity) fragment.getActivity()).getCurrentProductivity();
        if(productivity != null){
            try {
                mListSelectDay.clear();
                String sFromDay = Commons.timeStampToDate(productivity.getFromDate());
                String sToDay = Commons.timeStampToDate(productivity.getToDate());
                String[] lstFromDay = sFromDay.split("/");
                int fromDay = Integer.parseInt(lstFromDay[0]);
                int toDay = Integer.parseInt(sToDay.split("/")[0]);
                int month = Integer.parseInt(lstFromDay[1]);
                int year = Integer.parseInt(lstFromDay[2]);

                for (int i = fromDay; i <= toDay; i++){
                    mListSelectDay.add(
                            new ItemSelectDay(
                                    false,
                                    Commons.dayName(i, month, year),
                                    i+"",
                                    month,
                                    year));
                }

                Calendar calendar = Calendar.getInstance();
                int thisMonth = calendar.get(Calendar.MONTH)+1;
                if(month == thisMonth){
                    mListSelectDay.get(mListSelectDay.size()-1).setSelected(true);
                    binding.rvSelectDay.scrollToPosition(mListSelectDay.size()-1);
                    callGetProductivityAPI(mListSelectDay.get(mListSelectDay.size()-1));
                }
                else {
                    mListSelectDay.get(0).setSelected(true);
                    binding.rvSelectDay.scrollToPosition(0);
                    callGetProductivityAPI(mListSelectDay.get(0));
                }
                adapterSelectDay.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void callGetProductivityAPI(ItemSelectDay item){

        binding.rvDayStatistics.setVisibility(View.GONE);
        binding.pbLoadingDate.setVisibility(View.VISIBLE);

        GetProductivityInput data = new GetProductivityInput(context);

        Calendar calendar = Calendar.getInstance();
        calendar.set(item.getYear(), item.getMonth()-1, item.getDate(), 0, 0, 0);
        Log.e("Productivity", item.getDate() + "/" + item.getMonth() + "/" + item.getYear());
        data.setFromDate(calendar.getTimeInMillis()/1000 * 1000);
        calendar.set(item.getYear(), item.getMonth()-1, item.getDate(), 24, 0, 0);
        data.setToDate(calendar.getTimeInMillis()/1000 *1000);

        new GetProductivityAPI(context, data, this).execute();
    }

    /**
     * Sau khi gọi API getProductivity
     * @param result
     */
    @Override
    public void onSuccess(Object result) {
        if(result != null){
            mList.clear();
            ArrayList<Productivity> lst = (ArrayList<Productivity>) result;
            mList.addAll(lst);
            adapter.notifyDataSetChanged();
        }
        binding.rvDayStatistics.setVisibility(View.VISIBLE);
        binding.pbLoadingDate.setVisibility(View.GONE);
    }

    @Override
    public void onFailed() {
        binding.rvDayStatistics.setVisibility(View.VISIBLE);
        binding.pbLoadingDate.setVisibility(View.GONE);
    }

    /**
     * Gọi khi click vào item day
     * @param action
     */
    @Override
    public void onSuccess(int action) {
        try {
            ItemSelectDay item = mListSelectDay.get(action);
            if (item != null){
                callGetProductivityAPI(item);
            }
        } catch (Exception e) {
        }
    }

    @BindingAdapter("setMenu")
    public static void setBackground(View v, boolean b){
        if(b){
            v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBlackGray));
        }
        else {
            v.setBackgroundColor(ContextCompat.getColor(v.getContext(), android.R.color.transparent));
        }
    }

    //region TODO: On Click_____________________

    //endregion TODO: On Click__________________End/
}
