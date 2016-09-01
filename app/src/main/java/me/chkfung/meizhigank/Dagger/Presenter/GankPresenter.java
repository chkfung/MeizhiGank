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

package me.chkfung.meizhigank.Dagger.Presenter;

import java.util.Collections;

import javax.inject.Inject;

import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.NetworkApi;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Gank Presenter that perform business logic
 * Created by Fung on 04/08/2016.
 */

public class GankPresenter implements GankContract.Presenter {
    @Inject
    NetworkApi networkApi;
    @Inject
    Scheduler scheduler;
    private GankContract.View mView;
    private Subscription mSubscription;

    @Inject
    GankPresenter(GankContract.View view) {
        this.mView = view;
    }

    @Override
    public void getGank(String date) {
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = networkApi.getDay(date)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Day>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e);
                    }

                    @Override
                    public void onNext(Day day) {
                        Collections.sort(day.getCategory());
                        mView.setupRecycleView(day);
                    }
                });
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
