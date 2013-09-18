package org.wen.express.type;

import org.wen.express.AppContext;
import org.wen.express.R;

/**
 * 标记Drawer的每一项
 * Created by wenjiahui on 9/18/13.
 */
public enum Category {
    SEARCH(AppContext.getInstance().getResources().getString(R.string.search)), //主界面
    HISTORY(AppContext.getInstance().getResources().getString(R.string.history)); //查询历史记录

    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
