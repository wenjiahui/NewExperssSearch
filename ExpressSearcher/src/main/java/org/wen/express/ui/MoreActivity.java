package org.wen.express.ui;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import me.imid.swipebacklayout.lib.app.SwipeBackSherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import org.wen.express.R;
import org.wen.express.ui.fragment.ListCompanyFragment;

public class MoreActivity extends SwipeBackSherlockActivity {

    ListCompanyFragment mFrament = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        mFrament =  ListCompanyFragment.getInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container,mFrament).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Search")
                .setIcon(R.drawable.ic_search)
                .setActionView(R.layout.collapsible_edittext)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        if ("Search".equals(title)) {
            EditText inputEdittext = (EditText) item.getActionView().findViewById(R.id.input);
            inputEdittext.setFocusable(true);
            inputEdittext.setFocusableInTouchMode(true);
            inputEdittext.requestFocus();
            InputMethodManager inputManager =
                    (InputMethodManager)inputEdittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(inputEdittext, 0);
            inputEdittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    mFrament.filterCompany(s.toString());
                }
            });
        }
        return true;
    }
}
