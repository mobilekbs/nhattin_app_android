package com.andemteam.eaty.Commons.CustomViews.Spinner.Beans;

import java.io.Serializable;

/**
 * Created by Zanty on 24/06/2017.
 */

public class ItemSpinner implements Serializable {
    private int id;
    private String title;

    public ItemSpinner() {
    }

    public ItemSpinner(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
