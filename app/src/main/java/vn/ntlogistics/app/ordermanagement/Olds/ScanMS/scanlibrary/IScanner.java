package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary;

import android.net.Uri;

/**
 * Created by jhansi on 04/04/15.
 */
public interface IScanner {

    void onBitmapSelect(Uri uri);

    void onScanFinish(Uri uri);
    

}
