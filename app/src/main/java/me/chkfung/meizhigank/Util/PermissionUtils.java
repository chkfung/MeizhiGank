package me.chkfung.meizhigank.Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fung on 01/08/2016.
 */

public class PermissionUtils {
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

    public static boolean permissionGranted(int requestCode, int permissionCode, int[] grantResults) {
        if (requestCode != permissionCode) {
            return false;
        }
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
