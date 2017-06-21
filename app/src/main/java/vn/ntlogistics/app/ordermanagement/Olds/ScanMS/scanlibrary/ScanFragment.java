package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by jhansi on 29/03/15.
 */
public class ScanFragment extends Fragment {

	private Button scanButton, btnNoScan;
	private ImageView sourceImageView;
	private FrameLayout sourceFrame;
	private PolygonView polygonView;

	private LinearLayout lnOK, lnNo;

	private View view;
	private ProgressDialogFragment progressDialogFragment;
	private IScanner scanner;
	private Bitmap original;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof IScanner)) {
			throw new ClassCastException("Activity must implement IScanner");
		}
		this.scanner = (IScanner) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.scan_fragment_layout, null);
		init();
		return view;
	}

	public ScanFragment() {

	}

	private void init() {
		sourceImageView = (ImageView) view.findViewById(R.id.sourceImageView);
		scanButton = (Button) view.findViewById(R.id.scanButton);
		btnNoScan = (Button) view.findViewById(R.id.btnNoScan);

		lnNo = (LinearLayout) view.findViewById(R.id.linearCancel);
		lnOK = (LinearLayout) view.findViewById(R.id.linearOk);

		// btnNoScan.setOnClickListener(new NoScanClickListener());

		btnNoScan.setOnTouchListener(new NoScanPressListener());

		// scanButton.setOnClickListener(new ScanButtonClickListener());
		scanButton.setOnTouchListener(new YesScanPressListener());

		sourceFrame = (FrameLayout) view.findViewById(R.id.sourceFrame);
		polygonView = (PolygonView) view.findViewById(R.id.polygonView);
		sourceFrame.post(new Runnable() {
			@Override
			public void run() {
				original = getBitmap();

				//original = changeContrast(original, 2.3f, -100);

				if (original != null) {
					//setBitmap(original);
				}
			}
		});
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

	private Bitmap getBitmap() {
		Uri uri = getUri();
		try {
			Bitmap bitmap = Utils.getBitmap(getActivity(), uri);
			getActivity().getContentResolver().delete(uri, null, null);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Uri getUri() {
		Uri uri = getArguments().getParcelable(ScanConstants.SELECTED_BITMAP);
		return uri;
	}

	/*private void setBitmap(Bitmap original) {
		Bitmap scaledBitmap = scaledBitmap(original, sourceFrame.getWidth(),
				sourceFrame.getHeight());

		// Bitmap lastBit = resizeImageToShow(scaledBitmap);

		sourceImageView.setImageBitmap(scaledBitmap);
		Bitmap tempBitmap = ((BitmapDrawable) sourceImageView.getDrawable())
				.getBitmap();
		Map<Integer, PointF> pointFs = getEdgePoints(tempBitmap);
		polygonView.setPoints(pointFs);
		polygonView.setVisibility(View.VISIBLE);
		int padding = (int) getResources().getDimension(R.dimen.scanPadding);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				tempBitmap.getWidth() + 2 * padding, tempBitmap.getHeight() + 2
						* padding);
		layoutParams.gravity = Gravity.CENTER;
		polygonView.setLayoutParams(layoutParams);
	}*/

	/*private Map<Integer, PointF> getEdgePoints(Bitmap tempBitmap) {
		// List<PointF> pointFs = getContourEdgePoints(tempBitmap);

		List<PointF> pointFs = getCornerByGiao(tempBitmap);

		// List<PointF> pointFs = getConnerByHughLineP(tempBitmap);

		// List<PointF> pointFs = getConnerByHughLineVer2(tempBitmap);

		Map<Integer, PointF> orderedPoints = orderedValidEdgePoints(tempBitmap,
				pointFs);
		return orderedPoints;
	}*/

	/*private List<PointF> getConnerByHughLineVer2(Bitmap tempBitmap) {
		// Blur the image to filter out the noise.
		Mat img = ImageUtils.bitmapToMat(tempBitmap);
		float width = (float) img.size().width;
		float height = (float) img.size().height;
		Mat blurred = new Mat();
		Imgproc.medianBlur(img, blurred, 9);

		// Set up images to use.
		Mat gray0 = new Mat(blurred.size(), CvType.CV_8U);
		Mat gray = new Mat();

		List<Mat> sources = new ArrayList<Mat>();
		sources.add(blurred);
		List<Mat> destinations = new ArrayList<Mat>();
		destinations.add(gray0);

		// To filter rectangles by their areas.
		Mat lines = new Mat();
		List<PointF> lsPoint = new ArrayList<>();
		// Find squares in every color plane of the image.
		for (int c = 0; c < 3; c++) {
			int[] ch = { c, 0 };
			MatOfInt fromTo = new MatOfInt(ch);

			Core.mixChannels(sources, destinations, fromTo);

			// Try several threshold levels.
			for (int l = 0; l < 5; l++) {
				if (l == 0) {

					Imgproc.Canny(gray0, gray, 0, 50);
					Imgproc.dilate(gray, gray, Mat.ones(new Size(5, 5), 0));

				} else {
					int threshold = (l + 1) * 255 / 5;
					Imgproc.threshold(gray0, gray, threshold, 255,
							Imgproc.THRESH_BINARY);
				}
			}

			Imgproc.HoughLinesP(gray, lines, 1, Math.PI / 180, 50, 50, 10);

		}

		for (int i = 0; i < lines.cols(); i++) {
			for (int j = i + 1; j < lines.cols(); j++) {
				PointF mFPoint = findIntersection(lines.get(0, i),
						lines.get(0, j));
				if (mFPoint.x >= 0 && mFPoint.y >= 0) {
					lsPoint.add(mFPoint);
				}

			}

		}
		Log.d("DÒNG",
				"Số cột: " + lines.cols() + ", Số dòng: " + lines.rows()
						+ ", Size: " + lines.size());

		if (lsPoint.size() != 4) {
			lsPoint.clear();
			lsPoint.add(new PointF(40, 110));
			lsPoint.add(new PointF(width - 40, 110));
			lsPoint.add(new PointF(40, height - 110));
			lsPoint.add(new PointF(width - 40, height - 110));
			return lsPoint;

		} else {
			return lsPoint;
		}

	}

	private List<PointF> getConnerByHughLineP(Bitmap tempBitmap) {
		Mat img = ImageUtils.bitmapToMat(tempBitmap);
		float width = (float) img.size().width;
		float height = (float) img.size().height;
		// generate gray scale and blur
		Mat gray = new Mat();
		Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.blur(gray, gray, new Size(3, 3));

		// detect the edges
		Mat edges = new Mat();
		int lowThreshold = 50;
		int ratio = 3;

		Imgproc.Canny(gray, edges, lowThreshold, lowThreshold * ratio);
		Mat lines = new Mat();
		Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180, 50, 50, 10);

		List<PointF> lsPoint = new ArrayList<>();
		Log.d("DÒNG",
				"Số cột: " + lines.cols() + ", Số dòng: " + lines.rows()
						+ ", Size: " + lines.size());
		for (int i = 0; i < lines.cols(); i++) {
			for (int j = i + 1; j < lines.cols(); j++) {
				PointF mFPoint = findIntersection(lines.get(0, i),
						lines.get(0, j));
				if (mFPoint.x >= 0 && mFPoint.y >= 0) {
					lsPoint.add(mFPoint);
				}

			}

		}
		if (lsPoint.size() != 4) {
			lsPoint.clear();
			lsPoint.add(new PointF(40, 110));
			lsPoint.add(new PointF(width - 40, 110));
			lsPoint.add(new PointF(40, height - 110));
			lsPoint.add(new PointF(width - 40, height - 110));
			return lsPoint;

		} else {
			return lsPoint;
		}

	}

	private PointF findIntersection(double[] a, double[] b) {
		double x1 = a[0], y1 = a[1], x2 = a[2], y2 = a[3];
		double x3 = b[0], y3 = b[1], x4 = b[2], y4 = b[3];

		float d = (float) ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

		Log.d("d", d + "");

		Point mPoint = new Point();
		PointF mFPoint;
		if (d != 0) {
			mPoint.x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2)
					* (x3 * y4 - y3 * x4))
					/ d;
			mPoint.y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2)
					* (x3 * y4 - y3 * x4))
					/ d;

			mFPoint = new PointF((float) mPoint.x, (float) mPoint.y);
			return mFPoint;

		} else {
			mPoint.x = -1;
			mPoint.y = -1;
			mFPoint = new PointF((float) mPoint.x, (float) mPoint.y);
			return mFPoint;
		}

	}

	private List<PointF> getCornerByGiao(Bitmap tempBitmap) {
		float x1, x2, x3, x4, y1, y2, y3, y4;
		List<PointF> pointFs = new ArrayList<>();
		// Create OpenCV mat from the bitmap
		Mat srcMat = ImageUtils.bitmapToMat(tempBitmap);
		float width = (float) srcMat.size().width;
		float height = (float) srcMat.size().height;

		// Find the largest rectangle
		// Find image views
		RectFinder rectFinder = new RectFinder(0.2, 0.98);
		MatOfPoint2f rectangle = rectFinder.findRectangle(srcMat);

		if (rectangle == null) {
			Toast.makeText(getActivity(), R.string.notRetacleg,
					Toast.LENGTH_SHORT).show();
			pointFs.add(new PointF(40, 110));
			pointFs.add(new PointF(width - 40, 110));
			pointFs.add(new PointF(40, height - 110));
			pointFs.add(new PointF(width - 40, height - 110));
			return pointFs;
		}

		PerspectiveTransformation perspective = new PerspectiveTransformation();
		Point[] points = perspective.getByGiao(rectangle);

		x1 = (float) points[0].x;
		x2 = (float) points[1].x;
		x3 = (float) points[2].x;
		x4 = (float) points[3].x;

		y1 = (float) points[0].y;
		y2 = (float) points[1].y;
		y3 = (float) points[2].y;
		y4 = (float) points[3].y;

		pointFs.add(new PointF(x1, y1));
		pointFs.add(new PointF(x2, y2));
		pointFs.add(new PointF(x3, y3));
		pointFs.add(new PointF(x4, y4));

		return pointFs;

	}*/

	private List<PointF> getContourEdgePoints(Bitmap tempBitmap) {
		float[] points = ((ScanActivity) getActivity()).getPoints(tempBitmap);
		float x1 = points[0];
		float x2 = points[1];
		float x3 = points[2];
		float x4 = points[3];

		float y1 = points[4];
		float y2 = points[5];
		float y3 = points[6];
		float y4 = points[7];

		List<PointF> pointFs = new ArrayList<>();
		pointFs.add(new PointF(x1, y1));
		pointFs.add(new PointF(x2, y2));
		pointFs.add(new PointF(x3, y3));
		pointFs.add(new PointF(x4, y4));
		return pointFs;
	}

	private Map<Integer, PointF> getOutlinePoints(Bitmap tempBitmap) {
		Map<Integer, PointF> outlinePoints = new HashMap<>();
		outlinePoints.put(0, new PointF(0, 0));
		outlinePoints.put(1, new PointF(tempBitmap.getWidth(), 0));
		outlinePoints.put(2, new PointF(0, tempBitmap.getHeight()));
		outlinePoints.put(3,
				new PointF(tempBitmap.getWidth(), tempBitmap.getHeight()));
		return outlinePoints;
	}

	private Map<Integer, PointF> orderedValidEdgePoints(Bitmap tempBitmap,
			List<PointF> pointFs) {
		Map<Integer, PointF> orderedPoints = polygonView
				.getOrderedPoints(pointFs);
		if (!polygonView.isValidShape(orderedPoints)) {
			orderedPoints = getOutlinePoints(tempBitmap);
		}
		return orderedPoints;
	}

	private class ScanButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {

		}
	}

	private class NoScanClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			exitApp();
		}

	}

	private class NoScanPressListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// PRESSED
				lnNo.setBackgroundColor(Color.parseColor("#aebbc7"));
				return true; // if you want to handle the touch event
			case MotionEvent.ACTION_UP:
				// RELEASED
				lnNo.setBackgroundColor(Color.parseColor("#111111"));
				exitApp();
				return true; // if you want to handle the touch event
			}

			return false;
		}
	}

	private class YesScanPressListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// PRESSED
				lnOK.setBackgroundColor(Color.parseColor("#aebbc7"));
				return true; // if you want to handle the touch event
			case MotionEvent.ACTION_UP:
				// RELEASED
				lnOK.setBackgroundColor(Color.parseColor("#111111"));
				Map<Integer, PointF> points = polygonView.getPoints();
				if (isScanPointsValid(points)) {
					new ScanAsyncTask(points).execute();

				} else {
					showErrorDialog();
				}
				return true; // if you want to handle the touch event
			}

			return false;
		}
	}

	private void exitApp() {

		Intent it = new Intent();
		it.setComponent(new ComponentName("com.appautocrop", "com.scan.ScanMS"));
		Bundle a = new Bundle();
		a.putInt("fromMyCrop", 9);
		it.putExtras(a);
		startActivity(it);
		getActivity().finish();
	}

	private void showErrorDialog() {
		SingleButtonDialogFragment fragment = new SingleButtonDialogFragment(
				R.string.ok, getString(R.string.cantCrop), "Error", true);
		FragmentManager fm = getActivity().getFragmentManager();
		fragment.show(fm, SingleButtonDialogFragment.class.toString());
	}

	private boolean isScanPointsValid(Map<Integer, PointF> points) {
		return points.size() == 4;
	}

	private Bitmap scaledBitmap(Bitmap bitmap, int width, int height) {
		Matrix m = new Matrix();
		m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()),
				new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), m, true);
	}

	private Bitmap getScannedBitmap(Bitmap original, Map<Integer, PointF> points) {
		int width = original.getWidth();
		int height = original.getHeight();
		float xRatio = (float) original.getWidth() / sourceImageView.getWidth();
		float yRatio = (float) original.getHeight()
				/ sourceImageView.getHeight();

		float x1 = (points.get(0).x) * xRatio;
		float x2 = (points.get(1).x) * xRatio;
		float x3 = (points.get(2).x) * xRatio;
		float x4 = (points.get(3).x) * xRatio;
		float y1 = (points.get(0).y) * yRatio;
		float y2 = (points.get(1).y) * yRatio;
		float y3 = (points.get(2).y) * yRatio;
		float y4 = (points.get(3).y) * yRatio;
		Log.d("", "POints(" + x1 + "," + y1 + ")(" + x2 + "," + y2 + ")(" + x3
				+ "," + y3 + ")(" + x4 + "," + y4 + ")");
		Bitmap _bitmap = ((ScanActivity) getActivity()).getScannedBitmap(
				original, x1, y1, x2, y2, x3, y3, x4, y4);
		return _bitmap;
	}

	private class ScanAsyncTask extends AsyncTask<Void, Void, Bitmap> {

		private Map<Integer, PointF> points;

		public ScanAsyncTask(Map<Integer, PointF> points) {
			this.points = points;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog(getResources().getString(R.string.catting));
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			return getScannedBitmap(original, points);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			dismissDialog();
			Uri uri = Utils.getUri(getActivity(), bitmap);
			bitmap.recycle();
			scanner.onScanFinish(uri);
		}
	}

	protected void showProgressDialog(String message) {
		progressDialogFragment = new ProgressDialogFragment(message);
		FragmentManager fm = getFragmentManager();
		progressDialogFragment
				.show(fm, ProgressDialogFragment.class.toString());
	}

	protected void dismissDialog() {
		progressDialogFragment.dismissAllowingStateLoss();
	}

}