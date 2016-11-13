package com.jiajunhui.multiscreenhttp.utils;


import com.jiajunhui.multiscreenhttp.bean.AuthResult;
import com.jiajunhui.multiscreenhttp.bean.ResultEntity;

/**
 * Created by Taurus on 2016/11/9.
 */

public class ResultPackage {
    public static String getAuthResponse(){
        return getJsonResult(0,"AuthSuccess",new AuthResult(NetAddressUtils.getHostIP()));
    }

    private static String getJsonResult(int resultCode,String resultMessage, Object object){
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setResultCode(resultCode);
        resultEntity.setResultMessage(resultMessage);
        resultEntity.setData(object);
        return GsonTools.createGsonString(resultEntity);
    }
}
