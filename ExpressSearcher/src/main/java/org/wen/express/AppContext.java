package org.wen.express;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import org.wen.express.common.HardwareUtility;

/**
 * Created by wenjiahui on 8/27/13.
 */
public class AppContext extends Application{

    private static Context applicationContext;

    /**judge whether the device is pad or phone*/
    private static boolean isTablet = false;

    public static Context getInstance() {
        return applicationContext;
    }

    public static boolean isTablet() {
        return isTablet;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        isTablet = HardwareUtility.isTabletDevice(applicationContext);
    }


}