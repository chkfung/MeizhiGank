package me.chkfung.meizhigank.Util;

import java.util.List;

import me.chkfung.meizhigank.Model.Day;

/**
 * Created by Fung on 16/08/2016.
 */

public class CommonUtil {

    public static List<Day.ResultsBean.DataBean> getDataOf(Day data, String selectedItem) {
        switch (selectedItem) {
            case "Android":
                return data.getResults().getAndroid();
            case "iOS":
                return data.getResults().getIOS();
            case "App":
                return data.getResults().getApp();
            case "休息视频":
                return data.getResults().getRestVideo();
            case "拓展资源":
                return data.getResults().getExtra();
            case "瞎推荐":
                return data.getResults().getRecommend();
            case "前端":
                return data.getResults().getFrontEnd();
            default:
                return null;
        }
    }
}
