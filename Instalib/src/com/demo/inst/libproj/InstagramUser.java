package com.demo.inst.libproj;

import java.io.Serializable;

import android.graphics.Bitmap;

public class InstagramUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String imgUrl;

	private Bitmap image;

	private String caption;

	private String thumbnailUrl;

	public InstagramUser(String name, String imgUrl, String caption,
			String thumbnailUrl) {
		this.name = name;
		this.imgUrl = imgUrl;
		this.caption = caption;
		this.thumbnailUrl = thumbnailUrl;
		// TO BE LOADED LATER - OR CAN SET TO A DEFAULT IMAGE
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnail) {
		this.thumbnailUrl = thumbnail;
	}
}
