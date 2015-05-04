package org.wen.android.express;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.wen.android.express.common.AppLogger;
import org.wen.android.express.common.Cache;
import org.wen.android.express.common.HardwareUtility;
import org.wen.android.express.type.AppConstant;

/**
 * Created by wenjiahui on 8/27/13.
 */
public class AppContext extends Application{

    private static Context applicationContext;

    private Cache mCache;

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

        mCache = Cache.get(this);

        if (!checkDBExist()) {
            copyDB();
        } else if (upgradeApp()) {
            deletePreDatabase();
            copyDB();
            SharedPreferences.Editor editor = getSharedPreferences(this.getPackageName(), MODE_PRIVATE).edit();
            int version = getCurrentVersion();
            editor.putInt(AppConstant.APP_VERSION, version);
            editor.commit();
        }
    }

    public void saveCache(String key, String cache) {
        mCache.put(key, cache, 60*60);
    }

    public String getCache(String key) {
        return mCache.getAsString(key);
    }

    /**
     * 程序首次启动会从raw文件夹中负责数据库到指定目录，这里判断数据库是否存在
     * 若不存在，则进行数据库复制操作
     * @return false数据库不存在， true存在
     */
    private boolean checkDBExist() {

        String dbDir = "data/data/" + this.getPackageName() + "/databases";
        File dir = new File(dbDir);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        File dbFile = new File(dir, "company.db");
        if (!dbFile.exists()) {
            AppLogger.d("database is not exist");
            return false;
        }
        AppLogger.d("database already exist");
        return true;
    }

    private void deletePreDatabase() {
        String target = "data/data/" + this.getPackageName() + "/databases/company.db";
        File targetFile = new File(target);
        try {
            targetFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查应用版本号是否升级
     * @return true应用升级
     */
    public boolean upgradeApp() {
        int preVersionCode = getSharedPreferences(getPackageName(), MODE_PRIVATE).getInt(AppConstant.APP_VERSION, 0);
        int currentVersionCode = getCurrentVersion();
        if (currentVersionCode > preVersionCode) {
            return true;
        }
        return false;
    }


    /**
     * 获取当前应用的版本号
     * @return
     */
    public int getCurrentVersion() {
        int version = 0;
        try {
            PackageInfo packageInfo =  getPackageManager().getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * 数据库复制操作
     */
    private void copyDB() {
        String target = "data/data/" + this.getPackageName() + "/databases/company.db";
        File targetFile = new File(target);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fos == null) {
            Toast.makeText(this, R.string.copy_db_error, Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }

        InputStream is = getResources().openRawResource(R.raw.company);
        byte[] temp = new byte[1024];
        try {
            while (is.read(temp) != -1) {
                fos.write(temp);
            }
            fos.flush();
            AppLogger.d("finished copy the database");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           if (fos != null) {
               try {
                   fos.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } // end finally

    } // end function copyDB()
}