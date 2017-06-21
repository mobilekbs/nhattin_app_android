package vn.ntlogistics.app.ordermanagement.Views.Fragments.OrderDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.ContentGoodsLayout;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.ImageViewPager.ViewPagerImageAdapter;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BLDetail;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BillDetail;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.RecyclerViewServiceAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceInfoFragment extends BaseFragment {
    View v;
    LinearLayout lnGoods;

    //TODO: image viewpager
    List<String> mUrls;
    //TODO: Layout chứa viewPager, nếu mUrls rỗng thì ẩn viewpager đi
    View viewPagerIndicator;
    ViewPagerImageAdapter imageAdapter;
    ViewPager viewPagerImage;
    private ImageButton btnNext, btnPre;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;

    String categoryTitle;

    //TODO: Biến lưu OrderDetail tạm
    BillDetail detail;
    Bill bill;
    List<BLDetail> mListBLDetail;
    //TODO: Adapter của recycler view hiển thị dịch vụ
    RecyclerViewServiceAdapter adapter;
    List<Integer> mListService;

    /**
     * Khai báo giao diện
     */
    //Kích thước/ khối lượng
    TextView tvCargoNumber, tvTotalSize, tvSizeDetail;
    View lnSizeWeight;

    //Dịch vụ gia tăng
    TextView tvTypeDelivery;
    RecyclerView rv;

    int blDetailHub = 0, position;

    boolean loadLayout;

    public ServiceInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_service_info, container, false);

        /*final BaseAsystask asystask = new BaseAsystask(){

            @Override
            public void onPre() {

            }

            @Override
            public void doInBG() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        init(v);
                    }
                });
            }

            @Override
            public void onPost() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupControls();
                    }
                });
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                asystask.execute();
            }
        }, 200);*/

        return v;
    }

    private void init() {
        initControls();

        initViewPagerImage();
    }

    private void initControls() {
        tvCargoNumber = (TextView) v.findViewById(R.id.tvCargoNumber);
        tvTotalSize = (TextView) v.findViewById(R.id.tvTotalSize);
        tvSizeDetail = (TextView) v.findViewById(R.id.tvSizeDetail);
        tvTypeDelivery = (TextView) v.findViewById(R.id.tvTypeDelivery);
        lnSizeWeight = v.findViewById(R.id.lnSizeWeight);

        //categoryTitle = getActivity().getIntent().getExtras().getString("categoryTitle");

        //detail = SOrderDetail.getOurInstance();
        try {
            detail = (BillDetail) getActivity().getIntent().getExtras().getSerializable("orderDetail");
            bill = (Bill) getActivity().getIntent().getExtras().getSerializable("order");
            categoryTitle = bill.getService();
        } catch (Exception e) {
        }

        try {
            blDetailHub = getActivity().getIntent().getExtras().getInt("blDetailHub");
            position = getActivity().getIntent().getExtras().getInt("position",-1);
        } catch (Exception e) {
        }

        if (detail != null) {
            mListBLDetail = detail.getData();
            if(mListBLDetail == null)
                mListBLDetail = new ArrayList<>();
        }
        else
            mListBLDetail = new ArrayList<>();

        //TODO: Dịch vụ gia tăng
        setupAddService();

        //TODO: Kích thước, khối lượng chỉ hiển thị khi có 1 điểm đến
        if (mListBLDetail.size() < 2) {
            lnSizeWeight.setVisibility(View.VISIBLE);
            tvCargoNumber.setText(mListBLDetail.get(0).getQuantity());
            tvTotalSize.setText(mListBLDetail.get(0).getGrossWeight() + " kg");
            tvSizeDetail.setText(
                    mListBLDetail.get(0).getLength() + " x " +
                            mListBLDetail.get(0).getWidth() + " x " +
                            mListBLDetail.get(0).getHeight()
            );
        } else {
            lnSizeWeight.setVisibility(View.GONE);
        }

        //TODO: Khởi tại nội dung hàng hóa
        initContentGoods(v);

    }

    /**
     * Sau khi khỏi tạo giao diện sẽ truyền dữ liệu hiển thị vào
     */
    private void setupControls() {
        setupServiceIntoRecyclerView();
    }

    /**
     * Kiểm tra có loại dịch vụ nào có trong đơn hàng không
     * Nếu có thì thêm hình ảnh thể hiện dịch vụ đó
     */
    private void setupServiceIntoRecyclerView() {
        boolean insurance = false;
        boolean porter = false;
        boolean cod = false;
        if(blDetailHub == 0) {
            for (int i = 0; i < mListBLDetail.size(); i++) {
                if (mListBLDetail.get(i).checkInsurance())
                    insurance = true;
                if (mListBLDetail.get(i).checkPorter())
                    porter = true;
                if (mListBLDetail.get(i).checkCOD())
                    cod = true;
            }
        }
        else if(blDetailHub == 1){
            if (mListBLDetail.get(position).checkInsurance())
                insurance = true;
            if (mListBLDetail.get(position).checkPorter())
                porter = true;
            if (mListBLDetail.get(position).checkCOD())
                cod = true;
        }
        if(insurance)
            mListService.add(R.mipmap.ic_insurance);
        if(porter)
            mListService.add(R.mipmap.ic_porter);
        if(cod)
            mListService.add(R.mipmap.ic_cod);
        adapter.notifyDataSetChanged();
    }

    private void setupAddService() {
        tvTypeDelivery.setText(categoryTitle); //Loại dịch vụ vận chuyển
        //TODO: Khởi tạo list dịch vụ
        initInsurance(v);
    }

    private void initInsurance(View v) {
        rv = (RecyclerView) v.findViewById(R.id.rvInsurance);
        mListService = new ArrayList<>();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        adapter = new RecyclerViewServiceAdapter(getContext(), mListService);
        rv.setAdapter(adapter);
    }

    private void initContentGoods(View v) {
        lnGoods = (LinearLayout) v.findViewById(R.id.lnGoods);
        if(blDetailHub == 0) {
            for (int i = 0; i < mListBLDetail.size(); i++) {
                BLDetail item = mListBLDetail.get(i);
                ContentGoodsLayout content = drawContentGoods();
                content.setTvValue(Commons.DinhDangChuoiTien(item.getCargoValue()));
                content.setTvType(item.getCargoTitle());
                content.setTvContent(item.getCargoContent());
                lnGoods.addView(content);
            }
        }
        else if(blDetailHub == 1){ //HUB
            BLDetail item = mListBLDetail.get(position);
            ContentGoodsLayout content = drawContentGoods();
            content.setTvValue(Commons.DinhDangChuoiTien(item.getCargoValue()));
            content.setTvType(item.getCargoTitle());
            content.setTvContent(item.getCargoContent());
            lnGoods.addView(content);
        }
    }

    private void initViewPagerImage() {
        viewPagerImage = (ViewPager) v.findViewById(R.id.pager_introduction);
        btnNext = (ImageButton) v.findViewById(R.id.btn_next);
        btnPre = (ImageButton) v.findViewById(R.id.btn_finish);

        viewPagerIndicator = v.findViewById(R.id.viewPagerIndicator);

        pager_indicator = (LinearLayout) v.findViewById(R.id.viewPagerCountDots);

        mUrls = new ArrayList<>();
        if(detail.getCargoPhoto() != null)
            mUrls.addAll(detail.getCargoPhoto());

        if(mUrls.size()>0){
            viewPagerIndicator.setVisibility(View.VISIBLE);
            setUiPageViewController();
        }
        else
            viewPagerIndicator.setVisibility(View.GONE);


    }

    private void setUiPageViewController() {
        //TODO: setup Viewpager
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPagerImage.getCurrentItem() < dotsCount) {
                    btnNext.setVisibility(View.VISIBLE);
                    viewPagerImage.setCurrentItem(viewPagerImage.getCurrentItem() + 1);
                } else {
                    btnNext.setVisibility(View.GONE);
                }
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPagerImage.getCurrentItem() < 1) {
                    btnPre.setVisibility(View.GONE);
                } else {
                    btnPre.setVisibility(View.VISIBLE);
                    viewPagerImage.setCurrentItem(viewPagerImage.getCurrentItem() - 1);
                }
            }
        });

        imageAdapter = new ViewPagerImageAdapter(getContext(), mUrls, bill.getBillID(),true);
        viewPagerImage.setAdapter(imageAdapter);
        viewPagerImage.setCurrentItem(0);
        viewPagerImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(
                            ContextCompat.getDrawable(getContext(), R.drawable.nonselecteditem_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selecteditem_dot));

                if (position + 1 == dotsCount) {
                    btnNext.setVisibility(View.GONE);
                } else if (position == 0) {
                    btnPre.setVisibility(View.GONE);
                } else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnPre.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //TODO: ---------------------

        dotsCount = imageAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    private ContentGoodsLayout drawContentGoods() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ContentGoodsLayout content = new ContentGoodsLayout(getContext());
        content.setLayoutParams(params);
        return content;
    }

    @Override
    public void changeFragment() {
        try {
            if(!loadLayout){
                init();
                setupControls();
                loadLayout = true;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Bill> mList) {

    }
}
