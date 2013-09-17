package org.wen.express.common;

import android.content.Context;

import org.wen.express.R;

/**
 * Created by wenjiahui on 9/3/13.
 */
public class HardwareUtility {

    public static boolean isTabletDevice(Context context) {
        boolean flag = context.getResources().getBoolean(R.bool.isTablet);
        return flag;
    }

}
