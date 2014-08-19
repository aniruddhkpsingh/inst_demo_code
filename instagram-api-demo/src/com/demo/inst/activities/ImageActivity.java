package com.demo.inst.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.inst.R;
import com.demo.inst.libproj.InstagramUser;
import com.demo.inst.utils.Constants;

public class ImageActivity extends Activity {
	private TextView tvUserName;
	private TextView tvAboutImage;
	private ImageView ivInstagramImage;
	private ProgressBar pbImageLoder;

	private ImageLoadTask mImageLoaderTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_screen);
		mImageLoaderTask = new ImageLoadTask();
		initUI();
		inflateUI();

	}

	private void initUI() {
		tvUserName = (TextView) findViewById(R.id.tv_user_name);
		tvAboutImage = (TextView) findViewById(R.id.tv_summary);
		ivInstagramImage = (ImageView) findViewById(R.id.iv_instagram_image);
		pbImageLoder = (ProgressBar) findViewById(R.id.pb_loader);
		pbImageLoder.bringToFront();
	}

	private void inflateUI() {
		Intent intent = getIntent();
		InstagramUser user = (InstagramUser) intent
				.getSerializableExtra(Constants.USER);
		if (user != null) {
			tvUserName.setText(user.getName());
			tvAboutImage.setText(user.getCaption());
			loadImage(user.getImgUrl());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mImageLoaderTask.cancel(true);
	}

	public void loadImage(String imgUrl) {
		if (imgUrl != null && !imgUrl.equals("")) {
			mImageLoaderTask.execute(imgUrl);
		}
	}

	// ASYNC TASK to avoid UI lags
	private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

		@Override
		protected void onPreExecute() {
			Log.i("ImageLoadTask", "Loading image...");
		}

		// param[0] is img url
		protected Bitmap doInBackground(String... param) {
			Log.i("ImageLoadTask", "Attempting to load image URL: " + param[0]);
			try {
				Bitmap b = getBitmapFromURL(param[0]);
				return b;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onProgressUpdate(String... progress) {
			// NO OP
		}

		protected void onPostExecute(Bitmap ret) {
			if (ret != null) {
				ivInstagramImage.setImageBitmap(ret);
			} else {
				Toast.makeText(getApplicationContext(),
						getString(R.string.bitmap_error_string),
						Toast.LENGTH_SHORT).show();
			}
			pbImageLoder.setVisibility(View.GONE);
		}
	}

	public Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
