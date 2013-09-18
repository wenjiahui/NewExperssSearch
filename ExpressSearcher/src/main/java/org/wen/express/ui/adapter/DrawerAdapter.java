package org.wen.express.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.wen.express.R;
import org.wen.express.type.Category;

/**
 * Created by wenjiahui on 9/18/13.
 */
public class DrawerAdapter  extends BaseAdapter{

    private LayoutInflater inflater;

    public DrawerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Category.values().length;
    }

    @Override
    public Object getItem(int i) {
        return Category.values()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_drawer, null);
        }
        TextView item = (TextView) view.findViewById(R.id.item);
        String displayName = ((Category)getItem(i)).getValue();
        item.setText(displayName);

        view.setTag(getItem(i));
        return view;
    }
}
