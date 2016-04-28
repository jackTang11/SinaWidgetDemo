package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;

import java.util.ArrayList;

/**
 * Created by liuchonghui on 16/4/28.
 */
public class PairScrollWebAndListFragment extends BaseFragment {

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
        mView = inflater.inflate(R.layout.activity_web_and_list, container,
                false);
        initView(mView);
        return mView;

    }

    private void initView(View view){
        final WebView webView = (WebView) view.findViewById(R.id.web);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl("http://www.angeldevil.me");
        // webView.loadData("Test</br>Test</br>Test</br>Test</br>Test</br>Test", "text/html", "utf-8");

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
