package me.chkfung.meizhigank.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by Fung on 21/07/2016.
 */

public class Meizhi {

    /**
     * error : false
     * results : [{"_id":"57918b5c421aa90d2fc94b35","createdAt":"2016-07-22T10:56:28.274Z","desc":"恐龙爪子萌妹子","publishedAt":"2016-07-22T11:04:44.305Z","source":"web","type":"福利","url":"http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg","used":true,"who":"代码家"},{"_id":"578f93c4421aa90de83c1bf4","createdAt":"2016-07-20T23:07:48.480Z","desc":"7.21","publishedAt":"2016-07-20T16:09:07.721Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f60rw11f5mj20iy0sg0u2.jpg","used":true,"who":"daimajia"},{"_id":"578e3d3b421aa90df33fe7e8","createdAt":"2016-07-19T22:46:19.966Z","desc":"7.20","publishedAt":"2016-07-20T17:25:17.522Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f5zlndsr0oj20go0ciq4h.jpg","used":true,"who":"代码家"},{"_id":"578da129421aa90dea11ea28","createdAt":"2016-07-19T11:40:25.857Z","desc":"7.19","publishedAt":"2016-07-19T12:13:10.925Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f5z2eko5xnj20f00miq5s.jpg","used":true,"who":"代码家"},{"_id":"578c4ecf421aa90dea11ea17","createdAt":"2016-07-18T11:36:47.363Z","desc":"7.18","publishedAt":"2016-07-18T11:49:19.322Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f5xwnxj2vmj20dw0dwjsc.jpg","used":true,"who":"代码家"},{"_id":"57885b8c421aa90de83c1b96","createdAt":"2016-07-15T11:42:04.295Z","desc":"7.15","publishedAt":"2016-07-15T11:56:07.907Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f5ufyzg2ajj20ck0kuq5e.jpg","used":true,"who":"代码家"},{"_id":"5786f935421aa90df638bb2a","createdAt":"2016-07-14T10:30:13.329Z","desc":"馬場 ふみか","publishedAt":"2016-07-14T11:39:49.710Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f5t889dhpoj20f00mi414.jpg","used":true,"who":"代码家"},{"_id":"5785bb72421aa90df638bb19","createdAt":"2016-07-13T11:54:26.657Z","desc":"7.13","publishedAt":"2016-07-13T12:10:33.380Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f5s5382uokj20fk0ncmyt.jpg","used":true,"who":"代码家"},{"_id":"57846575421aa90de83c1b6a","createdAt":"2016-07-12T11:35:17.922Z","desc":"7.12","publishedAt":"2016-07-12T12:00:59.523Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f5qyx2wpohj20xc190tr7.jpg","used":true,"who":"代码家"},{"_id":"578319de421aa90dea11e9ac","createdAt":"2016-07-11T12:00:30.299Z","desc":"佐佐木希","publishedAt":"2016-07-11T12:27:19.231Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034gw1f5pu0w0r56j20m80rsjuy.jpg","used":true,"who":"代码家"}]
     */

    private boolean error;
    /**
     * _id : 57918b5c421aa90d2fc94b35
     * createdAt : 2016-07-22T10:56:28.274Z
     * desc : 恐龙爪子萌妹子
     * publishedAt : 2016-07-22T11:04:44.305Z
     * source : web
     * type : 福利
     * url : http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg
     * used : true
     * who : 代码家
     */

    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String _id;
        private String createdAt;
        private String desc;
        private Date publishedAt;
        private String source;
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

        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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
