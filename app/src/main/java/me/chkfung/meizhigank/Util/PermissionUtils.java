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

package me.chkfung.meizhigank.Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Permission Util for easy life
 * Created by Fung on 01/08/2016.
 */

public class PermissionUtils {
    @SuppressWarnings("SameParameterValue")
    public static boolean requestPermission(Activity activity, int requestCode, String... permissions) {
        boolean granted = true;
        List<String> permissionRequired = new ArrayList<>();
        for (String s : permissions) {
            int permissionCode = ActivityCompat.checkSelfPermission(activity, s);
            if (permissionCode == PackageManager.PERMISSION_DENIED) {
                permissionRequired.add(s);
                granted = false;
            }
        }
        if (granted)
            return granted;
        else {
            ActivityCompat.requestPermissions(activity,
                    permissionRequired.toArray(new String[permissionRequired.size()]),
                    requestCode);
            return false;
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static boolean permissionGranted(int requestCode, int permissionCode, int[] grantResults) {
        if (requestCode != permissionCode) {
            return false;
        }
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
