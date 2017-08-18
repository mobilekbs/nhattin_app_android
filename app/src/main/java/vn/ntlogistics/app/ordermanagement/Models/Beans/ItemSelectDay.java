package vn.ntlogistics.app.ordermanagement.Models.Beans;

import java.io.Serializable;

/**
 * Created by Zanty on 05/08/2017.
 */

public class ItemSelectDay implements Serializable {
    private boolean selected;
    private String nameDay;
    private String numberDay;
    private int month;
    private int year;
    private int date;

    public ItemSelectDay() {
    }

    public ItemSelectDay(boolean selected, String nameDay, String numberDay, int month, int year) {
        this.selected = selected;
        this.nameDay = nameDay;
        this.numberDay = numberDay;
        this.month = month;
        this.year = year;
        this.date = Integer.parseInt(numberDay);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNameDay() {
        return nameDay;
    }

    public void setNameDay(String nameDay) {
        this.nameDay = nameDay;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public void setNumberDay(String numberDay) {
        this.numberDay = numberDay;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
