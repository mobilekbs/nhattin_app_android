package vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login;

import java.io.Serializable;

/**
 * Created by Zanty on 22/06/2016.
 */
public class User implements Serializable {
    private int idStaff;
    private String publickey;
    private String localkey;
    private String value_staff;
    private String myBank;

    public User() {
    }

    public User(int idStaff, String publickey, String localkey, String value_staff, String myBank) {
        this.idStaff = idStaff;
        this.publickey = publickey;
        this.localkey = localkey;
        this.value_staff = value_staff;
        this.myBank = myBank;
    }

    public int getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getLocalkey() {
        return localkey;
    }

    public void setLocalkey(String localkey) {
        this.localkey = localkey;
    }

    public String getValue_staff() {
        return value_staff;
    }

    public void setValue_staff(String value_staff) {
        this.value_staff = value_staff;
    }

    public String getMyBank() {
        return myBank;
    }

    public void setMyBank(String myBank) {
        this.myBank = myBank;
    }
}
