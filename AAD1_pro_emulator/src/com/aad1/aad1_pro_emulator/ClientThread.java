package com.aad1.aad1_pro_emulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class ClientThread implements Runnable {
	private String serverMessage = null;
	DataOutputStream out;
    BufferedReader in;
    private static String serverIpAddress = "";
    private boolean connected = false;
    private Handler handler = new Handler();
    private static int SERVERPORT = 6000;
    public Socket socket = null;
    String MsgOut = "Hallo!";
    ServerComm com = null;
    
    public void setIp(String ip){
    	this.serverIpAddress = ip;
    }
    
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(ClientThread.serverIpAddress);
            Log.d("ClientActivity",serverAddr.toString());
            Log.d("ClientActivity", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            connected = true;
            if(socket != null){
            	ServerComm com = new ServerComm();
            	com.setSocket(socket);
            	com.setConnected(true);
            	Thread sThread = new Thread(com);
            	sThread.run();
            	com.Send2Server("fuck");
            	
            	while(true){
                	com.Send2Server("fuck");
                }
            }
            
           
        } catch (Exception e) {
            Log.e("ClientActivity", "S: Error", e);
        }
    }
    public void sendtoGUI(String fuck) {
    	Intent i = new Intent("message"); 
    	i.putExtra("msg", fuck);
    	LocalBroadcastManager.getInstance(null).sendBroadcast(i);
    }
}
