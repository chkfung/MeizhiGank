package me.chkfung.meizhigank.Contract.Presenter;

import java.util.List;

import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.Meizhi;
import me.chkfung.meizhigank.NetworkApi;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by Fung on 26/07/2016.
 */

public class MainPresenter implements MainContract.Presenter {
    MainContract.View mView;
    Subscription mSubscription;

    @Override
    public void loadMeizhi(int page, final List<Meizhi.ResultsBean> MeizhiData) {
        MeizhiApp meizhiApp = MeizhiApp.get(mView.getContext());
        NetworkApi networkApi = meizhiApp.getNetworkApi();
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = networkApi.getMeizhi(Constants.MEIZHI_AMOUNT, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(meizhiApp.getDefaultSubscribeScheduler())
                //v5
                .flatMap(new Func1<Meizhi, Observable<Meizhi.ResultsBean>>() {
                    @Override
                    public Observable<Meizhi.ResultsBean> call(Meizhi meizhi) {
                        return Observable.from(meizhi.getResults());
                    }
                })
                .subscribe(new Subscriber<Meizhi.ResultsBean>() {
                    @Override
                    public void onCompleted() {
                        mView.refreshRv();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.networkError(e);
                    }

                    @Override
                    public void onNext(Meizhi.ResultsBean resultsBean) {
                        MeizhiData.add(resultsBean);
                    }
                });
    }

    @Override
    public void attachView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public Subscription getmSubscription() {
        return mSubscription;
    }

    @Override
    public MainContract.View getView() {
        return mView;
    }
}
