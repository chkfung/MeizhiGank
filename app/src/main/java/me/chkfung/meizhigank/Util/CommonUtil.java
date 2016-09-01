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

package me.chkfung.meizhigank.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

import java.util.List;

import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.Model.Day;

/**
 * Created by Fung on 16/08/2016.
 */

public class CommonUtil {

    public static List<DataInfo> getDataOf(Day data, String selectedItem) {
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

    public static void Share(Context context, String Title, String url, String ChooserText) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, Title);
        i.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(Intent.createChooser(i, ChooserText));
    }

    public static void FancyAnimation(View v) {
        v.setAlpha(0);
        v.setScaleX(0.6f);
        v.animate().alpha(1)
                .scaleX(1f)
                .setStartDelay(300)
                .setDuration(900)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
    }
}
