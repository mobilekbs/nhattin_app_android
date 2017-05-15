package vn.ntlogistics.app.shipper.Models.Outputs.FirebaseOutput;

/**
 * Created by minhtan2908 on 3/22/17.
 */

public class ShortLinkFbsOutput {
    private String shortLink;
    private String previewLink;

    public ShortLinkFbsOutput() {
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }
}
