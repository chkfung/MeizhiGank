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

import dagger.Module;
import dagger.Provides;
import me.chkfung.meizhigank.Contract.MeizhiContract;
import me.chkfung.meizhigank.Dagger.PresenterScope;

/**
 * Created by Fung on 29/08/2016.
 */
@Module
public class MeizhiPresenterModule {
    private final MeizhiContract.View mView;

    public MeizhiPresenterModule(MeizhiContract.View view) {
        mView = view;
    }

    @PresenterScope
    @Provides
    MeizhiContract.View provideView() {
        return mView;
    }

    @PresenterScope
    @Provides
    Context provideContext() {
        return mView.getContext();
    }
}
