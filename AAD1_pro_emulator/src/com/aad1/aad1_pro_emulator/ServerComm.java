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
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class ServerComm implements Runnable {
	private String serverMessage = null;
	DataOutputStream out;
    BufferedReader in;
    public boolean connected = false;
    public Socket socket = null;
    String MsgOut = "Hallo!";

    
    public void setSocket(Socket s){
    	this.socket = s;
    }
    
    public void setConnected(boolean value){
    	this.connected = value;
    }
    
    public void Send2Server(String Msg) {
    	PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(Msg);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    public void run() {
        try {
        	if(connected){
        		Send2Server(MsgOut);
                Log.d("ClientActivity", "C: Sent.");
                
                while (connected) {
                	String line = null;
                	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while ((line = in.readLine()) != null) {
                        Log.d("ServerActivity", line);        
                    }
                }
            
        	}
        }catch (Exception e) {
           Log.e("ClientActivity", "S: Error", e);
        }
    }
    public void sendtoGUI(String fuck) {
    	Intent i = new Intent("message"); 
    	i.putExtra("msg", fuck);
    	LocalBroadcastManager.getInstance(null).sendBroadcast(i);
    }
}

