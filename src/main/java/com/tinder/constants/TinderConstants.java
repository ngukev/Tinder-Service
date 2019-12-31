package com.tinder.constants;

public interface TinderConstants {
    public static final String BASE_URL = "https://api.gotinder.com/v2/";
    public static final String RECS = "recs/core?locale=en";
    public static final String TEASER = "fast-match/teasers?locale=en";
    public static final String PROFILE = "profile?include=account%2Cboost%2Ccontact_cards%2Cemail_settings%2C" +
            "instagram%2Clikes%2Cnotifications%2Cplus_control%2Cproducts%2Cpurchase%2Creadreceipts%2Cspotify%2C" +
            "super_likes%2Ctinder_u%2Ctravel%2Ctutorials%2Cuser&locale=en";

    public static final String PASS = "pass/{USER_ID}?locale=en&s_number={S_NUMBER}";
    public static final String LIKE = "like/{USER_ID}?locale=en&s_number={S_NUMBER}";
    public static final String INITIAL_LOGIN = "auth/sms/send?auth_type=sms";
    public static final String LOGIN_VALIDATION = "auth/sms/validate?auth_type=sms";
    public static final String RETRIEVE_SMS_TOKEN = "auth/login/sms?locale=en";

}