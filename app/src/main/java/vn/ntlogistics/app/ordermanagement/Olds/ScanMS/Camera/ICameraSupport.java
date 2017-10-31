package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.Camera;

import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Zanty on 28/10/2017.
 */

public interface ICameraSupport {
    ICameraSupport open(int cameraId);
    int getOrientation(int cameraId);
    void setPreviewDisplay(SurfaceHolder holder) throws IOException;
    void stopPreview();
}
