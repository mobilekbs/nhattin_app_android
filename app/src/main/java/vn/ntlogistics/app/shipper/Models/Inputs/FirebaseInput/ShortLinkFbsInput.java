package vn.ntlogistics.app.shipper.Models.Inputs.FirebaseInput;

/**
 * Created by minhtan2908 on 3/22/17.
 */

public class ShortLinkFbsInput {
    private String longDynamicLink;
    private Suffix suffix;

    public ShortLinkFbsInput() {
    }

    public ShortLinkFbsInput(String longDynamicLink) {
        this.longDynamicLink = longDynamicLink;
        this.suffix = new Suffix();
    }

    public String getLongDynamicLink() {
        return longDynamicLink;
    }

    public void setLongDynamicLink(String longDynamicLink) {
        this.longDynamicLink = longDynamicLink;
    }

    public Suffix getSuffix() {
        return suffix;
    }

    public void setSuffix(Suffix suffix) {
        this.suffix = suffix;
    }

    class Suffix{
        private String option = "UNGUESSABLE";

        public Suffix() {
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }
    }
}
