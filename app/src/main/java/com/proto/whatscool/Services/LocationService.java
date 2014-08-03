package com.proto.whatscool.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sinasix on 6/28/14.
 */
public class LocationService extends Service

{
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
