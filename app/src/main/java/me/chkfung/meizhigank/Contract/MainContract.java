package me.chkfung.meizhigank.Contract;

import java.util.List;

import me.chkfung.meizhigank.Base.BaseContract;
import me.chkfung.meizhigank.Model.Meizhi;

/**
 * Created by Fung on 21/07/2016.
 */

public interface MainContract {
    public interface Presenter extends BaseContract.Presenter<View> {
        void loadMeizhi(int page, List<Meizhi.ResultsBean> MeizhiData);
    }

    public interface View extends BaseContract.View {
        void refreshRv();

        void networkError(Throwable e);

        void summonMeizhi(boolean clearItem);
    }
}
