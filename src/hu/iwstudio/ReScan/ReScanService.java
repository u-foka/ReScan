package hu.iwstudio.ReScan;

import android.app.Notification;
import android.app.Service;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;

public class ReScanService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Resources res = getResources();

        Notification notification = new Notification(android.R.drawable.stat_notify_sync,
                res.getString(R.string.scanning_text), System.currentTimeMillis());

        Intent i = new Intent(this, ReScanMain.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        notification.setLatestEventInfo(this, res.getString(R.string.app_name),
                res.getString(R.string.scanning_text), pi);

        notification.flags |= Notification.FLAG_NO_CLEAR;

        startForeground(R.string.scanning_text, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }
}
