package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Reader;

import java.util.Hashtable;

public class DetectCode {
	public static String readBarcode(Bitmap bbMap) {

		/** way 2 **/
		Log.d("Before1", "Height: " + bbMap.getHeight());
		Log.d("Before1", "Width: " + bbMap.getWidth());
		Bitmap bMap = resizeImageToShow(bbMap);

		Log.d("After1", "Height: " + bMap.getHeight());
		Log.d("After1", "Width: " + bMap.getWidth());

		int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
		// copy pixel data from the Bitmap into the 'intArray' array
		bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
				bMap.getHeight());
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

		LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
				bMap.getHeight(), intArray);

		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		// Reader reader = new DataMatrixReader();
		Code128Reader reader = new Code128Reader();
		// ....doing the actually reading
		try {
			Result result = reader.decode(bitmap, hints);
			return result.toString();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// ImageView cannot show too large image.
	public static Bitmap resizeImageToShow(Bitmap bitmap) {
		final float LIMIT = 1250f;

		float dai = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth()
				: bitmap.getHeight();

		Log.d("MYWIDTH", dai + "");

		if (dai <= 1500) {
			return bitmap;
		}

		double widthRatio = bitmap.getWidth() / LIMIT;
		double heightRatio = bitmap.getHeight() / LIMIT;

		double ratio = Math.max(widthRatio, heightRatio);

		int resizedWidth = (int) (bitmap.getWidth() / ratio);
		int resizedHeight = (int) (bitmap.getHeight() / ratio);

		Log.d("", String.format("ResizTouching image to %d %d.", resizedWidth,
				resizedHeight));

		return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight,
				false);
	}
}
