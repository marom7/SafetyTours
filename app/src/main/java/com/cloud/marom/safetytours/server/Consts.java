package com.cloud.marom.safetytours.server;

public class Consts {
    public static final String BASE_URL = "https://wszimunforcustomer.ashdodport.co.il/Iport/IportWs.asmx";


    public static String getFullUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static final String PREF_FILE_NAME="pref_file_name";
    public static final String PREF_REGISTRATION_ID="registration_id";
    public static final String PREF_REGISTRATION_SENT="registration_sent";
    public static final String PREF_MESSAGE="message_file_name";
    public static final String MESSAGES="MESSAGES";
    public static final String LMESSAGES="ALL_MESSAGES";
}
