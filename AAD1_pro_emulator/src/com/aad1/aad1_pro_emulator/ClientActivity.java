package com.aad1.aad1_pro_emulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClientActivity extends Activity {
	 
    private EditText Message2Send;
    private Button connectPhones;
    private Boolean connected = false;
    ClientThread client = null;
    TextView getMsg = null;
    String sendstate = "";
    TextView txtsendstate;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
 
        Message2Send = (EditText) findViewById(R.id.etMsg);
        connectPhones = (Button) findViewById(R.id.connect_phones);
        connectPhones.setOnClickListener(connectListener);
        getMsg = (TextView) findViewById(R.id.tvGet);
        getMsg.setText("fuck you");
        txtsendstate = (TextView) findViewById(R.id.tvSendState);
        
        
        if (savedInstanceState==null) {
        
        	LocalBroadcastManager.getInstance(this).registerReceiver(updateGUI, new IntentFilter("message"));
    	
        	client = new ClientThread();
        	client.setIp("192.168.1.11");
        	Thread cThread = new Thread(client);
        
        	cThread.start();
    	}
    }
    
    private BroadcastReceiver updateGUI = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String message = intent.getStringExtra("msg");
			
			if(message != null){
				getMsg.setText(message);
			}
		}
    };
 
    private OnClickListener connectListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	//sendstate = client.Send2Server(getText(R.id.etMsg).toString());
        	txtsendstate.setText(sendstate);
        }
    };   
}