package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.Spinner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.Spinner.Beans.ItemSpinner;

/**
 * Created by Zanty on 24/06/2017.
 */

public class BaseSpinner {
    public static  <T extends ItemSpinner> void setupSpinner(Spinner spinner, final ArrayList<T> mList) {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                spinner.getContext(),
                android.R.layout.simple_spinner_item
                , mList) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(mList.get(position).getTitle());
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(mList.get(position).getTitle());
                return view;
                //return super.getView(position, convertView, parent);
            }

            @Nullable
            @Override
            public T getItem(int position) {
                return super.getItem(position);
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }
}
