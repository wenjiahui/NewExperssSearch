package org.wen.express.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.wen.express.R;

/**
 * Created by wenjiahui on 8/27/13.
 */
public class SimpleCompanyFragment extends Fragment{

    private static SimpleCompanyFragment instance = null;

    public static SimpleCompanyFragment getInstance() {
        if(instance == null) {
            instance = new SimpleCompanyFragment();
        }
        return instance;
    }


    private View mViewContainer;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mViewContainer = inflater.inflate(R.layout.fragment_simple_company, null);
    }

    public void selectCompany(String company) {
        TextView tv_company = (TextView) mViewContainer.findViewById(R.id.selected_company);
        tv_company.setText(company);
    }
}
