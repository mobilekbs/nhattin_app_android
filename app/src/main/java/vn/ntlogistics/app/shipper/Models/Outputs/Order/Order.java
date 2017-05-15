package vn.ntlogistics.app.shipper.Models.Outputs.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Zanty on 25/06/2016.
 */
public class Order extends Observable implements Serializable {
    private String shippingCode;
    private String amount;
    private String assignDatetime;
    private int jobType;
    private String sourceAddress;
    private String destinationAddress;
    private String stopCount;
    private String status;
    private String categoryTitle;
    // 0 - Binh thuong | 1 - tuc thoi | 2 - Hub
    private int orderType;
    private double distance;
    private List<String> lstBLCode;

    private double shipperAmount; //Tiền shipper nhận được
    private double codAmount; //Tiền COD
    private double advanceCodAmount; //Tiền ứng trước

    /*
   * Tổng khoảng cách
   */
    private long totalDistance;

    /*
     * List district from->to
     */
    private ArrayList<String> lstDistrictCode;
    private ArrayList<String> lstNote;

    private String orderKCode;

    public Order() {
    }

    public String getOrderKCode() {
        return orderKCode;
    }

    public void setOrderKCode(String orderKCode) {
        this.orderKCode = orderKCode;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public String getTotalDistanceText() {
        return totalDistance / 1000 + "." + (totalDistance % 1000) / 100 + " km";
    }

    public void setTotalDistance(long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public ArrayList<String> getLstDistrictCode() {
        return lstDistrictCode;
    }

    public void setLstDistrictCode(ArrayList<String> lstDistrictCode) {
        this.lstDistrictCode = lstDistrictCode;
    }

    public double getShipperAmount() {
        return shipperAmount;
    }

    public void setShipperAmount(double shipperAmount) {
        this.shipperAmount = shipperAmount;
    }

    public double getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(double codAmount) {
        this.codAmount = codAmount;
    }

    public double getAdvanceCodAmount() {
        return advanceCodAmount;
    }

    public void setAdvanceCodAmount(double advanceCodAmount) {
        this.advanceCodAmount = advanceCodAmount;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<String> getLstBLCode() {
        return lstBLCode;
    }

    public void setLstBLCode(List<String> lstBLCode) {
        this.lstBLCode = lstBLCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getAssignDatetime() {
        return assignDatetime;
    }

    public void setAssignDatetime(String assignDatetime) {
        this.assignDatetime = assignDatetime;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getStopCount() {
        return stopCount;
    }

    public void setStopCount(String stopCount) {
        this.stopCount = stopCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getLstNote() {
        return lstNote;
    }

    public void setLstNote(ArrayList<String> lstNote) {
        this.lstNote = lstNote;
    }

    public String getStringNode() {
        String note = "";
        if (lstNote != null) {
            if (lstNote.size() == 1) {
                note += lstNote.get(0);
            } else {
                for (int i = 0; i < lstNote.size(); i++) {
                    if (lstNote.get(i) != null && lstNote.get(i).length() > 0) {
                        int pos = i + 1;
                        if (i == 0) {
                            note += pos + ". " + lstNote.get(i);
                        } else {
                            note += "\n" + pos + ". " + lstNote.get(i);
                        }
                    }
                }
            }
        }
        return note;
    }
}
