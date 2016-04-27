package com.sina.sinawidgetdemo.custom.view.livelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.imageloadercompact.CompactImageView;
import com.android.imageloadercompact.ImageLoaderCompact;
import com.sina.sinawidgetdemo.R;

import java.util.List;

public class LiveListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommonLiveResp.CommonLiveItem> mDatas;

    public LiveListAdapter(Context context, List<CommonLiveResp.CommonLiveItem> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        if (mDatas == null || mDatas.size() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImagesHolder holder = null;
        if (convertView == null) {
            holder = new ImagesHolder(convertView);
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.livelistview_item, parent, false);
            holder.productImages = (CompactImageView) convertView.findViewById(R.id.product_image);
            convertView.setTag(holder);
        } else {
            holder = (ImagesHolder) convertView.getTag();
        }
        String url = "http://n.sinaimg.cn/transform/20150526/splR-avxeafs8127570.jpg";
        ImageLoaderCompact.getInstance().displayImage(mContext, url
                , holder.productImages);
        return convertView;
    }


    static class ImagesHolder {
        private View base;
        private CompactImageView productImages;
        private View social_special;

        public ImagesHolder(View base) {
            this.base = base;

        }
    }
}
