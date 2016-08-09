package me.chkfung.meizhigank.Contract.Presenter;

import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.Day;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Fung on 04/08/2016.
 */

public class GankPresenter implements GankContract.Presenter {
    private GankContract.View mView;
    private Subscription mSubscription;

    @Override
    public void getGank(String date) {
        MeizhiApp meizhiApp = MeizhiApp.get(mView.getContext());
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = meizhiApp.getNetworkApi().getDay(date)
                .subscribeOn(meizhiApp.getDefaultSubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Day>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Day day) {
                        mView.setupRecycleView(day);
                    }
                });

    }

    @Override
    public void attachView(GankContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    @Override
    public Subscription getmSubscription() {
        return mSubscription;
    }

    @Override
    public GankContract.View getView() {
        return mView;
    }
}
