package hu.iwstudio.ReScan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class ReScanMain extends Activity {

    BroadcastReceiver mFinishReceiver;
    ProgressDialog mDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();

        mDialog = ProgressDialog.show(this, res.getString(R.string.app_name),
                res.getString(R.string.dialog_text), true);

        IntentFilter finishFilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        finishFilter.addDataScheme("file");

        mFinishReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };

        registerReceiver(mFinishReceiver, finishFilter);


        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog.show();
    }

    @Override
    protected void onPause() {
        mDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mFinishReceiver);
        super.onDestroy();
    }

}
