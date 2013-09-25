package org.wen.express.ui.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.wen.express.R;
import org.wen.express.common.AppLogger;
import org.wen.express.dao.HistoryDBHelper;
import org.wen.express.module.History;
import org.wen.express.type.AppConstant;
import org.wen.express.ui.ResultActivity;
import org.wen.express.ui.adapter.HistoryAdapter;


/**
 * Created by wenjiahui on 9/1/13.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static HistoryFragment instance = null;

    public static HistoryFragment getInstance() {
        if (instance == null) {
            instance = new HistoryFragment();
        }
        return instance;
    }

    private View mContentView;
    private ListView lv_historys;
    private HistoryAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_history, null);
        initlizeUI();
        getLoaderManager().initLoader(0, null, this);
        return mContentView;
    }

    private void initlizeUI() {
       lv_historys = (ListView) mContentView.findViewById(R.id.lv_historys);
       mAdapter = new HistoryAdapter(getActivity());
       lv_historys.setAdapter(mAdapter);
       lv_historys.setOnItemClickListener(new ListView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView tv_company = (TextView) view.findViewById(R.id.company);
               TextView tv_code = (TextView)view.findViewById(R.id.code);
               String company = tv_company.getText().toString();
               String code = tv_code.getText().toString();
               Intent intent = new Intent(getActivity(), ResultActivity.class);
               intent.putExtra(AppConstant.COMPANY, company);
               intent.putExtra(AppConstant.EXPRESS_CODE, code);
               startActivity(intent);
           }
       });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), History.TABLE_PARAMS.CONTENT_ALL_URL,
                null, null, null, History.TABLE_PARAMS.COLUMN_QUERY_TIME + " desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        AppLogger.d("onLoadFinished");
        if (cursor == null || !cursor.moveToFirst()) {
            AppLogger.d("cursor has no datas");
        }
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.changeCursor(null);
    }
}
