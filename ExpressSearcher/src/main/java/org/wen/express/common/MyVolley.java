package org.wen.express.common;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.wen.express.AppContext;

/**
 * Created by wenjiahui on 9/21/13.
 */
public class MyVolley {

    private static RequestQueue requestQueue = Volley.newRequestQueue(AppContext.getInstance());

    private MyVolley() {

    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
}
