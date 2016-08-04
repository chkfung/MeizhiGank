package me.chkfung.meizhigank.Contract;

import me.chkfung.meizhigank.Base.BaseContract;

/**
 * Created by Fung on 04/08/2016.
 */

public interface GankContract {
    interface Presenter extends BaseContract.Presenter<View> {
        void getGank(String date);
    }

    interface View extends BaseContract.View {

    }
}
