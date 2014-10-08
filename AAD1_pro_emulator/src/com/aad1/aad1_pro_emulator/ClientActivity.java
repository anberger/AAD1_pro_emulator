package com.aad1.aad1_pro_emulator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ClientActivity extends Activity {
	 
    private EditText serverIp;
 
    private Button connectPhones;
 
    private static String serverIpAddress = "";
 
    private boolean connected = false;
 
    private Handler handler = new Handler();
    
    private static int SERVERPORT = 6000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
 
        serverIp = (EditText) findViewById(R.id.server_ip);
        connectPhones = (Button) findViewById(R.id.connect_phones);
        connectPhones.setOnClickListener(connectListener);
    }
 
    private OnClickListener connectListener = new OnClickListener() {
 
        @Override
        public void onClick(View v) {
            if (!connected) {
                serverIpAddress = serverIp.getText().toString();
                if (!serverIpAddress.equals("")) {
                    Thread cThread = new Thread(new ClientThread());
                    cThread.start();
                }
            }
        }
    };
 
    public class ClientThread implements Runnable {
 
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(ClientActivity.serverIpAddress);
                Log.d("ClientActivity",serverAddr.toString());
                Log.d("ClientActivity", "C: Connecting...");
                Socket socket = new Socket(serverAddr, ClientActivity.SERVERPORT);
                connected = true;
                //while (connected) {
                    try {
                        Log.d("ClientActivity", "C: Sending command.");
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                                    .getOutputStream())), true);
                            // WHERE YOU ISSUE THE COMMANDS
                            out.println("Hey Server!");
                            Log.d("ClientActivity", "C: Sent.");
                    } catch (Exception e) {
                        Log.e("ClientActivity", "S: Error", e);
                    }
                //}
                socket.close();
                Log.d("ClientActivity", "C: Closed.");
            } catch (Exception e) {
                Log.e("ClientActivity", "C: Error"+ e, e);
                connected = false;
            }
        }
    }
}