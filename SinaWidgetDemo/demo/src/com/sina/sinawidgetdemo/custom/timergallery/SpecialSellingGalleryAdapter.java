package com.sina.sinawidgetdemo.custom.timergallery;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.imageloadercompact.CompactImageView;
import com.android.imageloadercompact.ImageLoaderCompact;
import com.sina.sinawidgetdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SpecialSellingGalleryAdapter extends ArrayAdapter<JumpableImage> {
    private ArrayList<JumpableImage> list;
    private Context context;

    private boolean isSocial =false;

    private LayoutInflater mInflater;
    public SpecialSellingGalleryAdapter(Context context,
                                        List<JumpableImage> bigImageList) {
        super(context, R.layout.jumei_mall_gallery_item);
        this.list = (ArrayList<JumpableImage>) bigImageList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public SpecialSellingGalleryAdapter(Context context,
                                        List<JumpableImage> bigImageList, boolean isSocial) {
        this(context,bigImageList);
        this.isSocial = isSocial;
    }

    @Override
    public int getCount() {
        if(list.size()==1){
            return 1;
        }
        return Integer.MAX_VALUE;
    }
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        ImagesHolder holder = null;
        if(convertView == null) {
            holder = new ImagesHolder(convertView);
            convertView = mInflater.inflate(R.layout.jumei_mall_gallery_item, parent, false);
            holder.productImages=(CompactImageView) convertView.findViewById(R.id.product_image);
//			holder.social_special = convertView.findViewById(R.id.social_special_bg);
            convertView.setTag(holder);
        } else {
            holder = (ImagesHolder) convertView.getTag();
        }
//		holder.productImages.setBackgroundResource(R.drawable.weiboshare_bg);
//		holder.productImages.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.zhanweitu));
        if(null != list && list.size()>0 && !TextUtils.isEmpty(list.get(position % list.size()).img)){
            //holder.productImages.setImageUrl(list.get(position % list.size()).img,context.getImageFactory(),false);
//            Picasso.with(context).load(list.get(position % list.size()).img).into(holder.productImages);
            String url = "http://n.sinaimg.cn/transform/20150526/splR-avxeafs8127570.jpg";
//            url = list.get(position % list.size()).img;
            ImageLoaderCompact.getInstance().displayImage(context, url
                    , holder.productImages);
        } else {
            holder.productImages.setImageBitmap(null);
        }
//		if(isSocial){
//			holder.social_special.setVisibility(View.VISIBLE);
//		}else{
//            holder.social_special.setVisibility(View.GONE);
//        }

//		context.inflateImageBackgroundDrawbleExtForGallery(list.get(position % list.size()).img, holder.productImages);
        return convertView;
    }


    static class ImagesHolder {
        private View base;
        private CompactImageView productImages;
        private View social_special;

        public ImagesHolder(View base) {
            this.base = base;

        }

//		public ImageView getproductImages() {
//			if (productImages == null)
//				productImages = (UrlImageView) base
//						.findViewById(R.id.product_image);
//
//			return productImages;
//		}

    }
}