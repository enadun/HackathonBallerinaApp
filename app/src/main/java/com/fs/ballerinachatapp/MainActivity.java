package com.fs.ballerinachatapp;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fs.ballerinachatapp.service.BallerinaWebSocketListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //setSupportActionBar(toolbar);
//
//        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//        ImageButton imgBtn = (ImageButton) findViewById(R.id.sendButton);
//        imgBtn.setOnClickListener();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
private static final String TAG = "ChatActivity";

    //public static final String socketURL = "ws://echo.websocket.org";
    public static final String socketURL = "ws://10.10.10.220:9090/echo-server/ws";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = true;
    public EditText usernameEditText;
    public String username;
    public String token;
    private OkHttpClient client;
    private WebSocket ws;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        usernameEditText = (EditText)findViewById(R.id.usernameText);

        // button click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign in request
            }
        });

        client = new OkHttpClient();
        start();
    }

    private void start(){
        Request request = new Request.Builder().url(socketURL).build();
        BallerinaWebSocketListener ballerinaWebSocketListener = new BallerinaWebSocketListener(this);
        ws = client.newWebSocket(request, ballerinaWebSocketListener);
        client.dispatcher().executorService().shutdown();
    }

    public void setupChat(){
        setContentView(R.layout.activity_login);

        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String msg = createResponseString(username);
                    ws.send(msg);
                    return true;
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String msg = createResponseString(username);
                ws.send(msg);
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    public String createResponseString(String username) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message-type","signin");
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

//    public boolean sendChatMessage() {
//        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
//        chatText.setText("");
//        //side = !side;
//        return true;
//    }

    public boolean receiveChatMessage(String message, Boolean isUser) {
        chatArrayAdapter.add(new ChatMessage(!isUser, message));
        chatText.setText("");
        //side = !side;
        return true;
    }
}
