package com.fs.ballerinachatapp.service;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by enadun on 17/Jan/22.
 */

public class BallerinaWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private TextView tv;
    private Activity activity;

    public BallerinaWebSocketListener(Activity activity, TextView tv){
        super();
        this.activity = activity;
        this.tv = tv;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Hello, Ballerina!");
        webSocket.send("What's up ?");
        //webSocket.close(NORMAL_CLOSURE_STATUS, "Bye Bye !");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        outPut(text);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        outPut("Closing" + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        outPut("Error: " + t.getMessage());
    }

    private void outPut(final String text) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(tv.getText().toString() + "\n\n" + text);
            }
        });
    }
}

