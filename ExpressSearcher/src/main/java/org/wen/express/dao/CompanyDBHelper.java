package org.wen.express.dao;

import android.database.Cursor;

import org.wen.express.module.Company;

/**
 * Created by wenjiahui on 9/27/13.
 */
public class CompanyDBHelper {

    public static Company fromCursor(Cursor cursor) {
        Company company = new Company();
        String id = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_ID));
        String company_cn = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_CN_NAME));
        String company_en = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_EN_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_PHONE));
        company._id = id;
        company.company_cn = company_cn;
        company.company_en = company_en;
        company.phone = phone;
        return company;
    }

}
