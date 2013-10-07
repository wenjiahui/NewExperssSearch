package org.wen.express.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import org.wen.express.common.AppLogger;
import org.wen.express.module.History;

/**
 * Created by wenjiahui on 9/22/13.
 */
public class DataProvider extends ContentProvider{
    public static final String AUTHORITY = "org.wen.express.provider.express.data";

    private static UriMatcher mUriMatcher;
    private static final int HISTORYS = 1;
    private static final int HISTORY_ID = 2;

    private DBHelper dbHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "historys", HISTORYS);
        mUriMatcher.addURI(AUTHORITY, "historys/*", HISTORY_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {
            case HISTORYS :
                sqb.setTables(History.TABLE_PARAMS.TABLE_NAME);
                sqb.setProjectionMap(History.TABLE_PARAMS.mProjection);
                break;
            case HISTORY_ID:
                sqb.setTables(History.TABLE_PARAMS.TABLE_NAME);
                sqb.setProjectionMap(History.TABLE_PARAMS.mProjection);
                sqb.appendWhere(History.TABLE_PARAMS.COLUMN_ID = " = '" + uri.getPathSegments().get(1) + "'");
                break;
            default:
                throw new IllegalArgumentException("unknown Uri:" + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqb.query(db, projection, selection, selectionArgs, null, null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (mUriMatcher.match(uri) != HISTORYS) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowid = db.insert(History.TABLE_PARAMS.TABLE_NAME, null, values);
        if (rowid > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowid);
            getContext().getContentResolver().notifyChange(_uri, null);
            db.close();
            return _uri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case HISTORYS :
                count = db.delete(History.TABLE_PARAMS.TABLE_NAME, where, whereArgs);
                break;
            case HISTORY_ID:
                String finalWhere = History.TABLE_PARAMS.COLUMN_ID  + " = " + uri.getPathSegments().get(1) ;
                if (!TextUtils.isEmpty(where)) {
                    finalWhere = finalWhere + " AND " + where;
                }
                AppLogger.d(finalWhere);
                count = db.delete(History.TABLE_PARAMS.TABLE_NAME, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown Uri:" + uri);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case HISTORYS :
                count = db.update(History.TABLE_PARAMS.TABLE_NAME, values, where, whereArgs);
                break;
            case HISTORY_ID:
                String finalWhere = History.TABLE_PARAMS.COLUMN_ID  + " = '" + uri.getPathSegments().get(1) + "'";
                if (!TextUtils.isEmpty(where)) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(History.TABLE_PARAMS.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public static class DBHelper extends SQLiteOpenHelper {
        // 数据库名
        private static final String DB_NAME = "express.db";

        // 数据库版本
        private static final int VERSION = 1;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(History.TABLE_PARAMS.CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
