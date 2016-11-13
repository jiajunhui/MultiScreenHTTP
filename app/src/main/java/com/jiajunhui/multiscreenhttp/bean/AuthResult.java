package com.jiajunhui.multiscreenhttp.bean;

import android.os.Build;

import java.io.Serializable;

/**
 * Created by Taurus on 2016/11/9.
 */

public class AuthResult implements Serializable {

    private String serverIP;
    private String deviceModel;

    public AuthResult() {
    }

    public AuthResult(String serverIP) {
        this.serverIP = serverIP;
        this.deviceModel = Build.MODEL;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}
