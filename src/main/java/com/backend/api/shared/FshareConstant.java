package com.backend.api.shared;

import com.backend.api.dtos.fshare.LoginRequest;

public class FshareConstant {
    public static final String EMAIL = "theanh2906@gmail.com";
    public static final String APP_KEY = "dMnqMMZMUnN5YpvKENaEhdQQ5jxDqddt";
    public static final String USER_AGENT = "UsefulTools2022-710L5E";
    public static final String PASSWORD = "Jackytang2906*";
    public static final String FSHARE_API_URL = "https://api.fshare.vn/api";
    public static final String LOGIN_URL = FSHARE_API_URL + "/user/login";
    public static final String LOGOUT_URL = FSHARE_API_URL + "/user/logout";
    public static final String DELETE_TOKEN_URL = "https://www.fshare.vn/account/delete-token";
    public static final String USER_INFO_URL = FSHARE_API_URL + "/user/get";
    public static final String UPLOAD_URL = FSHARE_API_URL + "/session/upload";
    public static final LoginRequest LOGIN_REQUEST = new LoginRequest(EMAIL, PASSWORD, APP_KEY);
}
