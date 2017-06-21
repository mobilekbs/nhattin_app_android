package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by jhansi on 29/03/15.
 */
public class ResultFragment extends Fragment {

	private View view;
	private ImageView scannedImageView;
	private Bitmap original;

	private Button doneButton;

	private RadioGroup rGroupBtn;

	private RadioButton originalButton;
	private RadioButton MagicColorButton;
	private RadioButton grayModeButton;
	private RadioButton bwButton;
	private RadioButton superMagicButton;

	private Drawable icdefaultwhite, icdefaultblack, iclightblack,
			iclightwhite, icgrayblack, icgraywhite, icbwblack, icbwwhite,
			icmagicblack, icmagicwhite;

	private Bitmap transformed;

	private CapturePhotoUtils cpu = new CapturePhotoUtils();

	ItemBill itemBill = new ItemBill();

	public ResultFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.result_layout, null);
		init();
		return view;
	}

	private void init() {

		icdefaultblack = getResources().getDrawable(R.drawable.icdefaultblack);
		icdefaultwhite = getResources().getDrawable(R.drawable.icdefaultwhite);
		iclightblack = getResources().getDrawable(R.drawable.iclightblack);
		iclightwhite = getResources().getDrawable(R.drawable.iclightwhite);
		icgrayblack = getResources().getDrawable(R.drawable.icgrayblack);
		icgraywhite = getResources().getDrawable(R.drawable.icgraywhite);
		icbwblack = getResources().getDrawable(R.drawable.icbwblack);
		icbwwhite = getResources().getDrawable(R.drawable.icbwwhite);

		icmagicblack = getResources().getDrawable(R.drawable.icmagicblack);
		icmagicwhite = getResources().getDrawable(R.drawable.icmagicwhite);

		scannedImageView = (ImageView) view.findViewById(R.id.scannedImage);

		originalButton = (RadioButton) view.findViewById(R.id.original);
		superMagicButton = (RadioButton) view
				.findViewById(R.id.superMagicColor);
		MagicColorButton = (RadioButton) view.findViewById(R.id.magicColor);
		grayModeButton = (RadioButton) view.findViewById(R.id.grayMode);
		bwButton = (RadioButton) view.findViewById(R.id.BWMode);

		doneButton = (Button) view.findViewById(R.id.doneButton);
		doneButton.setOnClickListener(new DoneButtonClickListener());

		rGroupBtn = (RadioGroup) view.findViewById(R.id.rGroupBtn);
		// When Pick

		Bitmap bitmap = getBitmap();
		chekRadioButton();

		// setScannedImage(bitmap);

	}

	public void chekRadioButton() {
		if (superMagicButton.isChecked()) {

			originalButton.setTextColor(Color.parseColor("#606060"));
			superMagicButton.setTextColor(Color.WHITE);
			MagicColorButton.setTextColor(Color.parseColor("#606060"));
			grayModeButton.setTextColor(Color.parseColor("#606060"));
			bwButton.setTextColor(Color.parseColor("#606060"));

			originalButton.setCompoundDrawablesWithIntrinsicBounds(null,
					icdefaultblack, null, null);
			superMagicButton.setCompoundDrawablesWithIntrinsicBounds(null,
					icmagicwhite, null, null);
			MagicColorButton.setCompoundDrawablesWithIntrinsicBounds(null,
					iclightblack, null, null);
			grayModeButton.setCompoundDrawablesWithIntrinsicBounds(null,
					icgrayblack, null, null);
			bwButton.setCompoundDrawablesWithIntrinsicBounds(null, icbwblack,
					null, null);
			superMagicButton();
		}
		rGroupBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (originalButton.isChecked()) {
					originalButton.setTextColor(Color.WHITE);
					MagicColorButton.setTextColor(Color.parseColor("#606060"));
					grayModeButton.setTextColor(Color.parseColor("#606060"));
					bwButton.setTextColor(Color.parseColor("#606060"));
					superMagicButton.setTextColor(Color.parseColor("#606060"));

					originalButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icdefaultwhite, null, null);
					MagicColorButton.setCompoundDrawablesWithIntrinsicBounds(
							null, iclightblack, null, null);
					grayModeButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icgrayblack, null, null);
					bwButton.setCompoundDrawablesWithIntrinsicBounds(null,
							icbwblack, null, null);
					superMagicButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icmagicblack, null, null);

					OriginalButton();

				} else if (superMagicButton.isChecked()) {
					superMagicButton.setTextColor(Color.WHITE);
					originalButton.setTextColor(Color.parseColor("#606060"));
					MagicColorButton.setTextColor(Color.parseColor("#606060"));
					grayModeButton.setTextColor(Color.parseColor("#606060"));
					bwButton.setTextColor(Color.parseColor("#606060"));

					superMagicButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icmagicwhite, null, null);
					originalButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icdefaultblack, null, null);
					MagicColorButton.setCompoundDrawablesWithIntrinsicBounds(
							null, iclightblack, null, null);
					grayModeButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icgrayblack, null, null);
					bwButton.setCompoundDrawablesWithIntrinsicBounds(null,
							icbwblack, null, null);
					superMagicButton();
				}

				else if (MagicColorButton.isChecked()) {
					originalButton.setTextColor(Color.parseColor("#606060"));
					MagicColorButton.setTextColor(Color.WHITE);
					grayModeButton.setTextColor(Color.parseColor("#606060"));
					bwButton.setTextColor(Color.parseColor("#606060"));
					superMagicButton.setTextColor(Color.parseColor("#606060"));
					originalButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icdefaultblack, null, null);
					MagicColorButton.setCompoundDrawablesWithIntrinsicBounds(
							null, iclightwhite, null, null);
					grayModeButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icgrayblack, null, null);
					bwButton.setCompoundDrawablesWithIntrinsicBounds(null,
							icbwblack, null, null);
					superMagicButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icmagicblack, null, null);

					MagicColorButton();
				} else if (grayModeButton.isChecked()) {
					originalButton.setTextColor(Color.parseColor("#606060"));
					MagicColorButton.setTextColor(Color.parseColor("#606060"));
					grayModeButton.setTextColor(Color.WHITE);
					bwButton.setTextColor(Color.parseColor("#606060"));
					superMagicButton.setTextColor(Color.parseColor("#606060"));

					originalButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icdefaultblack, null, null);
					MagicColorButton.setCompoundDrawablesWithIntrinsicBounds(
							null, iclightblack, null, null);
					grayModeButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icgraywhite, null, null);
					bwButton.setCompoundDrawablesWithIntrinsicBounds(null,
							icbwblack, null, null);
					superMagicButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icmagicblack, null, null);

					GrayButton();
				} else if (bwButton.isChecked()) {
					originalButton.setTextColor(Color.parseColor("#606060"));
					MagicColorButton.setTextColor(Color.parseColor("#606060"));
					grayModeButton.setTextColor(Color.parseColor("#606060"));
					bwButton.setTextColor(Color.WHITE);
					superMagicButton.setTextColor(Color.parseColor("#606060"));

					originalButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icdefaultblack, null, null);
					MagicColorButton.setCompoundDrawablesWithIntrinsicBounds(
							null, iclightblack, null, null);
					grayModeButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icgrayblack, null, null);
					bwButton.setCompoundDrawablesWithIntrinsicBounds(null,
							icbwwhite, null, null);
					superMagicButton.setCompoundDrawablesWithIntrinsicBounds(
							null, icmagicblack, null, null);

					BWButton();
				}
			}
		});
	}

	private Bitmap getBitmap() {
		Uri uri = getUri();
		try {
			original = Utils.getBitmap(getActivity(), uri);
			getActivity().getContentResolver().delete(uri, null, null);
			return original;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Uri getUri() {
		Uri uri = getArguments().getParcelable(ScanConstants.SCANNED_RESULT);
		return uri;
	}

	public void setScannedImage(Bitmap scannedImage) {
		scannedImageView.setImageBitmap(scannedImage);
	}

	private class DoneButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent data = new Intent();
			Bitmap bitmap = transformed;
			if (bitmap == null) {
				bitmap = original;
			}
			// saveToInternalStorage(bitmap);
			// addImage(bitmap);

			Bitmap highBitmap = bitmap;
			Bitmap lowBitmap = Bitmap
					.createScaledBitmap(bitmap, 80, 107, false);

			itemBill.setImg(lowBitmap);
			String barcode = getBarcode(highBitmap);

			if (barcode == null) {
				itemBill.setBill("RD" + getNumberRan(0, 9));
			} else {
				itemBill.setBill(barcode);
			}

			// itemBill.setBill("RD" + getNumberRan(0, 9));
			Variables.LST_ItemBill.add(itemBill);
			int size = Variables.LST_ItemBill.size();

			saveToInternalStorage(highBitmap,
					Variables.LST_ItemBill.get(size - 1).getBill());

			// Uri uri = Utils.getUri(getActivity(), bitmap);
			// data.putExtra(ScanConstants.SCANNED_RESULT, uri);

			getActivity().setResult(Activity.RESULT_OK, data);
			original.recycle();
			System.gc();
			getActivity().finish();
		}
	}

	public String getNumberRan(int min, int max) {
		Random rn = new Random();
		String s = "";

		for (int i = 0; i < 7; i++) {
			s = s + (min + rn.nextInt(max - min + 1));
		}
		return s;
	}

	public static void addImageToGallery(final String filePath,
			final Context context) {

		ContentValues values = new ContentValues();

		values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.MediaColumns.DATA, filePath);

		context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI,

		values);
	}

	public Bitmap changeContrast(Bitmap bmp, float contrast, float brightness) {
		ColorMatrix cm = new ColorMatrix(new float[] { contrast, 0, 0, 0,
				brightness, 0, contrast, 0, 0, brightness, 0, 0, contrast, 0,
				brightness, 0, 0, 0, 1, 0 });

		Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
				bmp.getConfig());

		Canvas canvas = new Canvas(ret);

		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		canvas.drawBitmap(bmp, 0, 0, paint);

		return ret;
	}

	public Bitmap changeContrastVs2(Bitmap src, double value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap

		// create a mutable empty bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

		// create a canvas so that we can draw the bmOut Bitmap from source
		// bitmap
		Canvas c = new Canvas();
		c.setBitmap(bmOut);

		// draw bitmap to bmOut from src bitmap so we can modify it
		c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));

		// color information
		int A, R, G, B;
		int pixel;
		// get contrast value
		double contrast = Math.pow((100 + value) / 100, 2);

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				// apply filter contrast for every channel R, G, B
				R = Color.red(pixel);
				R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (R < 0) {
					R = 0;
				} else if (R > 255) {
					R = 255;
				}

				G = Color.green(pixel);
				G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (G < 0) {
					G = 0;
				} else if (G > 255) {
					G = 255;
				}

				B = Color.blue(pixel);
				B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (B < 0) {
					B = 0;
				} else if (B > 255) {
					B = 255;
				}

				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		return bmOut;
	}

	public void OriginalButton() {
		transformed = original;
		scannedImageView.setImageBitmap(original);
	}

	public void superMagicButton() {
		// transformed = ((ScanActivity) getActivity())
		// .getMagicColorBitmap(original);
		// transformed =changeContrast(original, 1.75f, -55);
		// transformed =changeContrast(original, 2.1f, -105);(standard)

		transformed = changeContrast(original, 2.3f, -110);
		scannedImageView.setImageBitmap(transformed);
	}

	public void MagicColorButton() {
		// transformed = ((ScanActivity) getActivity())
		// .getMagicColorBitmap(original);
		transformed = changeContrast(original, 1.1f, 50);
		// not negative

		// transformed=changeContrastVs2(original, 1);

		scannedImageView.setImageBitmap(transformed);
	}

	public void GrayButton() {
		transformed = ((ScanActivity) getActivity()).getGrayBitmap(original);
		scannedImageView.setImageBitmap(transformed);
	}

	public void BWButton() {
		transformed = ((ScanActivity) getActivity()).getBWBitmap(original);
		scannedImageView.setImageBitmap(transformed);
	}

	public static Bitmap rotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		// matrix.postRotate(angle);
		// matrix.setRotate(90);
		matrix.preRotate(90);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);
	}

	public String getBarcode(Bitmap bMap) {
		int i = 0;
		String barCode = "";
		while (i < 2) {
			if (i == 0) {
				barCode = DetectCode.readBarcode(bMap);
					
			} else {
				barCode = DetectCode.readBarcode(rotateBitmap(bMap, 90));
				
			}
			if (barCode != null) {
				return barCode;
			}
			i++;
		}
		return null;
	}

	private void saveToInternalStorage(Bitmap bitmapImage, String name) {
		Toast.makeText(getActivity(), R.string.imgSave, Toast.LENGTH_SHORT)
				.show();
		String myPath = Environment.getExternalStorageDirectory()
				+ File.separator + "DCIM/ImgAppCrop";
		File fileMyPath = new File(myPath, name + ".jpg");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileMyPath);
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			MediaScannerConnection.scanFile(getActivity(),
					new String[] { fileMyPath.getPath() },
					new String[] { "image/jpeg" }, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}