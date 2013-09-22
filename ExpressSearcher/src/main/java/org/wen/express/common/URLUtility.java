package org.wen.express.common;

/**
 * Created by wenjiahui on 9/22/13.
 */
public class URLUtility {

    public static final String BASE_URL = "http://www.kuaidi100.com/query?type=COMPANY&postid=CODE";

    private URLUtility() {

    }

    public static String getURL(String company, String code) {
        String url = new String(BASE_URL);
        url = url.replace("COMPANY", company);
        url = url.replace("CODE", code);
        AppLogger.d("URL: " + url);
        return url;
    }
}
