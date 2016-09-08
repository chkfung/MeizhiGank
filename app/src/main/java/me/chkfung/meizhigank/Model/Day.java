/*
 * Meizhi & Gank.io
 * Copyright (C) 2016 ChkFung
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.chkfung.meizhigank.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Day Object is used to parse Gank Activity Response
 * Created by Fung on 04/08/2016.
 */

public class Day implements Parcelable {

    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
    /**
     * category : ["iOS","Android","瞎推荐","拓展资源","福利","RestVideo"]
     * error : false
     * results : {"Android":[{"_id":"56cc6d23421aa95caa707a69","createdAt":"2015-08-06T07:15:52.65Z","desc":"类似Link Bubble的悬浮式操作设计","publishedAt":"2015-08-07T03:57:48.45Z","type":"Android","url":"https://github.com/recruit-lifestyle/FloatingView","used":true,"who":"mthli"},{"_id":"56cc6d23421aa95caa707a6f","createdAt":"2015-08-07T01:33:07.815Z","desc":"Android开发中，有哪些让你觉得相见恨晚的方法、类或接口？","publishedAt":"2015-08-07T03:57:47.317Z","type":"Android","url":"http://www.zhihu.com/question/33636939","used":true,"who":"lxxself"},{"_id":"56cc6d23421aa95caa707c69","createdAt":"2015-08-06T15:00:38.350Z","desc":"使用注解来处理Activity的状态恢复","publishedAt":"2015-08-07T03:57:48.76Z","type":"Android","url":"https://github.com/tom91136/Akatsuki","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707c71","createdAt":"2015-08-07T02:19:44.342Z","desc":"Android Lollipop联系人之PinnedListView简单使用","publishedAt":"2015-08-07T03:57:48.142Z","type":"Android","url":"https://git.oschina.net/way/PinnedHeaderListView","used":true,"who":"有时放纵"},{"_id":"56cc6d23421aa95caa707c78","createdAt":"2015-08-06T14:58:28.171Z","desc":"图片可以自动滚动的ImageView，可以实现视差效果。","publishedAt":"2015-08-07T03:57:48.73Z","type":"Android","url":"https://github.com/Q42/AndroidScrollingImageView","used":true,"who":"鲍永章"}],"iOS":[{"_id":"56cc6d1d421aa95caa707769","createdAt":"2015-08-07T01:32:51.588Z","desc":"LLVM 简介","publishedAt":"2015-08-07T03:57:48.70Z","type":"iOS","url":"http://adriansampson.net/blog/llvm.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707a6b","createdAt":"2015-08-06T14:45:18.733Z","desc":"基于TextKit的UILabel，支持超链接和自定义表达式。","publishedAt":"2015-08-07T03:57:47.242Z","type":"iOS","url":"https://github.com/molon/MLLabel","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707bea","createdAt":"2015-08-07T01:33:30.871Z","desc":"Swift 和 C 函数","publishedAt":"2015-08-07T03:57:48.83Z","type":"iOS","url":"http://chris.eidhof.nl/posts/swift-c-interop.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707c77","createdAt":"2015-08-07T01:34:00.984Z","desc":"Arrays Linked Lists 和性能比较","publishedAt":"2015-08-07T03:57:48.174Z","type":"iOS","url":"http://airspeedvelocity.net/2015/08/03/arrays-linked-lists-and-performance/","used":true,"who":"CallMeWhy"}],"RestVideo":[{"_id":"56cc6d23421aa95caa707c68","createdAt":"2015-08-06T13:06:17.211Z","desc":"听到就心情大好的歌，简直妖魔哈哈哈哈哈，原地址\nhttp://v.youku.com/v_show/id_XMTQxODA5NDM2.html","publishedAt":"2015-08-07T03:57:48.104Z","type":"RestVideo","url":"http://www.zhihu.com/question/21778055/answer/19905413?utm_source=weibo&utm_medium=weibo_share&utm_content=share_answer&utm_campaign=share_button","used":true,"who":"lxxself"}],"拓展资源":[{"_id":"56cc6d23421aa95caa707bdf","createdAt":"2015-08-07T01:36:06.932Z","desc":"Display GitHub code in tree format","publishedAt":"2015-08-07T03:57:48.81Z","type":"拓展资源","url":"https://github.com/buunguyen/octotree","used":true,"who":"lxxself"}],"瞎推荐":[{"_id":"56cc6d23421aa95caa707bd0","createdAt":"2015-08-07T01:52:30.267Z","desc":"程序员的电台FmM，这个页面chrome插件有问题啊哭，我写了回删除不了啊","publishedAt":"2015-08-07T03:57:48.84Z","type":"瞎推荐","url":"https://cmd.fm/","used":true,"who":"lxxself"}],"福利":[{"_id":"56cc6d23421aa95caa707c52","createdAt":"2015-08-07T01:21:06.112Z","desc":"8.7\u2014\u2014（1）","publishedAt":"2015-08-07T03:57:47.310Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d23421aa95caa707c56","createdAt":"2015-08-07T01:21:33.518Z","desc":"8.7\u2014\u2014（2）","publishedAt":"2015-08-07T03:57:47.229Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutsd0pgiwj20go0p0djn.jpg","used":true,"who":"张涵宇"}]}
     */


    @SerializedName("error")
    private boolean error;

    @SerializedName("results")
    private ResultsBean results;

    @SerializedName("category")
    private List<String> category;

    @SuppressWarnings("unused")
    public Day() {
    }

    private Day(Parcel in) {
        this.error = in.readByte() != 0;
        this.results = in.readParcelable(ResultsBean.class.getClassLoader());
        this.category = in.createStringArrayList();
    }

    @SuppressWarnings("unused")
    public boolean isError() {
        return error;
    }

    @SuppressWarnings("unused")
    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    @SuppressWarnings("unused")
    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    public void setCategory(List<String> category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.results, flags);
        dest.writeStringList(this.category);
    }

    public static class ResultsBean implements Parcelable {
        public static final Parcelable.Creator<ResultsBean> CREATOR = new Parcelable.Creator<ResultsBean>() {
            @Override
            public ResultsBean createFromParcel(Parcel source) {
                return new ResultsBean(source);
            }

            @Override
            public ResultsBean[] newArray(int size) {
                return new ResultsBean[size];
            }
        };
        /**
         * _id : 56cc6d23421aa95caa707a69
         * createdAt : 2015-08-06T07:15:52.65Z
         * desc : 类似Link Bubble的悬浮式操作设计
         * publishedAt : 2015-08-07T03:57:48.45Z
         * type : Android
         * url : https://github.com/recruit-lifestyle/FloatingView
         * used : true
         * who : mthli
         */

        @SerializedName("Android")
        private List<DataInfo> Android;
        @SerializedName("iOS")
        private List<DataInfo> iOS;
        @SerializedName("App")
        private List<DataInfo> App;
        @SerializedName("前端")
        private List<DataInfo> FrontEnd;
        @SerializedName("休息视频")
        private List<DataInfo> RestVideo;
        @SerializedName("拓展资源")
        private List<DataInfo> Extra;

        //        @SerializedName("福利")
//        private List<DataBean> Fuli;
        @SerializedName("瞎推荐")
        private List<DataInfo> Recommend;

        public ResultsBean() {
        }

        ResultsBean(Parcel in) {
            this.Android = in.createTypedArrayList(DataInfo.CREATOR);
            this.iOS = in.createTypedArrayList(DataInfo.CREATOR);
            this.App = in.createTypedArrayList(DataInfo.CREATOR);
            this.RestVideo = in.createTypedArrayList(DataInfo.CREATOR);
            this.Extra = in.createTypedArrayList(DataInfo.CREATOR);
            this.Recommend = in.createTypedArrayList(DataInfo.CREATOR);
        }

        public List<DataInfo> getAndroid() {
            return Android;
        }

        @SuppressWarnings("unused")
        public void setAndroid(List<DataInfo> Android) {
            this.Android = Android;
        }

        public List<DataInfo> getIOS() {
            return iOS;
        }

        @SuppressWarnings("unused")
        public void setIOS(List<DataInfo> iOS) {
            this.iOS = iOS;
        }

        public List<DataInfo> getApp() {
            return App;
        }

        @SuppressWarnings("unused")
        public void setApp(List<DataInfo> App) {
            this.App = App;
        }

        public List<DataInfo> getFrontEnd() {
            return FrontEnd;
        }

        @SuppressWarnings("unused")
        public void setFrontEnd(List<DataInfo> frontEnd) {
            FrontEnd = frontEnd;
        }

        public List<DataInfo> getRestVideo() {
            return RestVideo;
        }

        @SuppressWarnings("unused")
        public void setRestVideo(List<DataInfo> restVideo) {
            this.RestVideo = restVideo;
        }

        public List<DataInfo> getExtra() {
            return Extra;
        }

//        public List<DataBean> getFuli() {
//            return Fuli;
//        }
//
//        public void setFuli(List<DataBean> fuli) {
//            this.Fuli = fuli;
//        }

        @SuppressWarnings("unused")
        public void setExtra(List<DataInfo> extra) {
            this.Extra = extra;
        }

        public List<DataInfo> getRecommend() {
            return Recommend;
        }

        @SuppressWarnings("unused")
        public void setRecommend(List<DataInfo> recommend) {
            this.Recommend = recommend;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.Android);
            dest.writeTypedList(this.iOS);
            dest.writeTypedList(this.App);
            dest.writeTypedList(this.RestVideo);
            dest.writeTypedList(this.Extra);
            dest.writeTypedList(this.Recommend);
        }

    }
}
