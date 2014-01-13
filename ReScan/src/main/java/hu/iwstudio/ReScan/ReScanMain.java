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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class ReScanMain extends Activity implements MediaScannerConnectionClient {

    private MediaScannerConnection mMediaScanner;
    private ProgressDialog mDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();

        startService(new Intent(this, ReScanService.class));

        mDialog = ProgressDialog.show(this, res.getString(R.string.app_name),
                res.getString(R.string.scanning_text), true);

        mMediaScanner = new MediaScannerConnection(this, this);
        mMediaScanner.connect();
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
        
        super.onDestroy();
    }

    @Override
    public void onMediaScannerConnected() {
        mMediaScanner.scanFile(Environment.getExternalStorageDirectory().getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMediaScanner.disconnect();
        this.stopService(new Intent(this, ReScanService.class));
        finish();
    }

}
