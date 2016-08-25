package me.chkfung.meizhigank.Dagger.Presenter;

import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.DataInfo;
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
    int counter = 100;

    @Inject
    MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadMeizhi(int page, final List<DataInfo> MeizhiData) {
        Logger.i("Logging " + counter++);
        MeizhiApp meizhiApp = MeizhiApp.get(mView.getContext());
        NetworkApi networkApi = meizhiApp.getNetworkApi();
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = networkApi.getMeizhi(Constants.MEIZHI_AMOUNT, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(meizhiApp.getDefaultSubscribeScheduler())
                //v5
                .flatMap(new Func1<Meizhi, Observable<DataInfo>>() {
                    @Override
                    public Observable<DataInfo> call(Meizhi meizhi) {
                        return Observable.from(meizhi.getResults());
                    }
                })
                .subscribe(new Subscriber<DataInfo>() {
                    @Override
                    public void onCompleted() {
                        mView.refreshRv(MeizhiData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.networkError(e);
                    }

                    @Override
                    public void onNext(DataInfo resultsBean) {
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
