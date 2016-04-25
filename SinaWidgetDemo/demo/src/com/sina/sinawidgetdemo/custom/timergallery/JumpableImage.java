package com.sina.sinawidgetdemo.custom.timergallery;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: include focus image,banner,advertisement,metro and all can jumpable image
 * @author hem@jumei.com   
 * @date 2013年10月25日 下午1:48:53
 */
//img                    :纯图片：          type=img, img=图片链接
//text                   :纯文字：          type=text ，img=图片链接 ,        		content=  rangd
//product                :图片+单个商品：   type=product，img=图片链接，      		product_id=产品ID
//deal                   :图片+单个DEAL       type=deal,img=图片链接, 			hash_id=deal的hash_id                        跳转到详情
//activity               :图片+活动标识：   type=activity，img=图片链接，     		category=mall ,label={商城活动标识} category=deal, label={团购活动标识}
//  特殊说明：现在采用的是 label  ，历史版本可能会采用activity  link  content 
//category               :图片+分类链接：   type=category，img=图片链接，     		brand_id=&name= 或 function_id=&name= 或 category_id=&name=  或 search=关键字 
//mobile_activity        :无线专场          type=mobile_activity，img=图片链接 , 		content=无线专场活动标识
//store_domain           :图片+旗舰店标识， type=store_domain，img=图片链接 , 		store_domain 旗舰店标识
//mobile_flow            :流量活动               type=mobile_flow ，img=图片链接 , 		content=webview活动标识, description=广告标题                        跳转到卫平界面
//img_pop                :图片+POP页面 名品专场  type=img_pop，partner_id=专场ID                                                 跳转到名品列表
//img_url                :图片+链接          type=img_url,img=图片链接 ,			url=跳转网址                                                                                             //http webview   // jumeimall ://
//mobile_webview         :webview活动标识        type=mobile_webview ，img=图片链接 , 		content=webview活动标识, description=广告标题   
//
//
//---
//
//customize              :自定义：          type=customize ， content=         不支持
//img_customized         :图片+自定义内容    type=img_customized ，img=图片链接 , 		content=内容            不支持
//store_domain_with_brand:品牌+旗舰店标识， type=store_domain_with_brand，img=图片链接 ,  brand_id=品牌ID,store_domain旗舰店标识                 不支持
//brand_with_keywords    :品牌+搜索关键，   type=brand_with_keywords，img=图片链接 , 	brand_id=品牌ID,search搜索关键字                                                不支持
//如果是metro广告位会增加metro字段
//如果是商城楼层推荐广告位会增加show_style

public class JumpableImage implements Serializable{
	private static final long serialVersionUID = 8028777851822235466L;
	public enum JUMP_TYPE{
		IMG("img"), PRODUCT("product"), ACTIVITY("activity"),GLOBAL_ACTIVITY("global_activity"), CATEGORY("category"), 
			DEAL("deal"), STORE_DOMAIN("store_domain"), IMG_POP("img_pop"),IMG_URL("img_url"),
				MOBILE_ACTIVITY("mobile_activity"), TEXT("text"), MOBILE_FLOW("mobile_flow"),
					MOBILE_WEBVIEW("mobile_webview"),NONE("null"),WORDS_URL("words_url"),WORDS_LABEL("words_label");
		private String mType;
		private static Map<String,JUMP_TYPE> mText2VauleMap = new HashMap<String, JUMP_TYPE>();
		private JUMP_TYPE(){}
		private JUMP_TYPE(String type){
			mType = type;
		}
		public String getTypeText(){
			return mType;
		}
		public static JUMP_TYPE getJumpTypeByText(String text){
			if(mText2VauleMap.isEmpty()){
				for(JUMP_TYPE jumpType : JUMP_TYPE.values()){
					mText2VauleMap.put(jumpType.getTypeText(), jumpType);
				}
			}
			return mText2VauleMap.get(text);
		}
	}
	//侧边栏搜索 可配置的分类
	public String categoryTitle;
    public String categorySub_title;
    public String categoryImage;
    public String categoryType;
    public String like_type;
    public List<JumpableImage> category_group=new ArrayList<JumpableImage>();
    public List<JumpableImage> function_group=new ArrayList<JumpableImage>();
    
    
	//common property START
	private String type="";
	public JUMP_TYPE jumpType;
	public String content="";
	public String link="";
	public String img="";
	public String category="";
	public String activity="";
	public String label="";
	public String description="";
	public String words="";
	//common property END
	
	//metro property START
	public String category_id="";
	public String metro="";
	public String name="";
	public String store_domain="";
	public String product_id="";
	public String function_id="";
	public String brand_id="";
	public String search="";
	//metro property END
	
	//2.0 extend
	public String partner_id="";
	public String hash_id="";
	public String webview="";
	public String url = "";
    public int show_times = 0;
	//2.0extend 
	public String materialId = "";
	public String materialDescription = "";
	//用于广告位返回的id
	public String ad_id = "";
	//所在的页面
	public String crrent_page = "";
	//当前JumpableImage的类型,有material/ad
	public String crrent_type = "";
	
	public String biTag = "";		//用作BI数据统计
	public String cardId = "";		//所在card
	
	public boolean isGroupMetro = false;
	
	public String card_Type = "";
    public boolean isFirst = true;
	public boolean isLast = true;
	
	/**eagele param added by chaoranf */
	public String eageleFPA = "";//。。。居然拼错了，，我错了
	public String eagleFp = "";
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(TextUtils.isEmpty(type)){
			type = "";
		}
		this.type = type;
		jumpType = JUMP_TYPE.getJumpTypeByText(type) == null ? JUMP_TYPE.NONE : JUMP_TYPE.getJumpTypeByText(type);
	}
	
	public String getFlag(){
		return content + img + link + getType();
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JUMP_TYPE getJumpType() {
		return jumpType;
	}
}
