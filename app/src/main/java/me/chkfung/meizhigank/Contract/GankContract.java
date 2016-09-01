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

package me.chkfung.meizhigank.Contract;

import me.chkfung.meizhigank.Base.BaseContract;
import me.chkfung.meizhigank.Model.Day;

/**
 * Gank Activity MVP Contract
 * Created by Fung on 04/08/2016.
 */

public interface GankContract {
    interface Presenter extends BaseContract.Presenter {
        void getGank(String date);
    }

    interface View extends BaseContract.View {
        void setupRecycleView(Day day);
        void showError(Throwable e);

        void startCustomTabIntent(String Desc, String url);
    }
}
