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

import android.view.MenuItem;

import java.util.ArrayList;

import me.chkfung.meizhigank.Base.BaseContract;
import me.chkfung.meizhigank.Model.DataInfo;

/**
 * Main Activity MVP Contract
 * Created by Fung on 21/07/2016.
 */

public interface MainContract {
    interface Presenter extends BaseContract.Presenter {
        /**
         * Perform Network Request to Obtain Meizhi Data
         * @param page Current Page Number
         * @param MeizhiData Current Meizhi Data
         */
        @SuppressWarnings("unused")
        void loadMeizhi(int page, ArrayList<DataInfo> MeizhiData);
    }

    interface View extends BaseContract.View {

        /**
         * Update Adapter
         *
         */
        void refreshRv(ArrayList<DataInfo> TempData);

        /**
         * UI Action when Network Error
         * @param e Throwable
         */
        void networkError(Throwable e);

        /**
         * Summon Cute Meizhi prrrrr
         * @param clearItem true = Reset MeizhiData, false = Add Meizhi Data
         */
        @SuppressWarnings("unused")
        void summonMeizhi(boolean clearItem);

        void firstTimelaunch();

        void tutorial();

        void setDayNightMode(boolean day);

        void setupAlarm(boolean enabled);

        void setMenuIcon(MenuItem item);
    }
}
