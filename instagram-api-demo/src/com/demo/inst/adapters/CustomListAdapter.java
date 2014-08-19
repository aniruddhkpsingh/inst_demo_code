package com.demo.inst.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.inst.R;
import com.demo.inst.beans.CustomUser;
import com.demo.inst.utils.Constants;

public class CustomListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private List items = new ArrayList();

	public CustomListAdapter(Context context, List items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	public int getCount() {
		return items.size();
	}

	public CustomUser getItem(int position) {
		return (CustomUser) items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		CustomUser s = (CustomUser) items.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_layout, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.textView1);
			holder.pic = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position % 3 == 0) {
			holder.pic.getLayoutParams().height = Constants.THUMBNAIL_SIZE;
			holder.pic.getLayoutParams().width = Constants.THUMBNAIL_SIZE;
		} else {
			holder.pic.getLayoutParams().height = Constants.THUMBNAIL_SIZE / 2;
			holder.pic.getLayoutParams().width = Constants.THUMBNAIL_SIZE / 2;
		}
		holder.pic.requestLayout();
		holder.name.setText(s.getName());
		if (s.getImage() != null) {
			holder.pic.setImageBitmap(s.getImage());
		} else {
			// MY DEFAULT IMAGE
			holder.pic.setImageResource(R.drawable.user_thumb);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView name;
		ImageView pic;
	}

}