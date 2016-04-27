package com.sina.sinawidgetdemo.custom.view.livelistview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;

import java.util.List;

/**
 * Created by caiy on 16/3/17.
 */
public class LiveListView extends RelativeLayout {
    private Context mContext;
    private RelativeLayout mCardLayout = null;

    /**
     * 禁止外部调用
     */
    private LiveListView(Context context) {
        super(context);
    }
    /**
     * 禁止外部调用
     */
    private LiveListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 禁止外部调用
     */
    private LiveListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveListView(Context context, RelativeLayout cardLayout){
        super(context);
        this.mContext = context;
        this.mCardLayout = cardLayout;

        loadData();
    }

    public void loadData() {
        LiveManager.getInstance().reqeustData(new OnRequestReceivedListener() {
            @Override
            public void onRequestReceiveSuccess(CommonLiveResp data) {
                showView(data);
                mCardLayout.setVisibility(View.VISIBLE);
                mCardLayout.invalidate();
            }

            @Override
            public void onRequestReceiveFailure() {
                removeAllViews();
                mCardLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showView(final CommonLiveResp resp){
        if(resp == null || resp.getLive() == null ||
                !"1".equals(resp.getLive().getStatus()) ||
                resp.getLive().getList() == null ||
                resp.getLive().getList().size() == 0){
            LiveListView.this.removeAllViews();
            mCardLayout.setVisibility(View.GONE);
            return;
        }
        View view  = (View) LayoutInflater.from(mContext).inflate(
                R.layout.card_live_list_layout, null);
        removeAllViews();
        addView(view);

        TextView titleView = (TextView)view.findViewById(R.id.live_list_title_tv);
        titleView.setText(TextUtils.isEmpty(resp.getLive().getTitle()) ? "" : resp.getLive().getTitle());

        LinearLayout moreLayout = (LinearLayout)view.findViewById(R.id.live_list_more_ll);
        ListView listView = (ListView)view.findViewById(R.id.live_list_contents_lv);
        moreLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoreLayoutOnClick(view);
            }
        });
        LiveListAdapter liveListAdapter = new LiveListAdapter(mContext,resp.getLive().getList());
        listView.setAdapter(liveListAdapter);
        setListViewHeightBasedOnChildren(mContext, listView, liveListAdapter, 93.3f);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<CommonLiveResp.CommonLiveItem> items = resp.getLive().getList();
                CommonLiveResp.CommonLiveItem item = items.get(position);
                if (item != null) {
                    onLiveListItemClick(item);
                }
            }
        });
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
            parent.addView(this);
        } else {
            mCardLayout.addView(this);
        }
    }

    protected void setListViewHeightBasedOnChildren(Context context, ListView listView,
                                                    BaseAdapter itemAdapter, float dpHeight) {
        if (itemAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < itemAdapter.getCount(); i++) {
            View listItem = itemAdapter.getView(i, null, listView);
            if(listItem != null){
                listItem.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                listItem.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                totalHeight += dip2px(context, dpHeight);
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (itemAdapter.getCount() - 1))
                + listView.getPaddingTop() + listView.getPaddingBottom();
//		DisplayMetrics  dm = new DisplayMetrics();
        //取得窗口属性
//		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        listView.setLayoutParams(params);
    }

    protected int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    protected void onMoreLayoutOnClick(View view) {

    }

    protected void onLiveListItemClick(CommonLiveResp.CommonLiveItem item) {

    }
}
