package com.fs.ballerinachatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import com.fs.ballerinachatapp.service.BallerinaWebSocketListener;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class WebServiceActivity extends AppCompatActivity {

    //public static final String socketURL = "ws://echo.websocket.org";
    public static final String socketURL = "ws://10.10.10.220:9090/echo-server/ws";

    private Button start;
    private Button sender;
    private Button close;
    private TextView output;
    private OkHttpClient client;
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        start = (Button) findViewById(R.id.start);
        output = (TextView) findViewById(R.id.output);
        sender = (Button) findViewById(R.id.send);
        close = (Button) findViewById(R.id.close);

        client = new OkHttpClient();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start();
            }
        });

        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send("Sended Message !");
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send("closeMe");
            }
        });
    }

//    private void start(){
//        Request request = new Request.Builder().url(socketURL).build();
//        BallerinaWebSocketListener ballerinaWebSocketListener = new BallerinaWebSocketListener(this, output);
//        ws = client.newWebSocket(request, ballerinaWebSocketListener);
//        client.dispatcher().executorService().shutdown();
//    }
}
