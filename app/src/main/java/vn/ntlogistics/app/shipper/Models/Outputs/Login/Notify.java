package vn.ntlogistics.app.shipper.Models.Outputs.Login;

/**
 * Created by Zanty on 15/07/2016.
 */
public class Notify {
    private String id;
    private String title;
    private String content;
    private String url;
    private boolean seen = false;

    public Notify() {
    }

    public Notify(String title, String content, boolean seen) {
        this.title = title;
        this.content = content;
        this.seen = seen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
