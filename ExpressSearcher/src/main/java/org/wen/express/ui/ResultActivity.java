package org.wen.express.ui;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.wen.express.AppContext;
import org.wen.express.R;
import org.wen.express.common.AppLogger;
import org.wen.express.common.GsonRequest;
import org.wen.express.common.GsonUtility;
import org.wen.express.common.MyVolley;
import org.wen.express.common.URLUtility;
import org.wen.express.module.Company;
import org.wen.express.module.Details;
import org.wen.express.module.History;
import org.wen.express.module.Result;
import org.wen.express.type.AppConstant;
import org.wen.express.ui.adapter.ResultAdapter;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import me.imid.swipebacklayout.lib.app.SwipeBackSherlockActivity;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class ResultActivity extends SwipeBackSherlockActivity implements PullToRefreshAttacher.OnRefreshListener {

    private TextView tv_company;
    private TextView tv_expressCode;
    private ProgressBar progressBar;
    private ListView listView;

    private String originCompany, originExpressCode;
    private String realCompany, realExpressCode;

    private Company company;

    private ResultAdapter adapter;

    private PullToRefreshAttacher mPullToRefreshAttacher;

    private String[] projection = {
        Company.Params.COLUMN_ID,
        Company.Params.COLUMN_CN_NAME,
        Company.Params.COLUMN_EN_NAME,
        Company.Params.COLUMN_PHONE,
        Company.Params.COLUMN_LETTER
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_result);

        initlize();
        company = getRealSearchValue();
        if (company != null) {
            updateUI(company);
            search(company, originExpressCode);
        }

//        searchTest(null, null);
    }

    private void initlize() {
        Bundle data = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        originCompany = data.getString(AppConstant.COMPANY, "");
        realExpressCode = originExpressCode = data.getString(AppConstant.EXPRESS_CODE, "");
        if (TextUtils.isEmpty(originCompany) || TextUtils.isEmpty(originExpressCode)) {
            ResultActivity.this.finish();
            return;
        }

        tv_company = (TextView) findViewById(R.id.company);
        tv_expressCode = (TextView) findViewById(R.id.express_code);

        tv_company.setText(originCompany);
        tv_expressCode.setText(originExpressCode);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        Result result = null;
        listView = (ListView) findViewById(R.id.lv_result);
        adapter = new ResultAdapter(result);
        listView.setAdapter(adapter);

        String cache = ((AppContext)AppContext.getInstance()).getCache(realExpressCode);
        AppLogger.d("cache " + realExpressCode + " is: " + cache);
        if (!TextUtils.isEmpty(cache)) {
            List<Details> cacheResult = GsonUtility.getGson().fromJson(cache, new TypeToken<List<Details>>() {}.getType());
            result = new Result();
            result.data = cacheResult;
            if (cacheResult == null) {
                AppLogger.d("make List from cache is null");
            } else {
                AppLogger.d("make List from cache' size: " + cacheResult.size());
            }
        }
        adapter.updateResult(result);
        adapter.notifyDataSetChanged();

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        mPullToRefreshAttacher.addRefreshableView(listView, this);
    }

    private void updateUI(final Company company) {
        tv_company.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_company.getPaint().setFakeBoldText(true);
        tv_company.setTextColor(AppContext.getInstance().getResources().getColor(R.color.company_can_called));
        tv_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "tel:" + company.phone;
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(callIntent);
            }
        });
    }

    private Company getRealSearchValue() {
        Company _company = null;
        Cursor cursor = getContentResolver().query(Company.Params.CONTENT_COMPANYS_URI, projection,
                "company_cn like '%" + originCompany + "%'", null, null);
        AppLogger.d("cursor's size: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {
            _company = Company.getBean(cursor);
            cursor.close();
        } else {
            progressBar.setVisibility(View.GONE);
//            Toast.makeText(ResultActivity.this, R.string.company_not_exist, Toast.LENGTH_SHORT).show();
            Crouton.makeText(ResultActivity.this ,R.string.company_not_exist, Style.ALERT).show();
        }
        return _company;
    }

    private void search(Company company, String realExpressCode) {

        if (company == null || TextUtils.isEmpty(company.company_en)) {
            return;
        }

        mPullToRefreshAttacher.setRefreshing(true);
        RequestQueue requestQueue = MyVolley.getRequestQueue();
        GsonRequest request = new GsonRequest(Request.Method.GET,
                URLUtility.getURL(company.company_en, realExpressCode),
                Result.class,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        requestQueue.add(request);
    }

    private void searchTest(Company company, String originExpressCode) {

        RequestQueue requestQueue = MyVolley.getRequestQueue();
        GsonRequest request = new GsonRequest(Request.Method.GET,
                URLUtility.getURL("shentong", "668535524584"),
                Result.class,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        requestQueue.add(request);
    }

    private Response.Listener createMyReqSuccessListener() {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result response) {
                if (mPullToRefreshAttacher.isRefreshing()) {
                    mPullToRefreshAttacher.setRefreshComplete();
                }
                AppLogger.d(response.toString());
                progressBar.setVisibility(View.GONE);
                if (response.data == null || response.data.size() == 0) {
                    //Toast.makeText(ResultActivity.this, response.message, Toast.LENGTH_SHORT).show();
                    Crouton.makeText(ResultActivity.this, response.message, Style.ALERT).show();
                    return;
                }
                listView.setVisibility(View.VISIBLE);
                adapter.updateResult(response);
                adapter.notifyDataSetChanged();

               //Toast.makeText(ResultActivity.this, R.string.load_data_successfully, Toast.LENGTH_SHORT).show();
                Crouton.makeText(ResultActivity.this, R.string.load_data_successfully, Style.INFO).show();

                History history = new History(response);
                history.company_cn = originCompany;
                History.saveOrUpdate(history);
                ((AppContext)AppContext.getInstance()).saveCache(history.code,
                        GsonUtility.getGson().toJson(response.getData(), new TypeToken<List<Details>>(){}.getType()));
            }
        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLogger.d(error.toString());
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(ResultActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                Crouton.makeText(ResultActivity.this, R.string.network_error, Style.ALERT).show();

                if (mPullToRefreshAttacher.isRefreshing()) {
                    mPullToRefreshAttacher.setRefreshComplete();
                }
            }
        };
    }


    @Override
    public void onRefreshStarted(View view) {
        search(company, realExpressCode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ResultActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
