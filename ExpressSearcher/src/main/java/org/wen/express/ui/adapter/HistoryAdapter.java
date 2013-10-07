package org.wen.express.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.wen.express.AppContext;
import org.wen.express.R;
import org.wen.express.dao.HistoryDBHelper;
import org.wen.express.module.History;

/**
 * Created by wenjiahui on 9/25/13.
 */
public class HistoryAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    private OnHistoryDelete deleteCallback;

    public void setDeleteCallback(OnHistoryDelete deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

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

        holder.delete.setOnClickListener(new DeleteListener(String.valueOf(history._id)));
    }


    private class DeleteListener implements View.OnClickListener {

        private String id ;
        private DeleteListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            if (deleteCallback != null) {
                deleteCallback.preDelete();
            }
            int result = AppContext.getInstance().getContentResolver().delete(
                    Uri.parse(History.TABLE_PARAMS.CONTENT_BASE_URI + id), null, null);
            if (result > 0) {
                Toast.makeText(AppContext.getInstance(), R.string.delete_record_succefully, Toast.LENGTH_SHORT).show();

            }
            if (deleteCallback != null) {
                deleteCallback.afterDelete();
            }
        }
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
        Button delete;

        public Holder(View view) {
            id = (TextView) view.findViewById(R.id.id);
            company = (TextView) view.findViewById(R.id.company);
            company_en = (TextView)view.findViewById(R.id.company_en);
            code = (TextView) view.findViewById(R.id.code);
            query_time = (TextView) view.findViewById(R.id.query_time);
            tag = (TextView) view.findViewById(R.id.tag);
            delete = (Button)view.findViewById(R.id.btn_delete);
        }
    }

    /***
     * 回调函数，重置mPreopenItemPosition = -1.
     * 避免下一次滑动时出现空指针错误
     */
    public interface OnHistoryDelete {
        void preDelete();
        void afterDelete();
    }
}
