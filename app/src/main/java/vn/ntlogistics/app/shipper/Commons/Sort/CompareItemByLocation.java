package vn.ntlogistics.app.shipper.Commons.Sort;


import java.util.Comparator;

import vn.ntlogistics.app.shipper.Models.Outputs.Others.Item;

/**
 * Created by Zanty on 01/08/2016.
 */
public class CompareItemByLocation implements Comparator<Item> {

    /**
     * Dùng:
     * Collections.sort(lstBaoCao_VPX_HienThi, new CompareByThanhTien());
     */

    public int compare(Item p1, Item p2) {
        try {
            float tt1 = p1.getDistance();
            float tt2 = p2.getDistance();
            if (tt1 > tt2) {
                return 1;
            }
            if (tt1 < tt2) {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // at this point all a.b,c,d are equal... so return "equal"
        return 0;
    }
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

}
