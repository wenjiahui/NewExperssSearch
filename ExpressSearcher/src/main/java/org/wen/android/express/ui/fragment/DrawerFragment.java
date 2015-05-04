package org.wen.android.express.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.wen.android.express.R;
import org.wen.android.express.type.Category;
import org.wen.android.express.ui.MainActivity;
import org.wen.android.express.ui.adapter.DrawerAdapter;

/**
 * Created by wenjiahui on 9/18/13.
 */
public class DrawerFragment extends Fragment{

    private static DrawerFragment instance;

    public static DrawerFragment getInstance() {
        if (instance == null) {
            instance = new DrawerFragment();
        }
        return instance;
    }

    private View drawerMenu;

    private MainActivity mainActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        drawerMenu = inflater.inflate(R.layout.fragment_drawer, container, false);
        initlize();
        return drawerMenu;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainActivity = (MainActivity) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化界面，绑定监听器
    private void initlize() {
        final ListView listView = (ListView) drawerMenu.findViewById(R.id.drawer_items);
        listView.setItemChecked(0, true);
        listView.setAdapter(new DrawerAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mainActivity != null) {
                    listView.setItemChecked(i, true);
                    mainActivity.onDrawerItemSelected((Category)view.getTag(), view, i);
                }
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }
}
