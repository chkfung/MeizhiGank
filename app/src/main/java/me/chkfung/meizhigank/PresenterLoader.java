package me.chkfung.meizhigank;

import android.content.Context;

import me.chkfung.meizhigank.Base.BaseContract;

/**
 * Created by Fung on 11/08/2016.
 */

public class PresenterLoader<T extends BaseContract.Presenter> extends android.support.v4.content.Loader<T> {
    private PresenterFactory<T> presenterFactory;
    private T presenter;

    public PresenterLoader(Context context, PresenterFactory<T> presenterFactory) {
        super(context);
        this.presenterFactory = presenterFactory;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (presenter != null)
            deliverResult(presenter);
        else
            forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter = presenterFactory.create();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }
}
