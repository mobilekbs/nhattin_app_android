package vn.ntlogistics.app.ordermanagement.Views.Fragments.MyOrders;


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

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.Location.GetCurrentLocation;
import vn.ntlogistics.app.ordermanagement.Commons.Location.Lonlat;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.BaseMyOrderViewModel;
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.UncompletedViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.RecyclerViewAdapter;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentUncompletedBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class UncompletedFragment extends BaseFragment {
    public static final String          TAG = "UncompletedFragment";

    private FragmentUncompletedBinding  binding;
    private UncompletedViewModel        viewModel;
    private View                        view;

    Spinner s1, s2;
    String[] ss1, ss2;
    List<Bill> mListBill;
    //TODO: list lưu toàn bộ đơn hàng tạm
    List<Bill> mListMain;
    List<Bill> mListMainSup;
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
    //SqlOrderanager db;

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
        //db = new SqlOrderanager(getContext());
        //init(view);
        //lnDialog.setVisibility(View.GONE);
        /**
         * Tạo instance sau khi khởi tạo để lấy được mListOrder
         * giá trị của mListOrder khi get data từ server về.
         * Nếu để trước init() thì mListOrder sẽ rỗng.
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
                 * Tạo instance sau khi khởi tạo để lấy được mListOrder
                 * giá trị của mListOrder khi get data từ server về.
                 * Nếu để trước init() thì mListOrder sẽ rỗng.
                 * *//*
                if (instance == null || instance != UncompletedFragment.this)
                    instance = UncompletedFragment.this;
            }
        }.execute();*/
        /*init(view);
        *//**
         * Tạo instance sau khi khởi tạo để lấy được mListOrder
         * giá trị của mListOrder khi get data từ server về.
         * Nếu để trước init() thì mListOrder sẽ rỗng.
         * *//*
        if (instance == null)
            instance = this;*/
        return view;
    }

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
                //getListOrderChecked(); // xu ly 1 list Order checked
            }
        });
    }*/

    /*public void checkAll(boolean check) {
        try {
            if (check) {
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(true);
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
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(false);
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
        //TODO: Get list Order checked
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                mListOrderChecked.add(mListOrder.get(i).getOrder());
                break;
            }
        }
        if(mListOrderChecked.size() > 0)
            job = mListOrderChecked.get(0).getJobType();
        return job;
    }*/

    /*private boolean checkJobTypeInList() {
        List<Order> mListOrderChecked = new ArrayList<>();
        //TODO: Get list Order checked
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck())
                mListOrderChecked.add(mListOrder.get(i).getOrder());
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
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                mListOrderKCode.add(mListOrder.get(i).getOrder().getOrderKCode());
                mListShippingCode.add(mListOrder.get(i).getOrder().getShippingCode());
                mListBLCode = mListOrder.get(i).getOrder().getLstBLCode();
                for (int j = 0; j < mListBLCode.size(); j++)
                    mData.add(new String(mListBLCode.get(j)));
            }
        }
        return mData;
    }*/


    /*private void removeOrderList() {
        List<Order> mListOrderDeleted = new ArrayList<>();
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                mListOrder.get(i).setCheck(false);
                mListOrderDeleted.add(mListOrder.get(i));
                mListMain.remove(mListOrder.get(i));
                mListMainSup.remove(mListOrder.get(i));
            }
        }
        //CompletedFragment.getInstanse().updateListAll(mListOrderDeleted);
        ((CompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,2)).updateListAll(mListOrderDeleted);
        setListOrderShow(s2.getSelectedOrderPosition());
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
    public void loadSuccess(final List<Bill> mList) {
        viewModel.loadSuccess(mList);
    }

    @Override
    public void loadFailed() {
        super.loadFailed();
        //viewModel.loadFailed();
    }

    public void updateList(Bill Bill) {
        viewModel.updateList(Bill);
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
        if (SC020102Activity.idOrder > -1) {
            Order Order = mListOrder.get(SC020102Activity.idOrder);
            mListMain.remove(Order);
            mListMainSup.remove(Order);
            setListOrderShow(s2.getSelectedOrderPosition());
            SC020102Activity.idOrder = -1;
            updateBadgeView();
            Order.setCheck(false);
            ((CompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,2)).updateList(Order);
        }
        else {
            Order Order = mListOrder.get(mListOrder.size()-1);
            mListMain.remove(Order);
            mListMainSup.remove(Order);
            setListOrderShow(s2.getSelectedOrderPosition());
            updateBadgeView();
            Order.setCheck(false);
            ((CompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,2)).updateList(Order);
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
