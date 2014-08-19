package com.demo.inst.utils;

public class Constants {

	public static final String CLIENT_ID = "60ebb77689cf4e83a4377ca800c88df9";
	public static final String CLIENT_SECRET = "b6298df3991d4a23b352b0b3dc900d53";
	public static final String WEBSITE_URL = "http://instagram.com/aniruddh.singh.798";
	public static final String REDIRECT_URI = "http://instagram.com/aniruddh.singh.798";
	@SuppressWarnings("unused")
	private static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";

	// Used for Authentication.
	@SuppressWarnings("unused")
	private static final String TOKENURL = "https://api.instagram.com/oauth/access_token";
	// Used for getting token and User details.
	public static final String APIURL = "https://api.instagram.com/v1";
	// Used to specify the API version which we are going to use.
	public static String CALLBACK_URL = REDIRECT_URI;

	// UI constants : IMAGE SIZES SENT BY INSTAGRAM SERVER
	public static final int THUMBNAIL_SIZE = 150;
	public static final int LOW_RESOLUTION_IMAGE_SIZE = 306;
	public static final int HIGH_RESOLUTION_IMAGE_SIZE = 640;

	public static final String USERS_LIST = "usersList";
	public static final String USER = "user";
}
