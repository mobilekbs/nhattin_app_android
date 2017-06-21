package vn.ntlogistics.app.ordermanagement.Views.Fragments.OrderDetail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.HeaderLayout;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.ReceiverInfoLayout;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BLDetail;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BillDetail;
import vn.ntlogistics.app.ordermanagement.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderInfoFragment extends BaseFragment {

    public static OrderInfoFragment instance;

    //TODO: show mapview in fragment
//    private static CustomMapView mMapView;
//    public GoogleMap mGoogleMap;

    View view;
    int statusOrder = 0;
    ScrollView sv;
    //TODO: API get order detail
    String shippingCode;
    int jobtype;
    //TODO: Xử lý vẽ đường đi
    //HandleConnect handleConnect;
    //TODO: Chứa danh sách latlng
    //List<LatLng> mListLatLng;
    //TODO: View map
    //View lnViewMap;

    LinearLayout lnReveicerInfo;

    //TODO: Biến set cho gọi API khi đã chạy xong
    boolean senderAPI = true;

    /**
     * Biến chứa thông tin orderDetail tạm
     * Biến BLDetail chứa thông tin chi tiết
     */
    BillDetail detail;
    Bill bill;
    List<BLDetail> mListBLDetail;
    //TODO: Tuyệt chiêu cuối, add layout người nhận để quản lý button
    List<ReceiverInfoLayout> listButton;

    /**
     * Khai báo giao diện
     */
    //TODO: Load init
    //ProgressBar progressBar;
    //TODO: header
    TextView tvFeeShip, tvFeeCod, tvShippingFeeU, tvTitleShipFee;
    View lnShipU, lnShippingOrder;
    //TODO: Thông tin người gửi
    TextView tvTSend, tvUSend;
    View btnCallSend, lnNote, lnTime, lnCODSend;
    HeaderLayout headerSend;
    TextView tvNameSend, tvTimeSend, tvNoteSend, tvAddressSend,
            tvPhoneSend, tvCODSend;
    //TODO: Thông tin người phụ trách
    TextView tvNameCurator, tvPhoneCurator;
    View btnCallCurator, lnCuratorInfo;
    //TODO: Thông tin người vận chuyển
    //TextView tvTShip, tvUShip;
    /*View lnNameShip, lnAddressShip, lnPhoneShip, lnCuratorShip, lnEmailShip; //lnShipper
    HeaderLayout headerShip;
    TextView tvNameShip, tvAddressShip, tvCuratorShip, tvPhoneShip, tvEmailShip;*/

    public OrderInfoFragment() {
        // Required empty public constructor
        if (instance != null || instance != this)
            instance = this;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_info, container, false);
        sv = (ScrollView) view.findViewById(R.id.sv);
        //progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        final BaseAsystask loadInit = new BaseAsystask(){

            @Override
            public void onPre() {
                //progressBar.setVisibility(View.VISIBLE);
                //sv.setVisibility(View.GONE);
            }

            @Override
            public void doInBG() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        init(view);
                    }
                });
                //setUpMapIfNeeded(view, savedInstanceState);
            }

            @Override
            public void onPost() {
                //progressBar.setVisibility(View.GONE);
                sv.setVisibility(View.VISIBLE);
                /*getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setUpMapIfNeeded(view, savedInstanceState);
                    }
                });*/
            }
        };
        loadInit.execute();

        return view;
    }

    private void init(View v) {
        listButton = new ArrayList<>();

        lnReveicerInfo = (LinearLayout) v.findViewById(R.id.lnReveicerInfo);
        //lnViewMap = v.findViewById(R.id.lnViewMap);
        //sv = (ScrollView) v.findViewById(R.id.sv);

        //TODO: header
        tvFeeShip = (TextView) v.findViewById(R.id.tvFeeShip);
        tvFeeCod = (TextView) v.findViewById(R.id.tvFeeCod);
        tvShippingFeeU = (TextView) v.findViewById(R.id.tvShippingFeeU);
        tvTitleShipFee = (TextView) v.findViewById(R.id.tvTitleShipFee);
        lnShipU = v.findViewById(R.id.lnShipU);
        lnShippingOrder = v.findViewById(R.id.lnShippingOrder);

        //TODO: Thông tin người gửi
        headerSend = (HeaderLayout) v.findViewById(R.id.headerSend);
        tvNameSend = (TextView) v.findViewById(R.id.tvNameSend);
        tvAddressSend = (TextView) v.findViewById(R.id.tvAddressSend);
        tvPhoneSend = (TextView) v.findViewById(R.id.tvPhoneSend);
        //tvSendSend = (TextView) v.findViewById(R.id.tvSendSend);
        //tvTrustSend = (TextView) v.findViewById(R.id.tvTrustSend);
        //tvUntrustSend = (TextView) v.findViewById(R.id.tvUntrustSend);
        tvNoteSend = (TextView) v.findViewById(R.id.tvNoteSend);
        tvTimeSend = (TextView) v.findViewById(R.id.tvTimeSend);
        tvCODSend = (TextView) v.findViewById(R.id.tvCodSend);
        btnCallSend = v.findViewById(R.id.btnCall);
        lnCODSend = v.findViewById(R.id.lnCodSend);
        lnNote = v.findViewById(R.id.lnNote);
        //lnTrustUntrustSend = v.findViewById(R.id.lnTrustUntrustSend);
        //btnTrustSend = v.findViewById(R.id.btnTrustSend);
        //btnUntrustSend = v.findViewById(R.id.btnUnTrustSend);
        lnTime = v.findViewById(R.id.lnTimeSend);
        //Dùng để nhận biết đã trust hay untrust chưa
        //tvTSend = (TextView) v.findViewById(R.id.tvTSend);
        //tvUSend = (TextView) v.findViewById(R.id.tvUSend);

        //TODO: Thông tin người phụ trách
        tvNameCurator = (TextView) v.findViewById(R.id.tvNameCurator);
        tvPhoneCurator = (TextView) v.findViewById(R.id.tvPhoneCurator);
        btnCallCurator = v.findViewById(R.id.btnCallCurator);
        lnCuratorInfo = v.findViewById(R.id.lnCuratorInfo);

        //TODO: Thông tin người vận chuyển
        //lnShipper = v.findViewById(R.id.lnShipper);
        /*headerShip = (HeaderLayout) v.findViewById(R.id.headerShip);
        tvNameShip = (TextView) v.findViewById(R.id.tvNameShip);
        tvAddressShip = (TextView) v.findViewById(R.id.tvAddressShip);
        tvCuratorShip = (TextView) v.findViewById(R.id.tvCuratorShip);
        tvPhoneShip = (TextView) v.findViewById(R.id.tvPhoneShip);
        tvEmailShip = (TextView) v.findViewById(R.id.tvEmailShip);
        lnNameShip = v.findViewById(R.id.lnNameShip);
        lnAddressShip = v.findViewById(R.id.lnAddressShip);
        lnEmailShip = v.findViewById(R.id.lnEmailShip);
        lnPhoneShip = v.findViewById(R.id.lnPhoneShip);
        lnCuratorShip = v.findViewById(R.id.lnCuratorShip);*/

        //detail = SOrderDetail.getOurInstance();
        try {
            detail = (BillDetail) getActivity().getIntent().getExtras().getSerializable("orderDetail");
        } catch (Exception e) {
        }
        try {
            bill = (Bill) getActivity().getIntent().getExtras().getSerializable("order");
        } catch (Exception e) {
        }
        if (detail != null)
            mListBLDetail = detail.getData();
        try {
            statusOrder = Integer.parseInt(
                    getActivity().getIntent().getExtras().get("statusOrder").toString()
            );
        } catch (Exception e) {
        }
        try {
            setupControls();
        } catch (Exception e) {
        }
        /**
         * Nam đỉnh vãi....!
         */
        //Xử lý button Trust/Untrust
       // handleButtonTrustUntrust();

    }

    /*private void handleButtonTrustUntrust() {
        //TODO: Thông tin người gửi

        if (detail.getSenderTrusted() != null) {
            int trusted = Integer.parseInt(detail.getSenderTrusted());
            if (trusted == 0) { //Untrusted
                tvUSend.setText(getResources().getString(R.string.Untrusted));
                btnTrustSend.setEnabled(false);
                btnTrustSend.setBackgroundColor(getResources().getColor(R.color.colorGrayGray));

            } else { //Trusted
                tvTSend.setText(getResources().getString(R.string.trusted));
                btnUntrustSend.setEnabled(false);
                btnUntrustSend.setBackgroundColor(getResources().getColor(R.color.colorGrayGray));
            }
        }

        btnTrustSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (senderAPI) {
                    senderAPI = false;
                    String s = tvTSend.getText().toString();
                    *//**
                     * So sánh, nếu tvTSend là chữ TRUST thì sẽ chuyển thành TRUSTED và ngược lại
                     *//*
                    if (s.equalsIgnoreCase(getResources().getString(R.string.trust))) {
                        List<String> mList = new ArrayList<String>();
                        mList.add(mListBLDetail.get(0).getSenderID());
                        callRatingAPI(mList, 1, 0, 1, mListBLDetail.get(0).getBlCode()); //1 là trust | 0 là insert
                    } else {
                        List<String> mList = new ArrayList<String>();
                        mList.add(mListBLDetail.get(0).getSenderID());
                        callRatingAPI(mList, 1, 1, 1, mListBLDetail.get(0).getBlCode()); //1 là trust | 1 là delete
                    }
                }
            }
        });
        btnUntrustSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (senderAPI) {
                    senderAPI = false;
                    String s = tvUSend.getText().toString();
                    *//**
                     * So sánh, nếu tvTSend là chữ TRUST thì sẽ chuyển thành TRUSTED và ngược lại
                     *//*
                    if (s.equalsIgnoreCase(getResources().getString(R.string.untrust))) {
                        List<String> mList = new ArrayList<String>();
                        mList.add(mListBLDetail.get(0).getSenderID());
                        callRatingAPI(mList, 0, 0, 1, mListBLDetail.get(0).getBlCode()); //1 là trust | 0 là insert
                    } else {
                        List<String> mList = new ArrayList<String>();
                        mList.add(mListBLDetail.get(0).getSenderID());
                        callRatingAPI(mList, 0, 1, 1, mListBLDetail.get(0).getBlCode()); //1 là trust | 1 là delete
                    }
                }
            }
        });
    }*/

    private void setupControls() {

        /*if (statusOrder != Constants.STATUS_COMPLETED) {
            lnTrustUntrustSend.setVisibility(View.GONE);
            //lnTrustUntrustShip.setVisibility(View.GONE);
        } else {
            lnTrustUntrustSend.setVisibility(View.VISIBLE);
            //lnTrustUntrustShip.setVisibility(View.VISIBLE);
        }*/


        try {
            shippingCode = bill.getBillID();
            jobtype = bill.getJobType();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BLDetail item = null;
        try {
            item = mListBLDetail.get(0);
        } catch (Exception e) {
            item = new BLDetail();
        }

        //TODO: Header
        tvTitleShipFee.setText(bill.getService());
        tvFeeCod.setText(Commons.DinhDangChuoiTien(detail.getTotalCodAmount()) + "đ");
        tvFeeShip.setText(Commons.DinhDangChuoiTien(detail.getTotalFee()) + "đ");

        //Thông tin người gửi
        headerSend.setConfirmCode(item.getSenderCode());
        headerSend.setupHeaderStopPlace(0,R.mipmap.ic_from);
        headerSend.setTitle(getResources().getString(R.string.point_start));
        tvNameSend.setText(item.getSenderName());
        tvAddressSend.setText(item.getSenderAddress());
        tvPhoneSend.setText(item.getSenderPhone());
        //tvTrustSend.setText(item.getSenderTrust());
        //tvUntrustSend.setText(item.getSenderUntrust());

        try {
            double FreightFeeReciver = 0, AddedServiceFee = 0, ColectingRevenue = 0, CODFee = 0, VATSend = 0,ExtraFeeSend = 0;
            for (int i = 0; i < mListBLDetail.size(); i++) {
                BLDetail blDetail = mListBLDetail.get(i);

                //TODO: Sum phí người gửi
                FreightFeeReciver += blDetail.getSenderShippingFee();
                AddedServiceFee += blDetail.getSenderVasFee();
                ColectingRevenue += blDetail.getSenderCodAmount();
                CODFee += blDetail.getSenderCodFee();
                VATSend += blDetail.getSenderVatAmount();
                ExtraFeeSend += blDetail.getSenderExtraFee();
            }
            double totalFeeSender = FreightFeeReciver + AddedServiceFee + ColectingRevenue + CODFee + VATSend + ExtraFeeSend;

            tvCODSend.setText(Commons.DinhDangChuoiTien(totalFeeSender)+getString(R.string.unit));
        } catch (Exception e) {
        }

        //Thông tin người phụ trách
        if(mListBLDetail.get(0).getOrderPicName() != null) {
            tvNameCurator.setText(mListBLDetail.get(0).getOrderPicName());
            tvPhoneCurator.setText(mListBLDetail.get(0).getOrderPicPhone());
            btnCallCurator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String uri = "tel:" + tvPhoneCurator.getText().toString().trim();
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse(uri));
                        getActivity().startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            lnCuratorInfo.setVisibility(View.GONE);
        }


        btnCallSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = "tel:" + tvPhoneSend.getText().toString().trim();
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse(uri));
                    getActivity().startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //TODO: View map
        /*lnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                *//*Bundle b = new Bundle();
                b.putSerializable("blDetail",(ArrayList<BLDetail>) detail.getData());
                Intent i = new Intent(getContext(), MapsActivity.class);
                i.putExtras(b);
                startActivity(i);*//*
            }
        });*/

        //Thông tin người vận chuyển
        /*if (statusOrder != Common.STATUS_NEW_ORDER) {

            tvCuratorShip.setText(SCurrentUser.getCurrentUser(getContext()).getFullName() + " (" + SCurrentUser.getCurrentUser(getContext()).getUserID() + ")");
            tvPhoneShip.setText(SCurrentUser.getCurrentUser(getContext()).getPhoneNo());
            if(SCurrentUser.getCurrentUser(getContext()).getEmail() != null)
                tvEmailShip.setText(SCurrentUser.getCurrentUser(getContext()).getEmail());
            else
                lnEmailShip.setVisibility(View.GONE);
            *//**
             * Nếu thông tin Tên và Địa chỉ công ty không có thì sẽ ẩn đi
             *//*
            if (mListBLDetail.get(0).getCompanyId() != null) {
                lnNameShip.setVisibility(View.VISIBLE);
                lnAddressShip.setVisibility(View.VISIBLE);
                headerShip.setConfirmCode(mListBLDetail.get(0).getDeliveryCode());
                tvNameShip.setText(mListBLDetail.get(0).getCompanyName()); // Người vận chuyển: công ty
                tvAddressShip.setText(mListBLDetail.get(0).getCompanyAddress()); // Địa chỉ người vận chuyển: công ty
            } else {
                // Không có company thì mã xác thực sẽ là của Shipper
                headerShip.setConfirmCode("");
                lnNameShip.setVisibility(View.GONE);
                lnAddressShip.setVisibility(View.GONE);
            }
        } else {
            lnShipper.setVisibility(View.GONE);
        }*/
        //Thông tin người gửi
        setupReceiverInfoLayout();
    }

    private void setupReceiverInfoLayout() {
        for (int i = 0; i < mListBLDetail.size(); i++) {
            final BLDetail item = mListBLDetail.get(i);
            final ReceiverInfoLayout reveicer = drawReceiver();
            //reveicer.setTime(item.getEtaDateTime());
            if(i == mListBLDetail.size() - 1) {
                reveicer.setupStopPlace(0, R.mipmap.ic_to);
                reveicer.setTitleHeader(getResources().getString(R.string.point_end));
            }
            else {
                reveicer.setupStopPlace(i+1, R.mipmap.ic_point);
                reveicer.setTitleHeader(getResources().getString(R.string.point_stop) +" "+ (i+1));
            }

            reveicer.setTvNote(item.getNote());
            reveicer.setTvCod(item.getTotalFeeReceiver()+"");
            //reveicer.setTvTime(item.getEtaDateTime());
            reveicer.setConfirmCode(item.getConsigneeCode());
            reveicer.setTvAddress(item.getConsigneeAddress());
            reveicer.setTvName(item.getConsigneeName());
            reveicer.setTvPhone(item.getConsigneePhone());

            //reveicer.setNumberTrust(item.getConsigneeTrust());
            //reveicer.setNumberUntrust(item.getConsigneeUntrust());


            /*if (statusOrder != Constants.STATUS_COMPLETED) {
                reveicer.setShowTrustUntrust(View.GONE);
            } else
                reveicer.setShowTrustUntrust(View.VISIBLE);*/
            reveicer.setOnClickCall(new ReceiverInfoLayout.SetOnClickReceiver() {
                @Override
                public void onClick(String value) {
                    try {
                        String uri = "tel:" + value.trim();
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse(uri));
                        getActivity().startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            reveicer.setTag(item.getConsigneeID());
            listButton.add(reveicer);
            lnReveicerInfo.addView(reveicer);
        }
    }

    private ReceiverInfoLayout drawReceiver() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ReceiverInfoLayout reveicer = new ReceiverInfoLayout(getContext());
        reveicer.setLayoutParams(params);
        return reveicer;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        /*try {
            threadLoadMap.interrupt();
            mMapView.onPause();
        } catch (Exception e) {
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
        /*try {
            threadLoadMap.interrupt();
            threadLoadMap = null;
            mMapView.onDestroy();
        } catch (Exception e) {
        }*/
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        /*try {
            mMapView.onLowMemory();
        } catch (Exception e) {
        }*/
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Bill> mList) {
    }
}
