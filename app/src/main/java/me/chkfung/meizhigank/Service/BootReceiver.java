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

package me.chkfung.meizhigank.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This Receiver will re-activate the alarm after device reboot.
 * If the notification disabled in Main Activity, this service will be disabled as well
 * Created by Fung on 31/08/2016.
 */

public class BootReceiver extends BroadcastReceiver {
    private final AlarmReceiver alarmReceiver = new AlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
            alarmReceiver.setAlarm(context);
    }
}
