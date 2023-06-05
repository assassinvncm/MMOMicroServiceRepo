package com.mmo.microservice.token.util;

public class URLRequestConstant {
    public static final String GET_PAGE_ACCESS_TOKEN = "https://graph.facebook.com/v16.0/me/accounts";
    public static final String GET_PAGE_ACCESS_TOKEN_PAGE_ID = "https://graph.facebook.com/v16.0/";
    public static final String GET_EXCHANGE_ACCESS_TOKEN = "https://graph.facebook.com/v16.0/oauth/access_token";
    public static final String[] GET_PAGE_ACCESS_TOKEN_PARAMS = {"access_token"};
    public static final String POST_CREATE_PUBLISH_REEL = "https://graph.facebook.com/v16.0/me/video_reels";
    public static final String POST_UPLOAD_LOCAL_REEL = "https://rupload.facebook.com/video-upload/v16.0/";
    public static final String GET_UPLOAD_STATUS_REEL = "https://graph.facebook.com/v16.0/";
    public static final String GET_ID_FB_USER_I4 = "https://graph.facebook.com/v16.0/me";
}
