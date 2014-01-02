/******************************************************************************
 * This file is part of ReScan.                                               *
 *                                                                            *
 *     ReScan is free software: you can redistribute it and/or modify         *
 *     it under the terms of the GNU General Public License as published by   *
 *     the Free Software Foundation, either version 2 of the License, or      *
 *     (at your option) any later version.                                    *
 *                                                                            *
 *     ReScan is distributed in the hope that it will be useful,              *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of         *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the          *
 *     GNU General Public License for more details.                           *
 *                                                                            *
 *     You should have received a copy of the GNU General Public License      *
 *     along with ReScan.  If not, see <http://www.gnu.org/licenses/>.        *
 *                                                                            *
 * Copyright (c) 2013 IWstudio.hu                                             *
 ******************************************************************************/

package hu.iwstudio.ReScan;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;

public class ReScanService extends Service {
    public final static int NOTIFICATION_SCANNING = 101;
    public final static int NOTIFICATION_SCANNING_FINISHED = 102;

    private final Resources mRes;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public ReScanService() {
        mRes = getResources();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new Notification(android.R.drawable.stat_notify_sync,
                mRes.getString(R.string.scanning_text), System.currentTimeMillis());

        Intent i = new Intent(this, ReScanMain.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        notification.setLatestEventInfo(this, mRes.getString(R.string.app_name),
                mRes.getString(R.string.scanning_text), pi);

        notification.flags |= Notification.FLAG_NO_CLEAR;

        startForeground(NOTIFICATION_SCANNING, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);

        Notification notification = new Notification(android.R.drawable.stat_notify_sync,
                mRes.getString(R.string.scanning_finished), System.currentTimeMillis());
        notification.setLatestEventInfo(this, mRes.getString(R.string.app_name), mRes.getString(R.string.scanning_finished), null);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_SCANNING_FINISHED, notification);
        nm.cancelAll();
    }
}
