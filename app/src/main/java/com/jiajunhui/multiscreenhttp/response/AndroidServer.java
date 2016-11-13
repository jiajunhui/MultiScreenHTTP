package com.jiajunhui.multiscreenhttp.response;

import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2016/11/9.
 */

public class AndroidServer extends Server {

    private OnResponseListener mOnResponseListener;

    public AndroidServer(int port,OnResponseListener onResponseListener) {
        super(port);
        this.mOnResponseListener = onResponseListener;
    }

    public AndroidServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        Map<String, String> parms = session.getParms();
        if(parms.containsKey("content") && mOnResponseListener!=null){
            mOnResponseListener.onResponse(parms.get("content"));
        }
        return super.serve(session);
    }

    public interface OnResponseListener{
        void onResponse(String content);
    }
}
