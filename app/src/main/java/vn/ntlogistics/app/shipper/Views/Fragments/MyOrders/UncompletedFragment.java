package vn.ntlogistics.app.shipper.Views.Fragments.MyOrders;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.Location.GetCurrentLocation;
import vn.ntlogistics.app.shipper.Models.Outputs.Location.Lonlat;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.Others.Item;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.MyOrderVMs.BaseMyOrderViewModel;
import vn.ntlogistics.app.shipper.ViewModels.MyOrderVMs.UncompletedViewModel;
import vn.ntlogistics.app.shipper.Views.Adapters.RecyclerViewAdapter;
import vn.ntlogistics.app.shipper.databinding.FragmentUncompletedBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class UncompletedFragment extends BaseFragment {
    public static final String          TAG = "UncompletedFragment";

    private FragmentUncompletedBinding binding;
    private UncompletedViewModel viewModel;
    private View                        view;

    Spinner s1, s2;
    String[] ss1, ss2;
    List<Item> mListItem;
    //TODO: list lưu toàn bộ đơn hàng tạm
    List<Item> mListMain;
    List<Item> mListMainSup;
    //Controls
    LinearLayout btnUpdate;
    CheckBox cbAll;

    //TODO: load my order
    int pageNumber = 0;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerViewAdapter adpater;

    LinearLayoutManager linearLayoutManager;
    boolean loading = false;
    boolean reload = true;

    //MyOrderFragment myOrderFragment;

    //TODO: API
    List<String> mListShippingCode, mListOrderKCode;

    //TODO: Progress Dialog
    View lnDialog;

    //TODO: lấy vị trí hiện tại
    GetCurrentLocation getCurrentLocation;
    Lonlat position;

    //TODO: Sqlite
    //SqliteManager db;

    public UncompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_uncompleted, container, false);
        viewModel = new UncompletedViewModel(
                getActivity(), this,
                binding.spinner, binding.spinner1, binding.rv, binding.swipeRefresh,
                Constants.STATUS_UNCOMPLETED
        );
        binding.setViewModel(viewModel);
        view = binding.getRoot();
        lnDialog = view.findViewById(R.id.lnDialog);
        //db = new SqliteManager(getContext());
        //init(view);
        //lnDialog.setVisibility(View.GONE);
        /**
         * Tạo instance sau khi khởi tạo để lấy được mListItem
         * giá trị của mListItem khi get data từ server về.
         * Nếu để trước init() thì mListItem sẽ rỗng.
         * */
        /*if (instance == null || instance != UncompletedFragment.this)
            instance = UncompletedFragment.this;*/
        /*new BaseAsystask(){

            @Override
            public void onPre() {
                lnDialog.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBG() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        init(view);
                    }
                });
            }

            @Override
            public void onPost() {
                lnDialog.setVisibility(View.GONE);
                *//**
                 * Tạo instance sau khi khởi tạo để lấy được mListItem
                 * giá trị của mListItem khi get data từ server về.
                 * Nếu để trước init() thì mListItem sẽ rỗng.
                 * *//*
                if (instance == null || instance != UncompletedFragment.this)
                    instance = UncompletedFragment.this;
            }
        }.execute();*/
        /*init(view);
        *//**
         * Tạo instance sau khi khởi tạo để lấy được mListItem
         * giá trị của mListItem khi get data từ server về.
         * Nếu để trước init() thì mListItem sẽ rỗng.
         * *//*
        if (instance == null)
            instance = this;*/
        return view;
    }

    @Override
    public BaseMyOrderViewModel getViewModelOrder() {
        return viewModel;
    }

    /*void init(View view) {
        mListOrderKCode = new ArrayList<>();
        mListShippingCode = new ArrayList<>();
        //myOrderFragment = MyOrderFragment.getInstanse();
        ss1 = new String[]{getResources().getString(R.string.sp_neworder), getResources().getString(R.string.sp_nearly)};
        List<Job> mListJob = new SJob().createListJobMain(getContext());
        ss2 = new String[mListJob.size()+1];
        ss2[0] = getString(R.string.totle_work);
        for (int i =0; i < mListJob.size(); i++){
            ss2[i+1] = mListJob.get(i).getName();
        }

        //TODO: Lấy vị trí hiện tại
        try {
            getCurrentLocation = new GetCurrentLocation(getContext());
            position = new LatLng(getCurrentLocation.getLatitude(),getCurrentLocation.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        s1 = (Spinner) view.findViewById(R.id.spinner);
        s2 = (Spinner) view.findViewById(R.id.spinner1);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        cbAll = (CheckBox) view.findViewById(R.id.cbAll1);

        //initButton(view);
        setupSpinner();
        setupSwipeRefresh();
        setupRecyclerView();

    }*/

    /*void initButton(View view) {
        btnUpdate = (LinearLayout) view.findViewById(R.id.btnUpdate);
        btnUpdate.setVisibility(View.GONE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setEnabledButton(btnUpdate);
                final CustomDialog dialog = new CustomDialog(getActivity());
                dialog.setTextTitle(getResources().getString(R.string.update_now));
                dialog.setTitleMessage(getResources().getString(R.string.message_dialog_update_now));
                dialog.setShow(true);
                dialog.setTextButton(getResources().getString(R.string.yes), getResources().getString(R.string.no));
                dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                    @Override
                    public void onClickOk() {
                        callUpdateStatusOrderAPI();
                        MyAnimation.setVisibilityAnimationDown(btnUpdate, false);
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                //getListItemChecked(); // xu ly 1 list item checked
            }
        });
    }*/

    /*public void checkAll(boolean check) {
        try {
            if (check) {
                for (int i = 0; i < mListItem.size(); i++)
                    mListItem.get(i).setCheck(true);
                adpater.notifyDataSetChanged();
                if (!checkJobTypeInList()) {
                    if (btnUpdate.getVisibility() != View.VISIBLE) {
                        MyAnimation.setVisibilityAnimationDown(btnUpdate, true);
                    }
                }
                else {
                    Message.showToast(getContext(),getResources().getString(R.string.toast_compare_job));
                    if (btnUpdate.getVisibility() == View.VISIBLE) {
                        MyAnimation.setVisibilityAnimationDown(btnUpdate, false);
                    }
                }
            } else {
                for (int i = 0; i < mListItem.size(); i++)
                    mListItem.get(i).setCheck(false);
                adpater.notifyDataSetChanged();
                if (btnUpdate.getVisibility() == View.VISIBLE) {
                    MyAnimation.setVisibilityAnimationDown(btnUpdate, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void showControls(boolean check, int action) {
        viewModel.showControls(check, action);
    }

    /*private int getJobTypeInList(){
        int job = 0;
        List<Order> mListOrderChecked = new ArrayList<>();
        //TODO: Get list item checked
        for (int i = 0; i < mListItem.size(); i++) {
            if (mListItem.get(i).isCheck()) {
                mListOrderChecked.add(mListItem.get(i).getOrder());
                break;
            }
        }
        if(mListOrderChecked.size() > 0)
            job = mListOrderChecked.get(0).getJobType();
        return job;
    }*/

    /*private boolean checkJobTypeInList() {
        List<Order> mListOrderChecked = new ArrayList<>();
        //TODO: Get list item checked
        for (int i = 0; i < mListItem.size(); i++) {
            if (mListItem.get(i).isCheck())
                mListOrderChecked.add(mListItem.get(i).getOrder());
        }
        //TODO: Kiểm tra xem trong list đã checked có trùng jobtype không
        int job = mListOrderChecked.get(0).getJobType();
        for (int i = 0; i < mListOrderChecked.size(); i++) {
            if (job != mListOrderChecked.get(i).getJobType()) {
                return true;
            }
        }
        return false;
    }*/

    /*private List<String> checkedFormList() {
        List<String> mData = new ArrayList<>();
        List<String> mListBLCode = new ArrayList<>();
        mListOrderKCode.clear();
        mListShippingCode.clear();
        for (int i = 0; i < mListItem.size(); i++) {
            if (mListItem.get(i).isCheck()) {
                mListOrderKCode.add(mListItem.get(i).getOrder().getOrderKCode());
                mListShippingCode.add(mListItem.get(i).getOrder().getShippingCode());
                mListBLCode = mListItem.get(i).getOrder().getLstBLCode();
                for (int j = 0; j < mListBLCode.size(); j++)
                    mData.add(new String(mListBLCode.get(j)));
            }
        }
        return mData;
    }*/


    /*private void removeItemList() {
        List<Item> mListItemDeleted = new ArrayList<>();
        for (int i = 0; i < mListItem.size(); i++) {
            if (mListItem.get(i).isCheck()) {
                mListItem.get(i).setCheck(false);
                mListItemDeleted.add(mListItem.get(i));
                mListMain.remove(mListItem.get(i));
                mListMainSup.remove(mListItem.get(i));
            }
        }
        //CompletedFragment.getInstanse().updateListAll(mListItemDeleted);
        ((CompletedFragment)((MainActivity)getActivity()).getChildFragment(1,2)).updateListAll(mListItemDeleted);
        setListItemShow(s2.getSelectedItemPosition());
        cbAll.setChecked(false);
        updateBadgeView();
    }*/

    /*private void callUpdateStatusOrderAPI() {
        JSUpdateStatus data = new JSUpdateStatus(getContext());
        data.setData(checkedFormList());
        int job = getJobTypeInList();
        int jobApi = SJob.getIdSuccessByJobType(job);
        data.setStatusId(jobApi+""); //8 là giao hàng thành công
        data.setShippingCode(mListShippingCode);
        if(job == 8){ //Update ShipK
            data.setJobType(0);
            data.setLstOrderKCode(mListOrderKCode);
            String json = new Gson().toJson(data);
            new UpdateOrderKStatusAPI(getContext(), json, null, jobApi, this).execute();
        }
        else {
            data.setJobType(job);
            String json = new Gson().toJson(data);
            new UpdateOrderStatusAPI(getContext(), json, this,jobApi,null).execute();
        }
        *//*String json = new Gson().toJson(data);
        if(job != 8) { //Update ShipK
            new UpdateOrderStatusAPI(getContext(), json, this, jobApi, null).execute();
        }
        else {
            Message.showToast(getContext(),getString(R.string.job_status_1));
        }*//*
    }*/

    @Override
    public void loadSuccess(final List<Order> mList) {
        viewModel.loadSuccess(mList);
    }

    @Override
    public void loadFailed() {
        super.loadFailed();
        viewModel.loadFailed();
    }

    public void updateList(Item item) {
        viewModel.updateList(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //viewModel.onResume();
        /*if(MyOrderFragment.flag == 4)
            viewModel.reloadOrder();*/
    }

    @Override
    public void reload() {
        try {
            viewModel.reloadOrder();
        } catch (Exception e) {
            Log.d(TAG, "reload");
            e.printStackTrace();
        }
    }

    @Override
    public void handleNotification(int action, Bundle b) {
        if(action == 0) {
            viewModel.reloadOrder();
        }
    }

    /**
     * Sau khi đơn hàng được update thì thành công sẽ chạy vào hàm này.
     * Nó sẽ chuyển Đơn Hàng sang Đã hoàn thành khi các BL trong đơn hàng
     * thỏa điều kiện thành công.
     */
    /*public void updateOrderFollowDetail() {
        if (SC020102Activity.idItem > -1) {
            Item item = mListItem.get(SC020102Activity.idItem);
            mListMain.remove(item);
            mListMainSup.remove(item);
            setListItemShow(s2.getSelectedItemPosition());
            SC020102Activity.idItem = -1;
            updateBadgeView();
            item.setCheck(false);
            ((CompletedFragment)((MainActivity)getActivity()).getChildFragment(1,2)).updateList(item);
        }
        else {
            Item item = mListItem.get(mListItem.size()-1);
            mListMain.remove(item);
            mListMainSup.remove(item);
            setListItemShow(s2.getSelectedItemPosition());
            updateBadgeView();
            item.setCheck(false);
            ((CompletedFragment)((MainActivity)getActivity()).getChildFragment(1,2)).updateList(item);
        }
    }*/

    /**
     * Hàm update BadgeView, sẽ được gọi khi update hoặc từ chối đơn hàng.
     */
    public void updateBadgeView() {
        viewModel.updateBadgeView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * Nếu không gọi hàm onDestroy set null thì khi signOut đăng nhập lại
         * thì biến instance sẽ lấy giá trị cũ.
         */
        //instance = null;
    }
}
