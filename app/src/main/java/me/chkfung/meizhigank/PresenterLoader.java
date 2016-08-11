package me.chkfung.meizhigank;

import android.content.Context;
import android.content.Loader;

import me.chkfung.meizhigank.Base.BaseContract;

/**
 * Created by Fung on 11/08/2016.
 */

public class PresenterLoader<T extends BaseContract.Presenter> extends Loader<T> {
    public PresenterLoader(Context context) {
        super(context);
    }

}
