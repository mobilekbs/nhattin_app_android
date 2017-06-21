package vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.BillFail;

/**
 * Created by Zanty on 02/06/2017.
 */

public class BillFailSqlite {
    private int id;
    private String bill;
    private String customerName;
    private String customerID;
    private String money;
    private String moneyCod;
    private String city;
    private String district;
    private String status;
    private String weight;
    private String itemQty;
    private String isDO;
    private String dimensionWeight;
    private String packageNo;

    public BillFailSqlite() {
    }

    public BillFailSqlite(int id, String bill, String customerName, String customerID, String money, String moneyCode, String city, String district, String status, String weight, String itemQty, String isDO, String dimensionWeight, String packageNo) {
        this.id = id;
        this.bill = bill;
        this.customerName = customerName;
        this.customerID = customerID;
        this.money = money;
        this.moneyCod = moneyCode;
        this.city = city;
        this.district = district;
        this.status = status;
        this.weight = weight;
        this.itemQty = itemQty;
        this.isDO = isDO;
        this.dimensionWeight = dimensionWeight;
        this.packageNo = packageNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyCod() {
        return moneyCod;
    }

    public void setMoneyCod(String moneyCode) {
        this.moneyCod = moneyCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDimensionWeight() {
        return dimensionWeight;
    }

    public void setDimensionWeight(String dimensionWeight) {
        this.dimensionWeight = dimensionWeight;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getIsDO() {
        return isDO;
    }

    public void setIsDO(String isDO) {
        this.isDO = isDO;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }
}
