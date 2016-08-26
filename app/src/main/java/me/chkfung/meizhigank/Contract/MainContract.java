package me.chkfung.meizhigank.Contract;

import java.util.List;

import me.chkfung.meizhigank.Base.BaseContract;
import me.chkfung.meizhigank.Model.DataInfo;

/**
 * Created by Fung on 21/07/2016.
 */

public interface MainContract {
    interface Presenter extends BaseContract.Presenter<View> {
        /**
         * Perform Network Request to Obtain Meizhi Data
         * @param page Current Page Number
         * @param MeizhiData Current Meizhi Data
         */
        void loadMeizhi(int page, List<DataInfo> MeizhiData);
    }

    interface View extends BaseContract.View {

        /**
         * Update Adapter
         *
         * @param arrayList Meizhi
         */
        void refreshRv(List<DataInfo> arrayList);

        /**
         * UI Action when Network Error
         * @param e Throwable
         */
        void networkError(Throwable e);

        /**
         * Summon Cute Meizhi prrrrr
         * @param clearItem true = Reset MeizhiData, false = Add Meizhi Data
         */
        void summonMeizhi(boolean clearItem);

        void animateToolbar();
    }
}
