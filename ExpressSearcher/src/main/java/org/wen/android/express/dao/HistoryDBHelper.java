package org.wen.android.express.dao;

import android.content.Context;
import android.database.Cursor;

import org.wen.android.express.common.AppLogger;
import org.wen.android.express.module.History;

/**
 * Created by wenjiahui on 9/25/13.
 */
public class HistoryDBHelper {

    private Context mContext;

    public HistoryDBHelper(Context context) {
        if (context == null) {
            AppLogger.d("HistoryDBHelper's context is empty");
        }
        mContext = context;
    }

    public static History fromCursor(Cursor mCursor) {
        History history = new History();

        history._id =  Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_ID)));
        history.code = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_CODE));
        history.queryTime = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_QUERY_TIME));
        history.company_cn = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_COMPANY_CN));
        history.company_en = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_COMPANY_EN));
        history.tag = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_TAG));
        history.updateTime = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_UPDATE_TIME));

        return history;
    }
}
