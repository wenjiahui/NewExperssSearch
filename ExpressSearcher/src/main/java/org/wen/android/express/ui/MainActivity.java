package org.wen.android.express.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import net.simonvt.menudrawer.MenuDrawer;
import org.wen.android.express.AppContext;
import org.wen.android.express.R;
import org.wen.android.express.common.AppLogger;
import org.wen.android.express.type.AppConstant;
import org.wen.android.express.type.Category;
import org.wen.android.express.ui.fragment.DrawerFragment;
import org.wen.android.express.ui.fragment.HistoryFragment;
import org.wen.android.express.ui.fragment.SimpleCompanyFragment;

public class MainActivity extends SherlockFragmentActivity {

    private MenuDrawer mDrawer;

    protected SimpleCompanyFragment mSimpleCompanyFragment;
    protected DrawerFragment mDrawerFramgment;

    protected Category mCategory = Category.SEARCH;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();

        AppLogger.d(AppContext.isTablet() + "");
        if (!AppContext.isTablet()) {
            mDrawer = MenuDrawer.attach(MainActivity.this);
            mDrawer.setContentView(R.layout.activity_main);
            mDrawer.setMenuView(R.layout.menu_drawer);

            // The drawable that replaces the up indicator in the action bar
            mDrawer.setSlideDrawable(R.drawable.ic_drawer);
            // Whether the previous drawable should be shown
            mDrawer.setDrawerIndicatorEnabled(true);
            mDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
                @Override public void onDrawerStateChange(int oldState, int newState) {
                    if (newState == MenuDrawer.STATE_OPEN) {
                        getSupportActionBar().setTitle(R.string.app_name);
                    } else if (newState == MenuDrawer.STATE_CLOSED) {
                        getSupportActionBar().setTitle(title);
                    }
                }

                @Override public void onDrawerSlide(float openRatio, int offsetPixels) {

                }
            });
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
        if (mSimpleCompanyFragment != null) {
            mSimpleCompanyFragment.search();
        }
    }


    public void onDrawerItemSelected(Category category, View view, int i) {
        mDrawer.closeMenu();
        if (mCategory == category) {
            return;
        }
        mDrawer.setActiveView(view, i);
        mCategory = category;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (category == Category.SEARCH) {
            mSimpleCompanyFragment = SimpleCompanyFragment.getInstance();
            ft.replace(R.id.container, mSimpleCompanyFragment, SimpleCompanyFragment.class.getSimpleName());
        } else if (category == Category.HISTORY) {
            ft.replace(R.id.container, HistoryFragment.getInstance(), HistoryFragment.class.getSimpleName());
        }
        ft.commit();
        title = category.getValue();
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
            String company = data.getStringExtra(AppConstant.COMPANY_CN);
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
