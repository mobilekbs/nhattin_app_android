package vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.SharedPreference.MySharedReference;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.Firebase.GetShortLinkFbs;
import vn.ntlogistics.app.shipper.Models.Outputs.FirebaseOutput.ShortLinkFbsOutput;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReferralCodeFragment extends BaseFragment {
    View view;
    //TODO: Init controls
    View btnSendReferral, btnSendApp;
    TextView tvReferralCode;

    private SharedPreferences mShared;

    public ReferralCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_referral_code, container, false);

        init();

        return view;
    }

    private void init() {
        mShared = new MySharedReference
                .build()
                .init(getContext(), Constants.SR_FIREBASE)
                .get();

        callGetShortLink();

        btnSendReferral = view.findViewById(R.id.btnSendReferral);
        btnSendApp = view.findViewById(R.id.btnSendApp);

        tvReferralCode = (TextView) view.findViewById(R.id.tvReferralCode);

        tvReferralCode.setText(SCurrentUser.getCurrentUser(getContext()).getPhoneNo());

        btnSendReferral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedText();
            }
        });
        btnSendApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedUrl();
            }
        });
    }

    private void sharedUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_app));
        share.putExtra(Intent.EXTRA_TEXT, getDynamicLink());

        startActivity(Intent.createChooser(share, getResources().getString(R.string.send_app)));
    }

    private void sharedText() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        //share.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_referral_code));
        share.putExtra(Intent.EXTRA_TEXT, getShortLink());

        startActivity(Intent.createChooser(share, getResources().getString(R.string.send_referral_code)));
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Order> mList) {

    }

    @Override
    public void loadSuccess(Object object) {
        try {
            if(object != null){
                ShortLinkFbsOutput output = (ShortLinkFbsOutput) object;
                setShortLink(output.getShortLink());
            }
        } catch (Exception e) {
        }
    }

    private void callGetShortLink(){
        new GetShortLinkFbs(
                getContext(),
                SCurrentUser.getCurrentUser(getContext()).getPhoneNo(),
                this
        ).execute();
    }

    private void setShortLink(String shortLink){
        new MySharedReference
                .build()
                .init(getContext(), Constants.SR_FIREBASE)
                .putString(Constants.SR_SHORT_LINK, shortLink)
                .save();
    }

    private String getShortLink(){
        return mShared.getString(Constants.SR_SHORT_LINK, getDynamicLink());
    }

    private String getDynamicLink(){
        return Constants.DYNAMIC_LINK + SCurrentUser.getCurrentUser(getContext()).getPhoneNo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setShortLink(null);
    }
}
