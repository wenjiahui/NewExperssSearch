package org.wen.express.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wen.express.R;
import org.wen.express.module.Company;
import org.wen.express.type.AppConstant;
import org.wen.express.ui.ResultActivity;

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
    private Button btn_search;
    private EditText et_code_input;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewContainer = inflater.inflate(R.layout.fragment_simple_company, null);
        initlize();
        return mViewContainer;
    }

    private void initlize() {
        btn_search = (Button) mViewContainer.findViewById(R.id.search);
        et_code_input = (EditText) mViewContainer.findViewById(R.id.ex_order_number);
        et_code_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    btn_search.setEnabled(false);
                } else {
                    btn_search.setEnabled(true);
                }
            }
        });
    }

    public void selectCompany(String company) {
        TextView tv_company = (TextView) mViewContainer.findViewById(R.id.selected_company);
        tv_company.setText(company);
    }

    public void setExpressCode(String code) {
        TextView tv_expressCode = (TextView) mViewContainer.findViewById(R.id.ex_order_number);
        tv_expressCode.setText(code);
    }

    public void search() {
        TextView tv_company = (TextView) mViewContainer.findViewById(R.id.selected_company);
        TextView tv_expressCode = (TextView) mViewContainer.findViewById(R.id.ex_order_number);
        String company = tv_company.getText().toString();
        String code = tv_expressCode.getText().toString();

        if (TextUtils.isEmpty(company)) {
            Toast.makeText(getActivity(), R.string.company_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(code)) {
            Toast.makeText(getActivity(), R.string.code_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getActivity(), ResultActivity.class);
        intent.putExtra(AppConstant.COMPANY, company);
        intent.putExtra(AppConstant.EXPRESS_CODE, code);
        getActivity().startActivity(intent);
    }
}
