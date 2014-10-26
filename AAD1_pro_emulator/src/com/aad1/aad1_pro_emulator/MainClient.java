package com.aad1.aad1_pro_emulator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainClient extends FragmentActivity{
	
	private static ClientThread mClientThread;   
	private Helper helper = new Helper();
	private long[] val = {0,0,0,0};

    private static int SERVERPORT = 0;
    private static String SERVER_IP = "";
    
    boolean connected = false;
    boolean spinner = true;
    
    public Handler mhandlerClient = new Handler(new Handler.Callback() {

        @Override public boolean handleMessage(Message msg) 
        {             	
        	Bundle bundle = msg.getData();
        	if(bundle != null) {
	        	if(bundle.containsKey("object")){
	        		String message = bundle.getString("object");
	        		valueData(message);
	        	}
        	}
        	return false;
        } 
    });
    
    // decodes the input stream, fills the val string array with the movement values
    public void valueData(String message){
    	ParserPackages parsed = helper.messageParser(message);
  
    	if(parsed.type.equals("carvalues")){
    		long values = Long.parseLong(parsed.message);
    		
    		val[3] = (values % 1000);
    		values = values / 1000;
    		val[2] = (values % 1000);
    		values = values / 1000;
    		val[1] = (values % 1000);
    		values = values / 1000;
    		val[0] = values;

    		replaceAnimation();
    	}
    	
    	if(parsed.type.equals("Error")){
    		Toast.makeText(getApplicationContext(), parsed.message, Toast.LENGTH_SHORT).show();
    	}
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        
        replaceAnimation();
        updateStatus();
        
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
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
    	if (connected == true) {
			mClientThread.cancel();
    	}
	}
    
    // bundles the val string array and gives it to the Animation Fragment
    public void replaceAnimation(){
    	if (findViewById(R.id.flAnimation) != null) {
            FragmentAnimation frag = new FragmentAnimation();
            Bundle bundle = new Bundle();
            bundle.putLongArray("val", val);
            frag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.flAnimation, frag).commit();
        }
    }
    
    //bundles the needed data (online/offline) and gives it to the Client Fragment
    public void updateStatus(){
    	if (findViewById(R.id.flControl) != null) {
    		FragmentClient frag2 = new FragmentClient();
    		Bundle bundleC = new Bundle();
    		bundleC.putBoolean("spinner", spinner);
    		frag2.setArguments(bundleC);
    		getSupportFragmentManager().beginTransaction().replace(R.id.flControl, frag2).commit();
    	}
    }

    //Closes the Client Thread => shuts down the connection to the server
	public void disconnect(View v) {
    	if (connected == true) {
			mClientThread.cancel();
    	}
    }

	//Starts the Client Thread ==> opens a connection to the server
	public void connect (View v) {
    	if(!connected){
    		mClientThread = new ClientThread(mhandlerClient);
    		mClientThread.start();
    	}
    }
	
	public void QuitAll (View v) {
    	if (connected == true) {
			mClientThread.cancel();
    	}
    	finish();
    	
    	Intent i = new Intent(MainClient.this, ClientStartUp.class);
    	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	i.putExtra("EXIT", true);
    	startActivity(i);
	}
	
	public void ChangeIpPort (View v) {
    	if (connected == true) {
			mClientThread.cancel();
    	}
    	finish();
	}

    class ClientThread extends Thread {

    	private Socket SOCK;
    	private DataOutputStream outputStream = null;
    	private DataInputStream inputStream = null;
    	
    	// Output Handler
    	Bundle bundle = new Bundle();
    	private Handler outHandler; 
    	
    	// Setter Methods
    	public ClientThread(Handler mHandler) {
            outHandler = mHandler;
        }
    	
    	// This Method sends Data to the TCP Server Class
    	private void Send2Activity(JsonObject jObject){
            Message msg = outHandler.obtainMessage(); 
            bundle.putString("object", jObject.toString());        
            msg.setData(bundle);
            outHandler.sendMessage(msg);
        }

    	// Send Data to Client
    	public void Send2Server(JsonObject jObject){
    		if(!SOCK.isClosed() && connected){
    			try {
    				String message = jObject.toString() + "\n";
    				outputStream.write(message.getBytes());
    				outputStream.flush();
    			} catch (IOException e) {
    				connected = false;
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	public void cancel(){
    		try {
    			Send2Server(helper.packageBuilder(Helper.getIPAddress(), SERVER_IP, "car", "offline"));
				SOCK.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                SOCK = new Socket(serverAddr, SERVERPORT);
                
                if (SOCK != null) {
                	if(SOCK.isConnected()){
                		
                		spinner = false;
                		updateStatus();
	        			
	        			outputStream = new DataOutputStream(SOCK.getOutputStream());
	        			inputStream = new DataInputStream(SOCK.getInputStream());
	        			
	                	connected = true;
	
	                	Send2Server(helper.packageBuilder(Helper.getIPAddress(), SERVER_IP, "car", "online"));
	                	
	                	while (!Thread.currentThread().isInterrupted()) {
	        			 
	        			    if(inputStream.available() > 0){
	        			    	
	        			    	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
	    				        String inputLine;
	    				        while ((inputLine = in.readLine()) != null){
	    							JsonParser parser = new JsonParser();
	    						    JsonObject jObject = parser.parse(inputLine).getAsJsonObject();
	    						    Send2Activity(jObject);
	    				        }
	        			    }
	                	}
                	}
                }

            } catch (Exception e1) {
                e1.printStackTrace();
                Send2Activity(helper.packageBuilder("dei", "ondere ip", "Error", "failed to Connect"));
                Log.d("Client","error" + e1.toString());
            } finally {
            	Send2Activity(helper.packageBuilder("dei", "ondere ip", "Info", "he i bin disconnected"));
            	connected = false;
            	SOCK = null;
            	outputStream = null;
            	inputStream = null;
        		spinner = true;
        		updateStatus();
            }
        }
    }
}

