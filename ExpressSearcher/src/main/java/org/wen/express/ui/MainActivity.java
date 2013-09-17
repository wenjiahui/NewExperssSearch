package org.wen.express.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import net.simonvt.menudrawer.MenuDrawer;

import org.wen.express.AppContext;
import org.wen.express.R;
import org.wen.express.common.AppLogger;
import org.wen.express.common.IntentUtil;
import org.wen.express.ui.fragment.SimpleCompanyFragment;

public class MainActivity extends SherlockFragmentActivity {

    protected AppContext application;

    private MenuDrawer mDrawer;

    protected SimpleCompanyFragment mSimpleCompanyFragment;

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
            setMenuDrawerListener(mDrawer.getMenuView());
        }

        mSimpleCompanyFragment = SimpleCompanyFragment.getInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mSimpleCompanyFragment).commit();
    }

    /**
     * 对mDrawer设置点击事情
     * @param menuView
     */
    private void setMenuDrawerListener(View menuView) {

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
     * 扫描条形码
     * @param view 点击事件源
     */
    public void scanQrCode(View view) {

    }

    /**
     * 查询订单详情
     * @param view 点击事件源
     */
    public void search(View view) {

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

    }
}
