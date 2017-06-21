package vn.ntlogistics.app.ordermanagement.Models.Outputs.Base;

import java.io.Serializable;

/**
 * Created by Zanty on 25/05/2017.
 */

public class BaseOutput<T> implements Serializable {
    private Integer errorType;
    /*
     * Mã lỗi 200: thành công.
     * 116 - DO Code không hợp lệ
     * 117 - DO Code đã được sử dụng
     * 141 - Vận đơn đã được cập nhật // old 2
     * 142 - Lỗi thông tin // old -1
     * 143 - Vận đơn không tồn tại // old -2
     * 144 - Vận đơn đã được nhập liệu // old -3 -4
     * 145 - Mã khách hàng không hợp lệ // old -6
     */
    private Integer errorCode;
    /*
     * Nội dung lỗi null: TH thanh công. Khác null: TH có lỗi
     */
    private String errorMessage;

    private T data;

    public BaseOutput() {
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
