package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.Camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Zanty on 28/10/2017.
 */
@SuppressWarnings("deprecation")
public class OldCamera implements ICameraSupport {
    private Camera camera;

    @Override
    public ICameraSupport open(int cameraId) {
        this.camera = Camera.open(cameraId);
        return this;
    }

    @Override
    public int getOrientation(int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        return info.orientation;
    }

    @Override
    public void setPreviewDisplay(SurfaceHolder holder) throws IOException {
        if (camera == null)
        camera.setPreviewDisplay(holder);
    }

    @Override
    public void stopPreview() {
        camera.stopPreview();
    }
}
