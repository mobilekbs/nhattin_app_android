package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.Camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;

/**
 * Created by Zanty on 28/10/2017.
 */

public class CameraModule {
    public static void getCamera(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // your code using Camera API here - is between 1-20
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // your code using Camera2 API here - is api 21 or higher
        }
    }
    public static Camera getCamera(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getNewCamera(Context context) {
        this.manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIds = manager.getCameraIdList();
            manager.openCamera(cameraIds[cameraId], new CameraDevice.StateCallback() {
                @Override
                public void onOpened(CameraDevice camera) {
                    CameraNew.this.camera = camera;
                }

                @Override
                public void onDisconnected(CameraDevice camera) {
                    CameraNew.this.camera = camera;
                    // TODO handle
                }

                @Override
                public void onError(CameraDevice camera, int error) {
                    CameraNew.this.camera = camera;
                    // TODO handle
                }
            }, null);
        } catch (Exception e) {
            // TODO handle
        }
    }*/
}
