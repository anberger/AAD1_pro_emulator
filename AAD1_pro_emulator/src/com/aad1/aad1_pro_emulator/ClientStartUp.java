package com.aad1.aad1_pro_emulator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ClientStartUp extends Activity {

	EditText ipAdresse, port;
	String[] previousIP = {"","","","",""};
	String[] previousPORT = {"","","","",""};
	String sqlRow;
	ImageButton clearCache;
	int i;
	AutoCompleteTextView actvIP,actvPORT;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		ipAdresse = (EditText) findViewById(R.id.etIPaddress);
		port = (EditText) findViewById(R.id.etPort);
		
    	refresh();
        
        clearCache = (ImageButton) findViewById(R.id.bClear1);
        clearCache.setOnLongClickListener(new OnLongClickListener() {
			
        	//Long Click event for deleting the database
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				TheDatabase td2 = new TheDatabase(ClientStartUp.this);
				td2.open();
				td2.deleteDataBase();
				td2.close();
				refresh();
				Toast.makeText(getApplicationContext(), "Cache cleared", Toast.LENGTH_LONG).show();
				return false;
			}
		});
        
        
        //on click listener for the auto complete list
    	actvIP.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    		//searches for the port which belongs to the selected IP
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String ipClicked;
				ipClicked = ipAdresse.getText().toString();
				int i;
				for (i=4; i>=0 ;i--) {
					if (ipClicked.toString().equals(previousIP[i].toString())) {
						port.setText(previousPORT[i]);
					}
				}
			}
        });
    	
    	//quit the application: intent from the client ==> closes this activity
    	if (getIntent().getBooleanExtra("EXIT", false)) 
    	{
    	        finish();
    	}
	}
	
	//refreshes the auto complete list on resume
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		refresh();
	}

	
	//handles the storage of the IP addresses and PORTs
	public void clickEnter(View v) {
		
		if (ipAdresse.getText().toString().length() != 0 && port.getText().toString().length() != 0) {
			Intent startClient = new Intent(ClientStartUp.this, MainClient.class);
			startClient.putExtra("ip", ipAdresse.getText().toString());
			startClient.putExtra("port", port.getText().toString());
			
			try {
				String ip2save = ipAdresse.getText().toString();
				String port2save = port.getText().toString();
				
				int i;
				for (i=4;i>=0;i--) {
					if (ip2save.toString().equals(previousIP[i].toString())) {
						try {				
							TheDatabase ex1 = new TheDatabase(this);
							ex1.open();
							ex1.deleteEntry(previousIP[i]);
							ex1.close();
						
						}catch (Exception e) {
							String error = e.toString();
							Dialog d = new Dialog(this);
							d.setTitle("Error!");
							TextView tv = new TextView(this);
							tv.setText(error);
							d.setContentView(tv);
							d.show();
						}
					}
				}
				
				TheDatabase entry = new TheDatabase(ClientStartUp.this);
				entry.open();
				entry.createEntry(ip2save, port2save);
				entry.close();
			}catch (Exception e) {
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			}
			
			startActivity(startClient);
		} else {
			Toast.makeText(getApplicationContext(), "Enter a valid IP and/or PORT", Toast.LENGTH_LONG).show();
		}
	}
	
	//clears the upper text field
	public void clickClear1(View v) {
		
		ipAdresse.setText("");
	}
	
	//clears the lower text field
	public void clickClear2(View v) {
		
		port.setText("");
	}
	
	//closes activity
	public void clickQuit(View v) {
		
		finish();
	}
	
	//refresh method
	//loads the current data from the database into the string arrays
	public void refresh(){
    	try {
			TheDatabase td = new TheDatabase(ClientStartUp.this);
			td.open();
			String[] returnedIP = td.getIP();
			String[] returnedPORT = td.getPORT();
			td.close();
			
			previousIP = returnedIP;
			previousPORT = returnedPORT;
		}catch (Exception e) {
			Dialog d = new Dialog(ClientStartUp.this);
			d.setTitle("Error");
			TextView tv = new TextView(ClientStartUp.this);
			tv.setText("Something is wrong!");
			d.setContentView(tv);
			d.show();
		}
    	
		//Creating the instance of ArrayAdapter containing list of language names  
        ArrayAdapter<String> adapterIP = new ArrayAdapter<String>(ClientStartUp.this,android.R.layout.select_dialog_item,previousIP);  
        //Getting the instance of AutoCompleteTextView  
        actvIP= (AutoCompleteTextView)ipAdresse;  
        actvIP.setThreshold(1);//will start working from first character  
        actvIP.setAdapter(adapterIP);//setting the adapter data into the AutoCompleteTextView  
		
		//Creating the instance of ArrayAdapter containing list of language names  
        ArrayAdapter<String> adapterPORT = new ArrayAdapter<String>(ClientStartUp.this,android.R.layout.select_dialog_item,previousPORT);  
        //Getting the instance of AutoCompleteTextView  
        actvPORT= (AutoCompleteTextView)port;  
        actvPORT.setThreshold(1);//will start working from first character  
        actvPORT.setAdapter(adapterPORT);//setting the adapter data into the AutoCompleteTextView
	}
}
