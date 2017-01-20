package com.freedom.lauzy.gankpro.function.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * bean
 * Created by Lauzy on 2017/1/20.
 */
@SuppressWarnings("unused")
public class DailyData {


    private boolean error;
    private ResultsBean results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    private static class ResultsBean {
        @SerializedName("Android")
        private List<ItemBean> androidList;
        @SerializedName("休息视频")
        private List<ItemBean> restVideoList;
        @SerializedName("iOS")
        private List<ItemBean> iOSList;
        @SerializedName("福利")
        private List<ItemBean> beautyList;
        @SerializedName("拓展资源")
        private List<ItemBean> expandList;
        @SerializedName("瞎推荐")
        private List<ItemBean> recommendList;
        @SerializedName("App")
        private List<ItemBean> appList;

        public List<ItemBean> getAndroidList() {
            return androidList;
        }

        public void setAndroidList(List<ItemBean> androidList) {
            this.androidList = androidList;
        }

        public List<ItemBean> getRestVideoList() {
            return restVideoList;
        }

        public void setRestVideoList(List<ItemBean> restVideoList) {
            this.restVideoList = restVideoList;
        }

        public List<ItemBean> getiOSList() {
            return iOSList;
        }

        public void setiOSList(List<ItemBean> iOSList) {
            this.iOSList = iOSList;
        }

        public List<ItemBean> getBeautyList() {
            return beautyList;
        }

        public void setBeautyList(List<ItemBean> beautyList) {
            this.beautyList = beautyList;
        }

        public List<ItemBean> getExpandList() {
            return expandList;
        }

        public void setExpandList(List<ItemBean> expandList) {
            this.expandList = expandList;
        }

        public List<ItemBean> getRecommendList() {
            return recommendList;
        }

        public void setRecommendList(List<ItemBean> recommendList) {
            this.recommendList = recommendList;
        }

        public List<ItemBean> getAppList() {
            return appList;
        }

        public void setAppList(List<ItemBean> appList) {
            this.appList = appList;
        }
    }
}
