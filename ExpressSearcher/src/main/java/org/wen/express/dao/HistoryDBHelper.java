package org.wen.express.dao;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import org.wen.express.common.AppLogger;
import org.wen.express.module.History;

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

        history.code = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_CODE));
        history.queryTime = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_QUERY_TIME));
        history.company_cn = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_COMPANY_CN));
        history.company_en = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_COMPANY_EN));
        history.tag = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_TAG));
        history.updateTime = mCursor.getString(mCursor.getColumnIndex(History.TABLE_PARAMS.COLUMN_UPDATE_TIME));

        return history;
    }
}
