package me.chkfung.meizhigank.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fung on 04/08/2016.
 */

public class Day {

    /**
     * category : ["iOS","Android","瞎推荐","拓展资源","福利","RestVideo"]
     * error : false
     * results : {"Android":[{"_id":"56cc6d23421aa95caa707a69","createdAt":"2015-08-06T07:15:52.65Z","desc":"类似Link Bubble的悬浮式操作设计","publishedAt":"2015-08-07T03:57:48.45Z","type":"Android","url":"https://github.com/recruit-lifestyle/FloatingView","used":true,"who":"mthli"},{"_id":"56cc6d23421aa95caa707a6f","createdAt":"2015-08-07T01:33:07.815Z","desc":"Android开发中，有哪些让你觉得相见恨晚的方法、类或接口？","publishedAt":"2015-08-07T03:57:47.317Z","type":"Android","url":"http://www.zhihu.com/question/33636939","used":true,"who":"lxxself"},{"_id":"56cc6d23421aa95caa707c69","createdAt":"2015-08-06T15:00:38.350Z","desc":"使用注解来处理Activity的状态恢复","publishedAt":"2015-08-07T03:57:48.76Z","type":"Android","url":"https://github.com/tom91136/Akatsuki","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707c71","createdAt":"2015-08-07T02:19:44.342Z","desc":"Android Lollipop联系人之PinnedListView简单使用","publishedAt":"2015-08-07T03:57:48.142Z","type":"Android","url":"https://git.oschina.net/way/PinnedHeaderListView","used":true,"who":"有时放纵"},{"_id":"56cc6d23421aa95caa707c78","createdAt":"2015-08-06T14:58:28.171Z","desc":"图片可以自动滚动的ImageView，可以实现视差效果。","publishedAt":"2015-08-07T03:57:48.73Z","type":"Android","url":"https://github.com/Q42/AndroidScrollingImageView","used":true,"who":"鲍永章"}],"iOS":[{"_id":"56cc6d1d421aa95caa707769","createdAt":"2015-08-07T01:32:51.588Z","desc":"LLVM 简介","publishedAt":"2015-08-07T03:57:48.70Z","type":"iOS","url":"http://adriansampson.net/blog/llvm.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707a6b","createdAt":"2015-08-06T14:45:18.733Z","desc":"基于TextKit的UILabel，支持超链接和自定义表达式。","publishedAt":"2015-08-07T03:57:47.242Z","type":"iOS","url":"https://github.com/molon/MLLabel","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707bea","createdAt":"2015-08-07T01:33:30.871Z","desc":"Swift 和 C 函数","publishedAt":"2015-08-07T03:57:48.83Z","type":"iOS","url":"http://chris.eidhof.nl/posts/swift-c-interop.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707c77","createdAt":"2015-08-07T01:34:00.984Z","desc":"Arrays Linked Lists 和性能比较","publishedAt":"2015-08-07T03:57:48.174Z","type":"iOS","url":"http://airspeedvelocity.net/2015/08/03/arrays-linked-lists-and-performance/","used":true,"who":"CallMeWhy"}],"RestVideo":[{"_id":"56cc6d23421aa95caa707c68","createdAt":"2015-08-06T13:06:17.211Z","desc":"听到就心情大好的歌，简直妖魔哈哈哈哈哈，原地址\nhttp://v.youku.com/v_show/id_XMTQxODA5NDM2.html","publishedAt":"2015-08-07T03:57:48.104Z","type":"RestVideo","url":"http://www.zhihu.com/question/21778055/answer/19905413?utm_source=weibo&utm_medium=weibo_share&utm_content=share_answer&utm_campaign=share_button","used":true,"who":"lxxself"}],"拓展资源":[{"_id":"56cc6d23421aa95caa707bdf","createdAt":"2015-08-07T01:36:06.932Z","desc":"Display GitHub code in tree format","publishedAt":"2015-08-07T03:57:48.81Z","type":"拓展资源","url":"https://github.com/buunguyen/octotree","used":true,"who":"lxxself"}],"瞎推荐":[{"_id":"56cc6d23421aa95caa707bd0","createdAt":"2015-08-07T01:52:30.267Z","desc":"程序员的电台FmM，这个页面chrome插件有问题啊哭，我写了回删除不了啊","publishedAt":"2015-08-07T03:57:48.84Z","type":"瞎推荐","url":"https://cmd.fm/","used":true,"who":"lxxself"}],"福利":[{"_id":"56cc6d23421aa95caa707c52","createdAt":"2015-08-07T01:21:06.112Z","desc":"8.7\u2014\u2014（1）","publishedAt":"2015-08-07T03:57:47.310Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d23421aa95caa707c56","createdAt":"2015-08-07T01:21:33.518Z","desc":"8.7\u2014\u2014（2）","publishedAt":"2015-08-07T03:57:47.229Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutsd0pgiwj20go0p0djn.jpg","used":true,"who":"张涵宇"}]}
     */

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

    public static class ResultsBean {
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

        private List<DataBean> Android;
        /**
         * _id : 56cc6d1d421aa95caa707769
         * createdAt : 2015-08-07T01:32:51.588Z
         * desc : LLVM 简介
         * publishedAt : 2015-08-07T03:57:48.70Z
         * type : iOS
         * url : http://adriansampson.net/blog/llvm.html
         * used : true
         * who : CallMeWhy
         */

        private List<DataBean> iOS;
        /**
         * _id : 56cc6d23421aa95caa707c68
         * createdAt : 2015-08-06T13:06:17.211Z
         * desc : 听到就心情大好的歌，简直妖魔哈哈哈哈哈，原地址
         * http://v.youku.com/v_show/id_XMTQxODA5NDM2.html
         * publishedAt : 2015-08-07T03:57:48.104Z
         * type : RestVideo
         * url : http://www.zhihu.com/question/21778055/answer/19905413?utm_source=weibo&utm_medium=weibo_share&utm_content=share_answer&utm_campaign=share_button
         * used : true
         * who : lxxself
         */

        @SerializedName("休息视频")
        private List<DataBean> RestVideo;
        /**
         * _id : 56cc6d23421aa95caa707bdf
         * createdAt : 2015-08-07T01:36:06.932Z
         * desc : Display GitHub code in tree format
         * publishedAt : 2015-08-07T03:57:48.81Z
         * type : 拓展资源
         * url : https://github.com/buunguyen/octotree
         * used : true
         * who : lxxself
         */

        @SerializedName("拓展资源")
        private List<DataBean> Extra;
        /**
         * _id : 56cc6d23421aa95caa707bd0
         * createdAt : 2015-08-07T01:52:30.267Z
         * desc : 程序员的电台FmM，这个页面chrome插件有问题啊哭，我写了回删除不了啊
         * publishedAt : 2015-08-07T03:57:48.84Z
         * type : 瞎推荐
         * url : https://cmd.fm/
         * used : true
         * who : lxxself
         */

        @SerializedName("瞎推荐")
        private List<DataBean> Recommend;
        /**
         * _id : 56cc6d23421aa95caa707c52
         * createdAt : 2015-08-07T01:21:06.112Z
         * desc : 8.7——（1）
         * publishedAt : 2015-08-07T03:57:47.310Z
         * type : 福利
         * url : http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg
         * used : true
         * who : 张涵宇
         */

        @SerializedName("福利")
        private List<DataBean> Fuli;

        public List<DataBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<DataBean> Android) {
            this.Android = Android;
        }

        public List<DataBean> getIOS() {
            return iOS;
        }

        public void setIOS(List<DataBean> iOS) {
            this.iOS = iOS;
        }

        public List<DataBean> getRestVideo() {
            return RestVideo;
        }

        public void setRestVideo(List<DataBean> restVideo) {
            this.RestVideo = restVideo;
        }

        public List<DataBean> getExtra() {
            return Extra;
        }

        public void setExtra(List<DataBean> extra) {
            this.Extra = extra;
        }

        public List<DataBean> getRecommend() {
            return Recommend;
        }

        public void setRecommend(List<DataBean> recommend) {
            this.Recommend = recommend;
        }

        public List<DataBean> getFuli() {
            return Fuli;
        }

        public void setFuli(List<DataBean> fuli) {
            this.Fuli = fuli;
        }

        public static class DataBean {
            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private boolean used;
            private String who;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public boolean isUsed() {
                return used;
            }

            public void setUsed(boolean used) {
                this.used = used;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }
        }

    }
}
