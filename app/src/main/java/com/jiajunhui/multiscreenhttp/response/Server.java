package com.jiajunhui.multiscreenhttp.response;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2016/11/9.
 */

public class Server extends NanoHTTPD {
    public Server(int port) {
        super(port);
    }

    public Server(String hostname, int port) {
        super(hostname, port);
    }

    public Response newPlainTextResponse(String txt){
        return newFixedLengthResponse(Response.Status.OK,MIME_PLAINTEXT,txt);
    }
}
