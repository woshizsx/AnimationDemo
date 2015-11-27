package com.hileone.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hileone.test.ipcstudy.IPCActvity;
import com.hileone.test.originmove.OriginMoveActivity;
import com.hileone.test.scroller.ScrollActivity;
import com.hileone.test.transmove.TransMoveActivity;
import com.hileone.test.webview.WebViewActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        LinearLayout contentView = (LinearLayout) ((FrameLayout) getWindow()
                .getDecorView().findViewById(android.R.id.content)).getChildAt(0);
        for (int i = 0; i < contentView.getChildCount(); i++) {
            contentView.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scrollHeader:
                startActivity(new Intent(MainActivity.this, ScrollActivity.class));
                break;
            case R.id.ipcStudy:
                startActivity(new Intent(MainActivity.this, IPCActvity.class));
                break;
            case R.id.transMove:
                startActivity(new Intent(MainActivity.this, TransMoveActivity.class));
                break;
            case R.id.originMove:
                startActivity(new Intent(MainActivity.this, OriginMoveActivity.class));
                break;
            case R.id.webView:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            default:
                break;
        }
    }
}
