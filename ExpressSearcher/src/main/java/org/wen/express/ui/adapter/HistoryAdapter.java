package org.wen.express.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.wen.express.R;
import org.wen.express.dao.HistoryDBHelper;
import org.wen.express.module.History;

/**
 * Created by wenjiahui on 9/25/13.
 */
public class HistoryAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public HistoryAdapter(Context context) {
        super(context, null, true);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public History getItem(int position) {
        mCursor.moveToPosition(position);
        return HistoryDBHelper.fromCursor(mCursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.item_history_listview, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        History history = HistoryDBHelper.fromCursor(cursor);
        Holder holder = getHolder(view);
        holder.id.setText(String.valueOf(history._id));
        holder.query_time.setText(history.queryTime);
        holder.company.setText(history.company_cn);
        holder.company_en.setText(history.company_en);
        holder.code.setText(history.code);
        holder.tag.setText(history.tag);
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

        TextView id;
        TextView company;
        TextView company_en;
        TextView code;
        TextView query_time;
        TextView tag;

        public Holder(View view) {
            id = (TextView) view.findViewById(R.id.id);
            company = (TextView) view.findViewById(R.id.company);
            company_en = (TextView)view.findViewById(R.id.company_en);
            code = (TextView) view.findViewById(R.id.code);
            query_time = (TextView) view.findViewById(R.id.query_time);
            tag = (TextView) view.findViewById(R.id.tag);
        }
    }
}
