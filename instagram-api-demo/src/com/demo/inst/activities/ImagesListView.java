package com.demo.inst.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.demo.inst.adapters.CustomListAdapter;
import com.demo.inst.beans.CustomUser;
import com.demo.inst.libproj.InstagramUser;
import com.demo.inst.utils.Constants;

public class ImagesListView extends ListActivity {
	private ArrayList<CustomUser> customUsersList = new ArrayList<CustomUser>();

	private CustomListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		@SuppressWarnings("unchecked")
		ArrayList<InstagramUser> userList = (ArrayList<InstagramUser>) getIntent()
				.getSerializableExtra(Constants.USERS_LIST);
		for (InstagramUser u : userList) {
			Log.d("InstaImages", "filter2 uname: " + u.getName() + ", url: "
					+ u.getImgUrl() + ", caption: " + u.getCaption());
			customUsersList.add(new CustomUser(u));
		}

		// CREATE BASE ADAPTER
		mAdapter = new CustomListAdapter(ImagesListView.this, customUsersList);

		// SET AS CURRENT LIST
		setListAdapter(mAdapter);

		for (CustomUser s : customUsersList) {
			// START LOADING IMAGES FOR EACH STUDENT
			s.loadImage(mAdapter);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		CustomUser user = mAdapter.getItem(position);
		Intent imageActivityIntent = new Intent(ImagesListView.this,
				ImageActivity.class);
		InstagramUser instUser = new InstagramUser(user.getName(),
				user.getImgUrl(), user.getCaption(), user.getThumbnailUrl());
		imageActivityIntent.putExtra(Constants.USER, instUser);
		startActivity(imageActivityIntent);
	}
}
