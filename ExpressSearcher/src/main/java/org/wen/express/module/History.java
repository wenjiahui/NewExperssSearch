package org.wen.express.module;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TimeUtils;

import org.wen.express.AppContext;
import org.wen.express.common.AppLogger;
import org.wen.express.common.TimeUtility;
import org.wen.express.provider.DataProvider;
import org.wen.express.type.AppConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenjiahui on 9/22/13.
 */
public class History {

    public int _id;
    public String updateTime;
    public String queryTime;
    public String company_cn;
    public String company_en;
    public String tag;
    public String code;
    public String details;

    public History() {

    }

    public History(Result response) {
        if (response == null) {
            return;
        }
        this.updateTime = response.getUpdatetime();
        queryTime = TimeUtility.getNowTime();
        company_en = response.getCom();
        code = response.getNu();
    }

    public static class TABLE_PARAMS {

        public static String TABLE_NAME = "historys";
        public static Uri CONTENT_BASE_URI = Uri.parse(DataProvider.AUTHORITY + TABLE_NAME + "/");

        public static String COLUMN_ID = "_id";
        public static String COLUMN_UPDATE_TIME = "updateTime";
        public static String COLUMN_QUERY_TIME = "queryTime";
        public static String COLUMN_COMPANY_CN = "company_cn";
        public static String COLUMN_COMPANY_EN = "company_en";
        public static String COLUMN_TAG = "tag";
        public static String COLUMN_CODE = "code";
        public static String COLUMN_DETAILS = "details";

        public static String CREATE_TABLE_SQL = "create table "
               + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
                                  + COLUMN_CODE + ", "
                                  + COLUMN_COMPANY_CN + ", "
                                  + COLUMN_COMPANY_EN + ", "
                                  + COLUMN_UPDATE_TIME + ", "
                                  + COLUMN_QUERY_TIME + ", "
                                  + COLUMN_DETAILS + ", "
                                  + COLUMN_TAG + ")" ;

        public static final Map<String, String> mProjection;
        static {
            mProjection = new HashMap<String, String>();
            mProjection.put(COLUMN_ID, COLUMN_ID);
            mProjection.put(COLUMN_CODE, COLUMN_CODE);
            mProjection.put(COLUMN_COMPANY_CN, COLUMN_COMPANY_CN);
            mProjection.put(COLUMN_COMPANY_EN, COLUMN_COMPANY_EN);
            mProjection.put(COLUMN_QUERY_TIME, COLUMN_QUERY_TIME);
            mProjection.put(COLUMN_TAG, COLUMN_TAG);
            mProjection.put(COLUMN_UPDATE_TIME, COLUMN_UPDATE_TIME);
            mProjection.put(COLUMN_DETAILS, COLUMN_DETAILS);
        }
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public void setCompany_cn(String company_cn) {
        this.company_cn = company_cn;
    }

    public void setCompany_en(String company_en) {
        this.company_en = company_en;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public static void saveOrUpdate(History history) {
        if( history == null) {
            AppLogger.d("history is null , now return");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(TABLE_PARAMS.COLUMN_UPDATE_TIME, history.updateTime.toString());
        values.put(TABLE_PARAMS.COLUMN_QUERY_TIME, history.queryTime.toString());
        values.put(TABLE_PARAMS.COLUMN_DETAILS, history.details);
        if(!TextUtils.isEmpty(history.tag)) {
            values.put(TABLE_PARAMS.COLUMN_TAG, history.tag);
        }

        SQLiteDatabase db = new DataProvider.DBHelper(AppContext.getInstance()).getWritableDatabase();
        AppLogger.d("the history : " + history.toString());
        long rowid = db.update(TABLE_PARAMS.TABLE_NAME, values, TABLE_PARAMS.COLUMN_CODE + " = '" + history.code + "'", null);
        AppLogger.d("update action result: " + rowid);
        if(rowid <= 0) {
            AppLogger.d("the search record is new, begin to insert into database");
            //new record, need to take insert action for persitence
            values.put(TABLE_PARAMS.COLUMN_COMPANY_EN, history.company_en);
            values.put(TABLE_PARAMS.COLUMN_COMPANY_CN, history.company_cn);
            values.put(TABLE_PARAMS.COLUMN_CODE, history.code);
            rowid = db.insert(TABLE_PARAMS.TABLE_NAME, TABLE_PARAMS.COLUMN_CODE, values);
        }
        if(rowid > 0) {
            AppLogger.d("persistent the record succeffully");
            Uri uri = ContentUris.withAppendedId(TABLE_PARAMS.CONTENT_BASE_URI, rowid);
            AppContext.getInstance().getContentResolver().notifyChange(uri, null);
        }
        db.close();
    }

    @Override
    public String toString() {
        return "History{" +
                "_id=" + _id +
                ", updateTime='" + updateTime + '\'' +
                ", queryTime='" + queryTime + '\'' +
                ", company_cn='" + company_cn + '\'' +
                ", company_en='" + company_en + '\'' +
                ", tag='" + tag + '\'' +
                ", code='" + code + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
