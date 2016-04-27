package com.sina.sinawidgetdemo.custom.view.livelistview;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchonghui on 16/4/27.
 */
public class CommonLiveResp {

    private CommonLive live;

    public CommonLive getLive() {
        return live;
    }

    public void setLive(CommonLive live) {
        this.live = live;
    }

    public void onParsed() {
    }


    public static class CommonLive{
        private String title;
        private String status;
        private String moreUrl;
        private List<CommonLiveItem> list = new ArrayList<CommonLiveItem>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMoreUrl() {
            return moreUrl;
        }

        public void setMoreUrl(String moreUrl) {
            this.moreUrl = moreUrl;
        }

        public List<CommonLiveItem> getList() {
            return list;
        }

        public void setList(List<CommonLiveItem> list) {
            this.list = list;
        }
    }

    public static class CommonLiveItem{

        private String isFollow;
        private String no;
        private String svip;
        private String authLogo;
        private String roomId;
        private String avatar;
        private String anchorId;
        private String imId;
        private String title;
        private String cover;
        private String nickName;
        private String viewerCount;
        private String hot;
        private String authInfo;
        private String roomUrl;
        private String signature;

        public String getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(String isFollow) {
            this.isFollow = isFollow;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getSvip() {
            return svip;
        }

        public void setSvip(String svip) {
            this.svip = svip;
        }

        public String getAuthLogo() {
            return authLogo;
        }

        public void setAuthLogo(String authLogo) {
            this.authLogo = authLogo;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(String anchorId) {
            this.anchorId = anchorId;
        }

        public String getImId() {
            return imId;
        }

        public void setImId(String imId) {
            this.imId = imId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getViewerCount() {
            return viewerCount;
        }

        public void setViewerCount(String viewerCount) {
            this.viewerCount = viewerCount;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getAuthInfo() {
            return authInfo;
        }

        public void setAuthInfo(String authInfo) {
            this.authInfo = authInfo;
        }

        public String getRoomUrl() {
            return roomUrl;
        }

        public void setRoomUrl(String roomUrl) {
            this.roomUrl = roomUrl;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

}

