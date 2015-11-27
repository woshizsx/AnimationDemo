package com.hileone.test.ipcstudy;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.*;
import android.util.Log;

import com.hileone.test.IDownloadInfoDispatch;
import com.hileone.test.IDownloadServices;

import java.util.Random;

public class DownloadService extends Service {

    RemoteCallbackList<IDownloadInfoDispatch> listenerList = new RemoteCallbackList<IDownloadInfoDispatch>();

    private Binder binder = new IDownloadServices.Stub() {

        @Override
        public void download(String path) throws RemoteException {
            Log.i("leone-test", "This path is: " + path);
            Log.i("leone-test", "This pid is: " + android.os.Process.myPid());
            Log.i("leone-test", "This path is: " + android.os.Process.myUid());
        }

        @Override
        public void registListener(IDownloadInfoDispatch listener) throws RemoteException {
            listenerList.register(listener);
            Log.i("leone-test", "Register Success");
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregistListener(IDownloadInfoDispatch listener) throws RemoteException {
            listenerList.unregister(listener);
            if (listenerList.getRegisteredCallbackCount() > 0) {
                Log.i("leone-test", "UnRegister Fail");
            }
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission("XXXX");
            if (check == PackageManager.PERMISSION_DENIED) {
                return true;
            }

            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }

            if (!packageName.startsWith("com.hileone")) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }
    };

    int count = 40;
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(--count > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        int N = listenerList.beginBroadcast();
                        for (int i = 0; i < N; i++) {
                            listenerList.getBroadcastItem(i).onDownloadInfoDispatch("INFO: >> " + (new Random().nextInt(20)));
                        }
                        listenerList.finishBroadcast();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }



}
