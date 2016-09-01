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

package me.chkfung.meizhigank.Dagger.Component;

import dagger.Component;
import me.chkfung.meizhigank.Dagger.Module.MeizhiPresenterModule;
import me.chkfung.meizhigank.Dagger.PresenterScope;
import me.chkfung.meizhigank.ui.MeizhiActivity;

/**
 * Meizhi Presenter Component Injection
 * Created by Fung on 29/08/2016.
 */
@PresenterScope
@Component(
        modules = MeizhiPresenterModule.class,
        dependencies = AppComponent.class
)
public interface MeizhiPresenterComponent {
    void inject(MeizhiActivity meizhiActivity);
}
