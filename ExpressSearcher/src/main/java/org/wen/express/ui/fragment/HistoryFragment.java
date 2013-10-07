package org.wen.express.ui.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

import org.wen.express.R;
import org.wen.express.common.AppLogger;
import org.wen.express.module.History;
import org.wen.express.type.AppConstant;
import org.wen.express.ui.ResultActivity;
import org.wen.express.ui.adapter.HistoryAdapter;


/**
 * Created by wenjiahui on 9/1/13.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, HistoryAdapter.OnHistoryDelete {

    private static HistoryFragment instance = null;
    private View mContentView;
    private SwipeListView swipeListView;
    private HistoryAdapter mAdapter;

    private int mPreopenItemPosition = -1;

    public static HistoryFragment getInstance() {
        if (instance == null) {
            instance = new HistoryFragment();
        }
        return instance;
    }

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
        mAdapter = new HistoryAdapter(getActivity());
        mAdapter.setDeleteCallback(this);

        swipeListView = (SwipeListView) mContentView.findViewById(R.id.lv_historys);

        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
                if (mPreopenItemPosition != -1 && mPreopenItemPosition != position) {
                    swipeListView.closeAnimate(mPreopenItemPosition);
                }
                mPreopenItemPosition = position;
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
                mPreopenItemPosition = -1;
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
//                mAdapter.notifyDataSetChanged();
            }

        });

        swipeListView.setAdapter(mAdapter);

        swipeListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_company = (TextView) view.findViewById(R.id.company);
                TextView tv_code = (TextView) view.findViewById(R.id.code);
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

    @Override
    public void preDelete() {
        if (mPreopenItemPosition != -1) {
            swipeListView.closeAnimate(mPreopenItemPosition);
        }
        swipeListView.closeOpenedItems();
        mPreopenItemPosition = -1;
    }

    @Override
    public void afterDelete() {
        mAdapter.notifyDataSetChanged();
        swipeListView.setAdapter(mAdapter);
    }
}
