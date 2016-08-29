package me.chkfung.meizhigank.UI.AdapterCallBack;

import android.support.v7.util.DiffUtil;

import me.chkfung.meizhigank.Model.Day;

/**
 * Created by Fung on 29/08/2016.
 */

public class GankDiffUtil extends DiffUtil.Callback {
    Day oldData;
    Day newData;

    public GankDiffUtil(Day oldData, Day newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.getCategory().size();
    }

    @Override
    public int getNewListSize() {
        return newData.getCategory().size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.getCategory().get(oldItemPosition) == newData.getCategory().get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.getCategory().get(oldItemPosition) == newData.getCategory().get(newItemPosition);
    }
}
