package com.demo.inst.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.inst.R;
import com.demo.inst.libproj.InstagramApp;
import com.demo.inst.libproj.InstagramApp.OAuthAuthenticationListener;
import com.demo.inst.libproj.InstagramUser;
import com.demo.inst.utils.Constants;

public class MainActivity extends Activity {

	private InstagramApp mApp;
	private Button btnConnect;
	private TextView tvSummary;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		mApp = new InstagramApp(this, Constants.CLIENT_ID,
				Constants.CLIENT_SECRET, Constants.CALLBACK_URL);
		mApp.setListener(listener);

		tvSummary = (TextView) findViewById(R.id.tvSummary);

		btnConnect = (Button) findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mApp.hasAccessToken()) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setMessage("Disconnect from Instagram?")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											mApp.resetAccessToken();
											btnConnect.setText("Connect");
											tvSummary.setText("Not connected");
										}
									})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});
					final AlertDialog alert = builder.create();
					alert.show();
				} else {
					mApp.authorize();
				}
			}
		});

		if (mApp.hasAccessToken()) {
			tvSummary.setText("Connected as " + mApp.getUserName());
			btnConnect.setText("Disconnect");
		}

	}

	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			tvSummary.setText("Connected as " + mApp.getUserName());
			btnConnect.setText("Disconnect");
			// Intent launch = new Intent(MainActivity.this, InstaImages.class);
			// ArrayList<User> usrlist = mApp.fetchImageURL();
			new FetchUrlTask().execute();
		}

		@Override
		public void onFail(String error) {
			Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
		}
	};

	private class FetchUrlTask extends
			AsyncTask<String, String, ArrayList<InstagramUser>> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ArrayList<InstagramUser> doInBackground(String... param) {
			ArrayList<InstagramUser> usrlist = mApp.fetchImageURL();
			return usrlist;
		}

		protected void onProgressUpdate(String... progress) {
			// nothing to do now
		}

		@Override
		protected void onPostExecute(ArrayList<InstagramUser> usrlist) {
			Intent launch = new Intent(MainActivity.this, ImagesListView.class);
			ArrayList<String> urls = new ArrayList<String>();
			if (usrlist != null) {
				for (InstagramUser u : usrlist) {
					urls.add(u.getImgUrl());
					Log.d("MainActivity",
							"filter2 uname: " + u.getName() + ", url: "
									+ u.getImgUrl() + ", caption: "
									+ u.getCaption());
				}
				launch.putExtra(Constants.USERS_LIST,
						(ArrayList<InstagramUser>) usrlist);
			} else {
				Toast.makeText(MainActivity.this,
						getString(R.string.user_error_string),
						Toast.LENGTH_SHORT).show();
			}
			startActivity(launch);
		}

	}

}