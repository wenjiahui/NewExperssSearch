package org.wen.android.express.common;

import com.google.gson.Gson;

/**
 * Created by wenjiahui on 9/25/13.
 */
public class GsonUtility {
    private static Gson gson = new Gson();

    public static Gson getGson() {
        synchronized (gson) {
            return gson;
        }
    }
}
