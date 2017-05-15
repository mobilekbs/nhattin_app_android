package vn.ntlogistics.app.shipper.Models.Outputs.Order;

/**
 * Created by Zanty on 12/07/2016.
 */
public class StatusBL {
    private String blCode;
    private String insertDate;
    private String status;
    private String jobName;

    public StatusBL() {
    }

    public String getBlCode() {
        return blCode;
    }

    public void setBlCode(String blCode) {
        this.blCode = blCode;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
