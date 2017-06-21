package vn.ntlogistics.app.ordermanagement.Olds.ScanMS;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraMS extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private PreviewCallback previewCallback;
	private AutoFocusCallback autoFocusCallback;

	public CameraMS(Context context, Camera camera, PreviewCallback previewCb,
                    AutoFocusCallback autoFocusCb) {
		super(context);
		mCamera = camera;
		previewCallback = previewCb;
		autoFocusCallback = autoFocusCb;

		mHolder = getHolder();
		mHolder.addCallback(this);
		
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d("DBG", "Error setting camera preview: " + e.getMessage());
        }

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
          }

          // stop preview before making changes
          try {
              mCamera.stopPreview();
          } catch (Exception e){
            // ignore: tried to stop a non-existent preview
          }

          try {
              // Hard code camera surface rotation 90 degs to match Activity view in portrait
              mCamera.setDisplayOrientation(90);

              mCamera.setPreviewDisplay(mHolder);
              mCamera.setPreviewCallback(previewCallback);
              mCamera.startPreview();
              mCamera.autoFocus(autoFocusCallback);
          } catch (Exception e){
              Log.d("DBG", "Error starting camera preview: " + e.getMessage());
          }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

}
