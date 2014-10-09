package com.aad1.aad1_pro_emulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainClient extends Activity{
	
	private Socket socket;

    private static int SERVERPORT = 0;
    private static String SERVER_IP = "";
    EditText serverIP, serverPORT, message;
    TextView deliver;
    boolean connected = false;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client); 
        
        message = (EditText) findViewById(R.id.etMsg);
        deliver = (TextView) findViewById(R.id.tvDelivery);
        
        Bundle ServerInfos = getIntent().getExtras();
        
        if (ServerInfos!=null) {
        	if (ServerInfos.containsKey("ip")) {
        		SERVER_IP = ServerInfos.getString("ip");
        	}
        	if (ServerInfos.containsKey("port")) {
        		SERVERPORT = Integer.parseInt(ServerInfos.getString("port"));
        	}
        }
    }

    public void disconnect(View v) {
    	try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void connect (View v) {
    	new Thread(new ClientThread()).start();
    }
    
    public void onClick(View view) {
        try {
        	
            String str = message.getText().toString();
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            out.println(str);
            out.flush();
            BufferedReader  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String read = in.readLine();
            System.out.println("MSG:" + read);
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	message.setText("");
        	deliver.setText("Message Delivery: " + "successful");
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                
                if (socket != null) {
                	while (connected) {
                		
                	}
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

