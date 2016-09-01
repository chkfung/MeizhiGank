package me.chkfung.meizhigank.ui.AdapterCallBack;

import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

import me.chkfung.meizhigank.Model.DataInfo;

/**
 * Created by Fung on 23/08/2016.
 */

public class MeizhiDiffUtil extends DiffUtil.Callback {
    private final List<DataInfo> oldMeizhi;
    private final List<DataInfo> newMeizhi;

    public MeizhiDiffUtil(List<DataInfo> oldMeizhi, List<DataInfo> newMeizhi) {
        this.oldMeizhi = oldMeizhi;
        this.newMeizhi = newMeizhi;
    }

    @Override
    public int getOldListSize() {
        return oldMeizhi.size();
    }

    @Override
    public int getNewListSize() {
        return newMeizhi.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldMeizhi.get(oldItemPosition).get_id(), newMeizhi.get(newItemPosition).get_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldMeizhi.get(oldItemPosition).getUrl(), newMeizhi.get(newItemPosition).getUrl());
    }
}
