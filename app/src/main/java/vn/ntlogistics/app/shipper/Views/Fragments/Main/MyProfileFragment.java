package vn.ntlogistics.app.shipper.Views.Fragments.Main;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.GetMyProfileAPI;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.User.MyProfile;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.MyProfileVMs.MyProfileFragmentViewModel;
import vn.ntlogistics.app.shipper.databinding.FragmentMyProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Hồ sơ của tôi
 */
public class MyProfileFragment extends BaseFragment {
    public static final String  TAG = "MyProfileFragment";

    FragmentMyProfileBinding binding;
    MyProfileFragmentViewModel viewModel;

    View                        view;

    //ImageView ivAvatar;

    /*TextView tvName, tvAddress, tvPhone, tvMail, tvAge, tvSex;
    TextView tvCompleted, tvTrust, tvUntrust, tvVehicle, tvPlateNo;
    ImageView img11, img12, img21, img22, img31, img32, img41, img42; //ivEditAvatar;
    View btnChangeInfo, btnUpdate;*/
    //ImageView temp; --> Xong

    /*String pathImage = "";

    GetPathFromURI getPathFromURI;

    /*
     * Biến check có thay đổi thông tin hay không
     */
    public static boolean check = false;

    /**
     * Lưu 1 bản cũ myProfile lại để khi hoàn tác trả lại giá trị cho người dùng
     * myProfile sẽ được sử dụng cho bên edit thông tin nên sử dụng static,
     * để khỏi chuyền biến khi chuyển activity.
    public MyProfile myProfileMain;
    public MyProfile myProfile; --> Xong*/

    /**
     * Biến để nhận biết được là edit image avatar
     * để làm hình tròn.
    int iAvatar = 0; --> Xong*/

    public MyProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile,container,false);
        viewModel = new MyProfileFragmentViewModel(this);
        binding.setViewModel(viewModel);
        //final View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        view = binding.getRoot();
        new GetMyProfileAPI(getActivity(), this, false).execute();
        return view;
    }

    public FragmentMyProfileBinding getBinding(){
        return binding;
    }

    public MyProfileFragmentViewModel getViewModel(){
        return viewModel;
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Order> mList) {
        /*if (mList == null) {
            try {
                myProfileMain = (MyProfile) myProfile.clone();
                ((MainActivity)getActivity()).setHeaderMenu(myProfileMain);
                check = false;
                Message.showToast(getContext(),getContext().getResources().getString(R.string.update_myprofile_success));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * Gọi từ GetMyProfileAPI
     * @param myProfile
     */
    public void loadMyProfile(final MyProfile myProfile) {
        viewModel.loadMyProfile(myProfile);
    }

    /**
     * Gọi từ MainActivity
     * @param requestCode
     * @param data
     */
    public void getResultFragment(int requestCode, Intent data) {
        viewModel.handleBeforeChooseImage(requestCode, data);
    }

    /**
     * Gọi từ MainActivity
     * @param myProfile
     */
    public void changeInfoUser(MyProfile myProfile){
        viewModel.changeInfoUser(myProfile);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void changeFragment() {
        try {
            new GetMyProfileAPI(getContext(), this, false).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(viewModel != null)
            viewModel.destroy();
    }
}
