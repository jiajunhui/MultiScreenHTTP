package com.jiajunhui.multiscreenhttp.bean;

import java.io.Serializable;

/**
 * Created by Taurus on 2016/11/9.
 */

public class ResultEntity implements Serializable {
    private int resultCode;
    private String resultMessage;
    private Object data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
