package vn.ntlogistics.app.ordermanagement.Commons.Sort;


import java.util.Comparator;

import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;


/**
 * Created by Zanty on 01/08/2016.
 */
public class CompareItemByLocation implements Comparator<Bill> {

    /**
     * Dùng:
     * Collections.sort(lstBaoCao_VPX_HienThi, new CompareByThanhTien());
     */

    public int compare(Bill p1, Bill p2) {
        try {
            long tt1 = p1.getTotalDistance();
            long tt2 = p2.getTotalDistance();
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
