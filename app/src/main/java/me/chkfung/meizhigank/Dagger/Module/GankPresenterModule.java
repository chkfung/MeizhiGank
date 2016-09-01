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

package me.chkfung.meizhigank.Dagger.Module;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.R;

/**
 * Gank Presenter Module to provide required Object
 * Created by Fung on 26/08/2016.
 */
@Module
public class GankPresenterModule {
    private final GankContract.View mView;


    public GankPresenterModule(GankContract.View view) {
        mView = view;
    }

    @PresenterScope
    @Provides
    GankContract.View provideGankContractView() {
        return mView;
    }

    @PresenterScope
    @Provides
    Context provideContext() {
        return mView.getContext();
    }

    @PresenterScope
    @Provides
    CustomTabsIntent.Builder provideCustomTabIntent(GankContract.View view) {
        Context mContext = view.getContext();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                .setShowTitle(true);

        builder.setStartAnimations(mContext, R.anim.right_in, R.anim.right_out);
        builder.setExitAnimations(mContext, R.anim.right_in_back, R.anim.right_out_back);
        builder.setCloseButtonIcon(
                BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_close_clear_cancel));

        return builder;

    }
}
