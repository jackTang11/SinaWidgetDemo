package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;

import java.util.ArrayList;

/**
 * Created by liuchonghui on 16/4/28.
 */
public class PairScrollTextViewAndListFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!isViewNull()) {
            return mView;
        }
        mView = inflater.inflate(R.layout.activity_text_and_list, container,
                false);
        initView(mView);
        return mView;

    }

    private void initView(View view){
        final TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText("Test\nTest\nTest\nTest\nTest\nTest\nTest");

        final ListView list = (ListView) view.findViewById(R.id.list);
        int count = 40;
        ArrayList<String> data = new ArrayList<String>(count);
        for (int i = 0; i < count; i++) {
            data.add("Text " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_expandable_list_item_1, data);
        TextView header = new TextView(getActivity());
        header.setText("Header");
        list.addHeaderView(header);
        list.setAdapter(adapter);
    }

}
