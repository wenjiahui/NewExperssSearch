package org.wen.express.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.wen.express.R;
import org.wen.express.dao.CompanyDBHelper;
import org.wen.express.module.Company;

/**
 * Created by wenjiahui on 9/27/13.
 */
public class MoreAdapter extends CursorAdapter{

    private final LayoutInflater mInflater;
    private final Resources mResources;
    private final String packageName;

    public MoreAdapter(Context context) {
        super(context, null, true);
        mInflater = LayoutInflater.from(context);
        mResources = context.getResources();
        packageName = context.getPackageName();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.item_more_company_listview, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = getHolder(view);
        Company company = CompanyDBHelper.fromCursor(cursor);
        holder.phone.setText(company.phone);
        holder.company.setText(company.company_cn);
        holder.company_en.setText(company.company_en);
        int iconId = mResources.getIdentifier(company.company_en + "_s", "drawable", packageName);
        if (iconId <= 0) {
            iconId = R.drawable.ic_launcher;
        }
        holder.icon.setImageResource(iconId);
    }


    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class Holder {

        TextView phone;
        TextView company;
        TextView company_en;
        ImageView icon;

        public Holder(View view) {
            phone = (TextView) view.findViewById(R.id.phone);
            company = (TextView) view.findViewById(R.id.company);
            company_en = (TextView)view.findViewById(R.id.company_en);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
