package com.jiajunhui.multiscreenhttp.broadcast;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Taurus on 2016/11/10.
 */

public class UDPSend {

    public static final int PORT = 9999;

    private static DatagramSocket detectSocket;
    private static SocketAddress hostAddress;

    private static Timer mTimer;
    private static TimerTask mTask;

    private static Handler mHandler;
    private static DatagramSocket listenDetectSocket;

    private static void initUDP() {
        try {
            mHandler = new Handler(Looper.getMainLooper());
//            hostAddress = InetAddress.getByName("255.255.255.255");
            hostAddress = new InetSocketAddress("255.255.255.255",PORT);
            detectSocket = new DatagramSocket(PORT);
            detectSocket.setReuseAddress(false);
            detectSocket.bind(hostAddress);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendUDP(OnUDPListener onUDPListener){
        closeUDP();
        stopSend();
        initUDP();
        startUDPListener(onUDPListener);
        mTask = new TimerTask() {
            @Override
            public void run() {
                sendUDP(Build.MODEL);
            }
        };
        mTimer = new Timer(true);
        mTimer.schedule(mTask,0,2000);
    }

    private static void stopSend(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer = null;
        }
        if(mTask!=null){
            mTask.cancel();
            mTask = null;
        }
    }

    private static void startUDPListener(final OnUDPListener onUDPListener) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    int listenPort = 8888;
                    byte[] buf = new byte[1024];
                    final DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    listenDetectSocket = new DatagramSocket(listenPort);
                    while (true && listenDetectSocket!=null) {
                        listenDetectSocket.receive(packet);
                        //receive data
                        final String receiveData = new String(packet.getData(), 0, packet.getLength());
                        if(receiveData!=null){
                            Log.d("UDP_Send","---Auth Success---Host:" + packet.getSocketAddress().toString());
                            if(onUDPListener!=null && mHandler!=null){
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onUDPListener.onAuthSuccess(receiveData,packet.getSocketAddress());
                                    }
                                });
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("UDP_Send","---listener_Exception---");
                }
            }
        }.start();

    }

    private static void sendUDP(String outMessage){
        try{
            // Send packet thread
            byte[] buf;
            if (TextUtils.isEmpty(outMessage))
                return;
            buf = outMessage.getBytes();
            Log.d("UDP_Send","Send " + outMessage + " to " + hostAddress);
            // Send packet to hostAddress:9999, server that listen
            // 9999 would reply this packet
            DatagramPacket out = new DatagramPacket(buf,
                    buf.length, hostAddress);
            if(detectSocket!=null){
                detectSocket.send(out);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("UDP_Send","---send_Exception---");
        }
    }

    public static void closeUDP(){
        try {
            if(detectSocket!=null){
                stopSend();
                detectSocket.disconnect();
                detectSocket.close();
                detectSocket = null;
            }
            if(listenDetectSocket!=null){
                listenDetectSocket.disconnect();
                listenDetectSocket.close();
                listenDetectSocket = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public interface OnUDPListener{
        void onAuthSuccess(String data, SocketAddress socketAddress);
    }

}
