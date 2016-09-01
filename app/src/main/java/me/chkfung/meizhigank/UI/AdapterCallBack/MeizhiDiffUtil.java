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

package me.chkfung.meizhigank.ui.AdapterCallBack;

import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

import me.chkfung.meizhigank.Model.DataInfo;

/**
 * New Util introduce in Support 24.2.0
 * Still Figuring Use cases
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
