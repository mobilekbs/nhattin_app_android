package vn.ntlogistics.app.ordermanagement.Views.Fragments.Image;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.ImageViewPager.photoview.PhotoView;
import vn.ntlogistics.app.ordermanagement.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewImageFragment extends Fragment {
    private String          imageUrl;
    PhotoView imageView;

    DisplayMetrics          metrics;
    int                     deviceWidth;

    public ViewImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_image, container, false);

        imageView = (PhotoView) view.findViewById(R.id.imgViewImage);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        metrics = getActivity().getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        Glide.with(getActivity())
                .load(imageUrl)
                .override(deviceWidth, 0)
                .into(imageView);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //    public void setImageRotate() {
    //SUB
//        imageView.setOrientation((imageView.getOrientation() + 90) % 360);
//    }
    public void setImageRotate() {
        //Photo
        imageView.setRotation((imageView.getRotation() + 90) % 360);

    }
}
