package vn.ntlogistics.app.ordermanagement.Commons.ACRA;


import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.GmailSender.GmailSender;


/**
 * Created by Zanty on 08/08/2016.
 */
public class ACRAReportSender implements ReportSender {

    /** Extract the required data out of the crash report.*/
    private String createCrashReport(CrashReportData report) {

        // I've extracted only basic information.
        // U can add loads more data using the enum ReportField. See below.
        StringBuilder body = new StringBuilder();
        body
                .append("Appname : Nhat Tin - Shipper")
                .append("\n")
                .append("Device : " + report.getProperty(ReportField.BRAND) + "-" + report.getProperty(ReportField.PHONE_MODEL))
                .append("\n")
                .append("Android Version :" + report.getProperty(ReportField.ANDROID_VERSION))
                .append("\n")
                .append("App Version : " + report.getProperty(ReportField.APP_VERSION_CODE))
                .append("\n")
                .append("STACK TRACE : \n" + report.getProperty(ReportField.STACK_TRACE));


        return body.toString();
    }

    @Override
    public void send(CrashReportData errorContent) throws ReportSenderException {
        // Extract the required data out of the crash report.
        final String reportBody = createCrashReport(errorContent);
        final GmailSender gmail = new GmailSender();

        new BaseAsystask() {

            @Override
            public void onPre() {

            }

            @Override
            public void doInBG() {
                try {
                    gmail.sendMail(
                            "Bug",
                            reportBody,
                            Constants.USER_GMAIL,
                            Constants.CC_GMAIL
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPost() {

            }
        }.execute();
    }
}
