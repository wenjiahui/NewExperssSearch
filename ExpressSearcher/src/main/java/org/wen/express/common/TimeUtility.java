package org.wen.express.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenjiahui on 9/24/13.
 */
public class TimeUtility {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getNowTime() {
        return formatter.format(new Date());
    }
}
