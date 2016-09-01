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

package me.chkfung.meizhigank.Contract;

import android.net.Uri;

import me.chkfung.meizhigank.Base.BaseContract;
import rx.Observable;

/**
 * Created by Fung on 26/07/2016.
 */

public interface MeizhiContract {
    interface Presenter extends BaseContract.Presenter {

        /**
         * Save Image to Storage
         * @param url URL
         */
        void SaveImage(String url);

        /**
         * Download Image using OkHttpClient
         * @param mUrl URL
         * @return Uri for informing gallery to update
         */
        Observable<Uri> DownloadImage(String mUrl);


    }

    interface View extends BaseContract.View {
        void SaveMenuTapped();

        void DownloadFailure();

        void ImageSaved();

        void updateProgressBar(int progress);
    }
}
