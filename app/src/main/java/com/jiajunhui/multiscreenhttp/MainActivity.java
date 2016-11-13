package com.jiajunhui.multiscreenhttp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiajunhui.multiscreenhttp.broadcast.UDPSend;
import com.jiajunhui.multiscreenhttp.response.AndroidServer;
import com.xapp.jjh.xui.activity.TopBarActivity;

import java.io.IOException;
import java.net.SocketAddress;

public class MainActivity extends TopBarActivity implements AndroidServer.OnResponseListener{

    private AndroidServer mServer;
    private TextView mTvState;
    private TextView mTvContent;
    private StringBuffer sb = new StringBuffer();

    @Override
    public View getContentView(LayoutInflater layoutInflater, ViewGroup container) {
        return inflate(R.layout.activity_main);
    }

    @Override
    public void findViewById() {
        mTvState = findView(R.id.tv_state);
        mTvContent = findView(R.id.tv_content);
    }

    @Override
    public void initData() {
        setTopBarTitle("AndroidServer");
        setSwipeBackEnable(false);
        setNavigationVisible(false);
        mServer = new AndroidServer(8080,this);
        try {
            mServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UDPSend.sendUDP(new UDPSend.OnUDPListener() {
            @Override
            public void onAuthSuccess(String data, SocketAddress socketAddress) {
                mTvState.setText("已连接:" + data);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UDPSend.closeUDP();
    }

    @Override
    public void onResponse(String content) {
        sb.append(content).append("\n");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvContent.setText(sb.toString());
            }
        });
    }
}
