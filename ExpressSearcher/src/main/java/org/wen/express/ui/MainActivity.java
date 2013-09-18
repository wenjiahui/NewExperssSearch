package org.wen.express.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import net.simonvt.menudrawer.MenuDrawer;

import org.wen.express.AppContext;
import org.wen.express.R;
import org.wen.express.common.AppLogger;
import org.wen.express.type.AppConstant;
import org.wen.express.type.Category;
import org.wen.express.ui.fragment.DrawerFragment;
import org.wen.express.ui.fragment.HistoryFragment;
import org.wen.express.ui.fragment.SimpleCompanyFragment;

public class MainActivity extends SherlockFragmentActivity {

    protected AppContext application;

    private MenuDrawer mDrawer;

    protected SimpleCompanyFragment mSimpleCompanyFragment;
    protected DrawerFragment mDrawerFramgment;

    protected Category mCategory = Category.SEARCH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();

        application = (AppContext) getApplication();

        AppLogger.d(application.isTablet() + "");
        if (!application.isTablet()) {
            mDrawer = MenuDrawer.attach(MainActivity.this);
            mDrawer.setContentView(R.layout.activity_main);
            mDrawer.setMenuView(R.layout.menu_drawer);

            // The drawable that replaces the up indicator in the action bar
            mDrawer.setSlideDrawable(R.drawable.ic_drawer);
            // Whether the previous drawable should be shown
            mDrawer.setDrawerIndicatorEnabled(true);
        }


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mSimpleCompanyFragment = SimpleCompanyFragment.getInstance();
        ft.add(R.id.container, mSimpleCompanyFragment);
        mDrawerFramgment = DrawerFragment.getInstance();
        ft.add(R.id.drawer_container, mDrawerFramgment);
        ft.commit();
    }


    /**
     * 常见快递公司按钮点击事件
     * @param view 点击事件源
     */
    public void oftenUsed(View view) {
        String companyName = ((TextView)view).getText().toString();
        if (!TextUtils.isEmpty(companyName) && mSimpleCompanyFragment != null) {
            mSimpleCompanyFragment.selectCompany(companyName);
        }
    }

    /**
     * 查看更多的快递公司
     * @param view 点击事件源
     */
    public void moreCompany(View view) {
        Intent intent = new Intent(MainActivity.this, MoreActivity.class);
        startActivityForResult(intent, AppConstant.LOAD_MORE_COMPANIES_REQUEST_CODE);
    }

    /**
     * 扫描条形码
     * @param view 点击事件源
     */
    public void scanQrCode(View view) {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, AppConstant.CAPTURE_REQUEST_CODE);
    }

    /**
     * 查询订单详情
     * @param view 点击事件源
     */
    public void search(View view) {

    }


    public void onDrawerItemSelected(Category category) {
        mDrawer.closeMenu();
        if (mCategory == category) {
            return;
        }
        mCategory = category;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (category == Category.SEARCH) {
            mSimpleCompanyFragment = SimpleCompanyFragment.getInstance();
            ft.replace(R.id.container, mSimpleCompanyFragment);
        } else if (category == Category.HISTORY) {
            ft.replace(R.id.container, HistoryFragment.getInstance());
        }
        ft.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.LOAD_MORE_COMPANIES_REQUEST_CODE && resultCode == RESULT_OK) {
            String company = data.getStringExtra(AppConstant.SELECTED_COMPANY);
            if (!TextUtils.isEmpty(company) && mSimpleCompanyFragment != null) {
                mSimpleCompanyFragment.selectCompany(company);
            }
        } else if (requestCode == AppConstant.CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            String code = data.getStringExtra(AppConstant.CAPTURE_CODE);
            if (!TextUtils.isEmpty(code) && mSimpleCompanyFragment != null) {
                mSimpleCompanyFragment.setExpressCode(code);
            }
        }
    }
}
