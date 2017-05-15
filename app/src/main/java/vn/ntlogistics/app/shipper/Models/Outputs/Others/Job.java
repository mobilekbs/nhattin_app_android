package vn.ntlogistics.app.shipper.Models.Outputs.Others;

/**
 * Created by minhtan2908 on 11/25/16.
 */

public class Job {
    private int id;
    private String name;

    public Job() {
    }

    public Job(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
