package org.wen.android.express.module;

import android.database.Cursor;
import android.net.Uri;

import org.wen.android.express.provider.CompanyProvider;

/**
 * Created by wenjiahui on 9/19/13.
 */
public class Company {

    public static class Params {
        public static final String TABLENAME = "company_info";

        public static final String DEFAULT_SORT_ORDER = "DESC";

        private static final String SCHEME = "content://";

        public static final Uri CONTENT_COMPANYS_URI = Uri.parse(SCHEME + CompanyProvider.AUTHORITY + "/company_info");

        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + CompanyProvider.AUTHORITY + "/company_info/");

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CN_NAME = "company_cn";
        public static final String COLUMN_EN_NAME = "company_en";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_LETTER = "letter";
    }

    public String _id;
    public String company_cn;
    public String company_en;
    public String phone;
    public String letter;

    @Override
    public String toString() {
        return "Company{" +
                "_id='" + _id + '\'' +
                ", company_cn='" + company_cn + '\'' +
                ", company_en='" + company_en + '\'' +
                ", phone='" + phone + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }

    public static Company getBean(Cursor cursor) {
        cursor.moveToFirst();
        Company company = new Company();

        company.company_cn = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_CN_NAME));
        company.company_en = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_EN_NAME));
        company.phone = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_PHONE));
        company.letter = cursor.getString(cursor.getColumnIndex(Company.Params.COLUMN_LETTER));
        return company;
    }
}
