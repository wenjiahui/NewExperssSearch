package org.wen.android.express.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import org.wen.android.express.module.Company;

import java.util.HashMap;

/**
 * Created by wenjiahui on 9/19/13.
 */
public class CompanyProvider extends ContentProvider {

    public static final String AUTHORITY = "org.wen.android.express.provider.express.companys";

    private DBHelper dbHelper;

    private static final int COMPANYS = 1;

    private static final int COMPANY_ID = 2;

    private static final UriMatcher sUriMatcher;
    private static HashMap<String, String> sCompanyProjectionMap;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "company_info", COMPANYS);
        sUriMatcher.addURI(AUTHORITY, "company_info/*", COMPANY_ID);

        sCompanyProjectionMap = new HashMap<String, String>();
        sCompanyProjectionMap.put(Company.Params.COLUMN_ID, Company.Params.COLUMN_ID);
        sCompanyProjectionMap.put(Company.Params.COLUMN_CN_NAME, Company.Params.COLUMN_CN_NAME);
        sCompanyProjectionMap.put(Company.Params.COLUMN_EN_NAME, Company.Params.COLUMN_EN_NAME);
        sCompanyProjectionMap.put(Company.Params.COLUMN_PHONE, Company.Params.COLUMN_PHONE);
        sCompanyProjectionMap.put(Company.Params.COLUMN_LETTER, Company.Params.COLUMN_LETTER);


    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String order) {
        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        sqb.setTables(Company.Params.TABLENAME);
        switch (sUriMatcher.match(uri)) {
            case COMPANYS:
                sqb.setProjectionMap(sCompanyProjectionMap);
                break;
            case COMPANY_ID:
                sqb.setProjectionMap(sCompanyProjectionMap);
                sqb.appendWhere(Company.Params.COLUMN_ID + " = '" + uri.getPathSegments().get(1) + "'");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = sqb.query(db, projection, selection, selectionArgs, null, null, order);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (sUriMatcher.match(uri) != COMPANYS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(Company.Params.TABLENAME, Company.Params.COLUMN_EN_NAME, contentValues);
        if (rowId > 0) {
            Uri returnUri =  ContentUris.withAppendedId(Company.Params.CONTENT_ID_URI_BASE, rowId);
            getContext().getContentResolver().notifyChange(returnUri, null);
            db.close();
            return returnUri;
        }
        db.close();
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


    static class DBHelper extends SQLiteOpenHelper {
        // 数据库名
        private static final String DB_NAME = "company.db";

        // 数据库版本
        private static final int VERSION = 5;

        private DBHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
