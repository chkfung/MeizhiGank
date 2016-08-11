package me.chkfung.meizhigank;

import me.chkfung.meizhigank.Base.BaseContract;

/**
 * Created by Fung on 11/08/2016.
 */

/**
 * @param <T> Presenter Object
 */
public interface PresenterFactory<T extends BaseContract.Presenter> {
    T create();
}
