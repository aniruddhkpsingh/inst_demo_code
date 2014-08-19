package com.demo.inst.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.demo.inst.adapters.CustomListAdapter;
import com.demo.inst.libproj.InstagramUser;

public class CustomUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String imgUrl;

	transient private Bitmap image;

	private String caption;

	private CustomListAdapter sta;

	private String thumbnailUrl;

	public CustomUser(InstagramUser instaUser) {
		if (instaUser != null) {
			this.name = instaUser.getName();
			this.imgUrl = instaUser.getImgUrl();
			this.setCaption(instaUser.getCaption());
			this.setThumbnailUrl(instaUser.getThumbnailUrl());
		} else {
			// nothing here
		}
		this.image = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Bitmap getImage() {
		return image;
	}

	public CustomListAdapter getAdapter() {
		return sta;
	}

	public void setAdapter(CustomListAdapter sta) {
		this.sta = sta;
	}

	public void loadImage(CustomListAdapter sta) {
		// HOLD A REFERENCE TO THE ADAPTER
		this.sta = sta;
		if (thumbnailUrl != null && !thumbnailUrl.equals("")) {
			new ImageLoadTask().execute(thumbnailUrl);
		}
	}

	// ASYNC TASK TO AVOID CHOKING UP UI THREAD
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
				Log.i("ImageLoadTask", "Successfully loaded " + name + " image");
				image = ret;
				if (sta != null) {
					// WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
					sta.notifyDataSetChanged();
				}
			} else {
				Log.e("ImageLoadTask", "Failed to load " + name + " image");
			}
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

}
