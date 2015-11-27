package com.hileone.test.ipcstudy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.widget.TextView;

import com.hileone.test.IDownloadInfoDispatch;
import com.hileone.test.IDownloadServices;
import com.hileone.test.R;

public class IPCActvity extends Activity {

    private IDownloadServices remoteBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IDownloadServices services = IDownloadServices.Stub.asInterface(service);
            try {
                remoteBinder = services;
                Log.i("leone-test", "Activity pid is: " + android.os.Process.myPid());
                Log.i("leone-test", "Activity uid is: " + android.os.Process.myUid());
                services.download("/home/neo/resume/index.html");
                services.registListener(dispatch);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteBinder = null;
        }
    };

    private IDownloadInfoDispatch dispatch = new IDownloadInfoDispatch.Stub() {
        @Override
        public void onDownloadInfoDispatch(String downloadInfo) throws RemoteException {
            handler.obtainMessage(1, downloadInfo).sendToTarget();
        }
    };

    private Handler handler  = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            StringBuilder builder = new StringBuilder(textView.getText());
            builder.append((String) msg.obj).append("\n");
            textView.setText(builder.toString());
        }
    };

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipcactvity);
        textView = (TextView) findViewById(R.id.text);
        this.bindService(new Intent(this, DownloadService.class), connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        this.unbindService(connection);
        if (remoteBinder != null && remoteBinder.asBinder().isBinderAlive()) {
            try {
                remoteBinder.unregistListener(dispatch);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
