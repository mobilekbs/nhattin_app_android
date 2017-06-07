package vn.ntlogistics.app.shipper.Views.Fragments.OrderDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.CustomViews.HeaderLayout;
import vn.ntlogistics.app.shipper.Commons.CustomViews.ReveicerFeeLayout;
import vn.ntlogistics.app.shipper.Commons.Singleton.SJob;
import vn.ntlogistics.app.shipper.Models.Enums.EServiceShip;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.BLDetail;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.OrderDetail;
import vn.ntlogistics.app.shipper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFeeFragment extends BaseFragment {
    View v;
    LinearLayout lnRevenueSender;

    /**
     * Khai báo giao diện
     */
    //Thu người gửi
    HeaderLayout headerSend;
    TextView tvFreightFeeReciver, tvAddedServiceFeeReciver, tvColectingRevenueReciver, tvCODFeeSend, tvVATSend, tvExtraFeeSend;

    View lnSenderFee;

    //Chi tiết cước
    HeaderLayout headerFee;
    TextView tv1, tv2, tv3, tv4, tvTotal, tvVAT, tvTotalFee;

    //TODO: Biến chứa thông tin
    OrderDetail detail;
    Order order;
    List<BLDetail> mListBLDetail;
    int jobType, blDetailHub = 0, position;

    boolean loadLayout = false;

    public ServiceFeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_service_fee, container, false);
        getBundle();
        return v;
    }

    private void getBundle() {
        try {
            detail = (OrderDetail) getActivity().getIntent().getExtras().getSerializable("orderDetail");
            order = (Order) getActivity().getIntent().getExtras().getSerializable("order");

            jobType = order.getJobType();
        } catch (Exception e) {
        }

        try {
            blDetailHub = getActivity().getIntent().getExtras().getInt("blDetailHub");
            position = getActivity().getIntent().getExtras().getInt("position",-1);
        } catch (Exception e) {
        }

        if(detail != null){
            mListBLDetail = detail.getData();
        }
        if(blDetailHub == 1) {
            changeFragment();
        }
    }

    private void init() {
        lnRevenueSender = (LinearLayout) v.findViewById(R.id.lnRevenueSender);
        lnSenderFee = v.findViewById(R.id.lnSenderFee);

        if(blDetailHub == 1) {
            lnSenderFee.setVisibility(View.GONE);
        }

        initControls();

    }

    private void initControls() {
        //TODO: Thu người gửi
        headerSend = (HeaderLayout) v.findViewById(R.id.headerSend);
        tvFreightFeeReciver = (TextView) v.findViewById(R.id.tvFreightFeeReciver);
        tvAddedServiceFeeReciver = (TextView) v.findViewById(R.id.tvAddedServiceFeeReciver);
        tvColectingRevenueReciver = (TextView) v.findViewById(R.id.tvColectingRevenueReciver);
        tvCODFeeSend = (TextView) v.findViewById(R.id.tvCODFeeSend);
        tvVATSend = (TextView) v.findViewById(R.id.tvVATSend);
        tvExtraFeeSend = (TextView) v.findViewById(R.id.tvExtraFeeSend);

        //TODO: Chi tiết cước
        headerFee = (HeaderLayout) v.findViewById(R.id.headerFee);
        tv1 = (TextView) v.findViewById(R.id.tv1);
        tv2 = (TextView) v.findViewById(R.id.tv2);
        tv3 = (TextView) v.findViewById(R.id.tv3);
        tv4 = (TextView) v.findViewById(R.id.tv4);
        tvTotal = (TextView) v.findViewById(R.id.tvTotal);
        tvVAT = (TextView) v.findViewById(R.id.tvVAT);
        tvTotalFee = (TextView) v.findViewById(R.id.tvTotalFee);

        setupControls();
    }

    private void setupControls() {
        //detail = SOrderDetail.getOurInstance();


        if (SJob.compareServiceShip(jobType) == EServiceShip.SHIP_U) {
            lnSenderFee.setVisibility(View.GONE);
        } else {
            //TODO: Khởi tạo Thu người nhận
            setupReveicerFeeLayout();
            /*if(position == 0)
                setupReveicerFeeLayout();
            else if(position == 1)
                setupReveicerFeeLayoutHub();*/
        }

        //TODO: Chi tiết cước
        headerFee.setConfirmCode(Commons.DinhDangChuoiTien(detail.getTotal())+"đ");
        tv1.setText(Commons.DinhDangChuoiTien(detail.getTotalShippingFee())+"đ");
        tv2.setText(Commons.DinhDangChuoiTien(detail.getTotalExtraFee())+"đ");
        tv3.setText(Commons.DinhDangChuoiTien(detail.getTotalVasFee())+"đ");
        tv4.setText(Commons.DinhDangChuoiTien(detail.getTotalCodFee())+"đ");
        tvTotal.setText(Commons.DinhDangChuoiTien(detail.getTotalFee())+"đ");
        tvVAT.setText(Commons.DinhDangChuoiTien(detail.getTotalVatAmount())+"đ");
        tvTotalFee.setText(Commons.DinhDangChuoiTien(detail.getTotalCodAmount())+"đ");
    }

    private void setupReveicerFeeLayout() {
        double FreightFeeReciver = 0, AddedServiceFee = 0, ColectingRevenue = 0, CODFee = 0, VATSend = 0,ExtraFeeSend = 0;
        if(blDetailHub == 0) {
            for (int i = 0; i < mListBLDetail.size(); i++) {
                BLDetail item = mListBLDetail.get(i);
                ReveicerFeeLayout reveicer = drawReveicer();
                reveicer.setAddress(mListBLDetail.get(i).getConsigneeAddress());
                reveicer.setFreightFee(Commons.DinhDangChuoiTien(item.getConsigneeShippingFee()) + "đ"); //Cước vận chuyển consigneeShippingFee
                reveicer.setAddedServiceFee(Commons.DinhDangChuoiTien(item.getConsigneeVasFee()) + "đ"); //Dịch vụ cộng thêm consigneeVasFee
                reveicer.setColectingRevenue(Commons.DinhDangChuoiTien(item.getConsigneeCodAmount()) + "đ"); //Tổng tiền thu hộ consigneeCodAmount
                reveicer.setVAT(Commons.DinhDangChuoiTien(item.getConsigneeVatAmount()) + "đ"); //Phí VAT consigneeVatAmount
                reveicer.setCOD(Commons.DinhDangChuoiTien(item.getConsigneeCodFee()) + "đ"); //Phí thu hộ
                reveicer.setExtraFee(Commons.DinhDangChuoiTien(item.getConsigneeExtraFee()) + "đ"); //Chi phí khác
                reveicer.setTotalFee(Commons.DinhDangChuoiTien(item.getTotalFeeReceiver()) + "đ"); //Tổng thu
                lnRevenueSender.addView(reveicer);

                //TODO: Sum phí người gửi
                FreightFeeReciver += item.getSenderShippingFee();
                AddedServiceFee += item.getSenderVasFee();
                ColectingRevenue += item.getSenderCodAmount();
                CODFee += item.getSenderCodFee();
                VATSend += item.getSenderVatAmount();
                ExtraFeeSend += item.getSenderExtraFee();
            }
        }
        else if(blDetailHub == 1){
            BLDetail item = mListBLDetail.get(position);
            ReveicerFeeLayout reveicer = drawReveicer();
            reveicer.setAddress(mListBLDetail.get(position).getConsigneeAddress());
            reveicer.setFreightFee(Commons.DinhDangChuoiTien(item.getConsigneeShippingFee()) + "đ"); //Cước vận chuyển consigneeShippingFee
            reveicer.setAddedServiceFee(Commons.DinhDangChuoiTien(item.getConsigneeVasFee()) + "đ"); //Dịch vụ cộng thêm consigneeVasFee
            reveicer.setColectingRevenue(Commons.DinhDangChuoiTien(item.getConsigneeCodAmount()) + "đ"); //Tổng tiền thu hộ consigneeCodAmount
            reveicer.setVAT(Commons.DinhDangChuoiTien(item.getConsigneeVatAmount()) + "đ"); //Phí VAT consigneeVatAmount
            reveicer.setCOD(Commons.DinhDangChuoiTien(item.getConsigneeCodFee()) + "đ"); //Phí thu hộ
            reveicer.setExtraFee(Commons.DinhDangChuoiTien(item.getConsigneeExtraFee()) + "đ"); //Chi phí khác
            reveicer.setTotalFee(Commons.DinhDangChuoiTien(item.getTotalFeeReceiver()) + "đ"); //Tổng thu
            lnRevenueSender.addView(reveicer);

            //TODO: Sum phí người gửi
            FreightFeeReciver += item.getSenderShippingFee();
            AddedServiceFee += item.getSenderVasFee();
            ColectingRevenue += item.getSenderCodAmount();
            CODFee += item.getSenderCodFee();
            VATSend += item.getSenderVatAmount();
            ExtraFeeSend += item.getSenderExtraFee();
        }

        double totalFeeSender = FreightFeeReciver + AddedServiceFee + ColectingRevenue + CODFee + VATSend + ExtraFeeSend;

        //TODO: Gán giá trị thu người gửi
        headerSend.setConfirmCode(Commons.DinhDangChuoiTien(totalFeeSender+"")+"đ");
        tvFreightFeeReciver.setText(Commons.DinhDangChuoiTien(FreightFeeReciver+"")+"đ");
        tvAddedServiceFeeReciver.setText(Commons.DinhDangChuoiTien(AddedServiceFee+"")+"đ");
        tvColectingRevenueReciver.setText(Commons.DinhDangChuoiTien(ColectingRevenue+"")+"đ");
        tvCODFeeSend.setText(Commons.DinhDangChuoiTien(CODFee+"")+"đ");
        tvVATSend.setText(Commons.DinhDangChuoiTien(VATSend+"")+"đ");
        tvExtraFeeSend.setText(Commons.DinhDangChuoiTien(ExtraFeeSend+"")+"đ");
    }

    //TODO: HUB________________________
    private void setupReveicerFeeLayoutHub() {
        double FreightFeeReciver = 0, AddedServiceFee = 0, ColectingRevenue = 0, CODFee = 0, VATSend = 0,ExtraFeeSend = 0;
        BLDetail item = mListBLDetail.get(position);
        ReveicerFeeLayout reveicer = drawReveicer();
        reveicer.setAddress(mListBLDetail.get(position).getConsigneeAddress());
        reveicer.setFreightFee(Commons.DinhDangChuoiTien(item.getConsigneeShippingFee())+"đ"); //Cước vận chuyển consigneeShippingFee
        reveicer.setAddedServiceFee(Commons.DinhDangChuoiTien(item.getConsigneeVasFee())+"đ"); //Dịch vụ cộng thêm consigneeVasFee
        reveicer.setColectingRevenue(Commons.DinhDangChuoiTien(item.getConsigneeCodAmount())+"đ"); //Tổng tiền thu hộ consigneeCodAmount
        reveicer.setVAT(Commons.DinhDangChuoiTien(item.getConsigneeVatAmount())+"đ"); //Phí VAT consigneeVatAmount
        reveicer.setCOD(Commons.DinhDangChuoiTien(item.getConsigneeCodFee())+"đ"); //Phí thu hộ
        reveicer.setExtraFee(Commons.DinhDangChuoiTien(item.getConsigneeExtraFee())+"đ"); //Chi phí khác
        reveicer.setTotalFee(Commons.DinhDangChuoiTien(item.getTotalFeeReceiver())+"đ"); //Tổng thu
        lnRevenueSender.addView(reveicer);

        //TODO: Sum phí người gửi
        FreightFeeReciver += item.getSenderShippingFee();
        AddedServiceFee += item.getSenderVasFee();
        ColectingRevenue += item.getSenderCodAmount();
        CODFee += item.getSenderCodFee();
        VATSend += item.getSenderVatAmount();
        ExtraFeeSend += item.getSenderExtraFee();

        double totalFeeSender = FreightFeeReciver + AddedServiceFee + ColectingRevenue + CODFee + VATSend + ExtraFeeSend;

        //TODO: Gán giá trị thu người gửi
        headerSend.setConfirmCode(Commons.DinhDangChuoiTien(totalFeeSender+"")+"đ");
        tvFreightFeeReciver.setText(Commons.DinhDangChuoiTien(FreightFeeReciver+"")+"đ");
        tvAddedServiceFeeReciver.setText(Commons.DinhDangChuoiTien(AddedServiceFee+"")+"đ");
        tvColectingRevenueReciver.setText(Commons.DinhDangChuoiTien(ColectingRevenue+"")+"đ");
        tvCODFeeSend.setText(Commons.DinhDangChuoiTien(CODFee+"")+"đ");
        tvVATSend.setText(Commons.DinhDangChuoiTien(VATSend+"")+"đ");
        tvExtraFeeSend.setText(Commons.DinhDangChuoiTien(ExtraFeeSend+"")+"đ");
    }

    private ReveicerFeeLayout drawReveicer(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ReveicerFeeLayout reveicer = new ReveicerFeeLayout(getContext());
        reveicer.setLayoutParams(params);
        return reveicer;
    }

    @Override
    public void changeFragment() {
        if(!loadLayout) {
            init();
            loadLayout = true;
        }
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Order> mList) {

    }


}
