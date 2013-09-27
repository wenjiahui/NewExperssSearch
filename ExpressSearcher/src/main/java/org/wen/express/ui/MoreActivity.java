package org.wen.express.ui;


import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.wen.express.R;
import org.wen.express.ui.fragment.ListCompanyFragment;

public class MoreActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        getSupportFragmentManager().beginTransaction().add(R.id.container, ListCompanyFragment.getInstance()).commit();
    }

}
