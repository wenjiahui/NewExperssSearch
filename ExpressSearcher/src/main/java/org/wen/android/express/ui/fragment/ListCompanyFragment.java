package org.wen.android.express.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.wen.android.express.R;
import org.wen.android.express.common.AppLogger;
import org.wen.android.express.module.Company;
import org.wen.android.express.type.AppConstant;
import org.wen.android.express.ui.adapter.MoreAdapter;

/**
 * Created by wenjiahui on 8/27/13.
 */
public class ListCompanyFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static ListCompanyFragment instance;

    public static ListCompanyFragment getInstance() {
        if(instance == null) {
            instance = new ListCompanyFragment();
        }
        return instance;
    }

    private  View mContentView;
    protected ListView mListView;
    protected MoreAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_list_company, container, false);
        initlizeUI();
        getLoaderManager().initLoader(0, null, this);
        return mContentView;
    }

    private void initlizeUI() {
        mListView = (ListView) mContentView.findViewById(R.id.lv_more_company);
        mAdapter = new MoreAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String company_cn = ((TextView)view.findViewById(R.id.company)).getText().toString();
                String company_en = ((TextView)view.findViewById(R.id.company_en)).getText().toString();
                Intent data = new Intent();
                data.putExtra(AppConstant.COMPANY_CN, company_cn);
                data.putExtra(AppConstant.COMPANY_EN, company_en);
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle data) {
        if (data == null) {
            data = new Bundle();
        }
        String company = data.getString("condition");
        String where = null;
        if (!TextUtils.isEmpty(company)) {
            where = Company.Params.COLUMN_CN_NAME + " like '%" + company + "%' or " + Company.Params.COLUMN_EN_NAME
                    + " like '%" + company + "%'";
            AppLogger.d(where);
        }
        return new CursorLoader(getActivity(), Company.Params.CONTENT_COMPANYS_URI,
                    null, where, null, Company.Params.COLUMN_LETTER + " asc");

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
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    public void filterCompany(String condition) {
        Bundle data = new Bundle();
        data.putString("condition", condition);
        getLoaderManager().restartLoader(0, data, this);
    }
}
