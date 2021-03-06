package org.wen.android.express.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.wen.android.express.AppContext;
import org.wen.android.express.R;
import org.wen.android.express.module.Details;
import org.wen.android.express.module.Result;

/**
 * Created by wenjiahui on 9/22/13.
 */
public class ResultAdapter extends BaseAdapter {

    private Result result;
    private List<Details> datas;

    public ResultAdapter(Result result) {
        this.result = result;
        if (result == null) {
            datas = new ArrayList<Details>();
        } else {
            datas = result.data;
        }

    }

    public void updateResult(Result result) {
        this.result = result;
        if (result != null) {
            datas = result.getData();
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Details detail = (Details) getItem(i);
        view = LayoutInflater.from(AppContext.getInstance()).inflate(R.layout.item_result_listview, null);
        TextView tv_time = (TextView) view.findViewById(R.id.time);
        TextView tv_context = (TextView)view.findViewById(R.id.context);
        tv_context.setText(detail.context);
        tv_time.setText(detail.time);
        if (i == 0) {
            tv_time.setTextColor(AppContext.getInstance().getResources().getColor(R.color.latest_express_details));
            tv_context.setTextColor(AppContext.getInstance().getResources().getColor(R.color.latest_express_details));
        }
        return view;
    }
}
