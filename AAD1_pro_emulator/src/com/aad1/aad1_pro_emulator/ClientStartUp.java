package com.aad1.aad1_pro_emulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class ClientStartUp extends Activity {

	EditText ipAdresse, port;
	String[] previousIP = {"192.168.1.11","10.192.26.116"};
	String[] previousPORT = {"6000","8080"};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		ipAdresse = (EditText) findViewById(R.id.etIPaddress);
		port = (EditText) findViewById(R.id.etPort);
		
		//Creating the instance of ArrayAdapter containing list of language names  
        ArrayAdapter<String> adapterIP = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,previousIP);  
        //Getting the instance of AutoCompleteTextView  
        AutoCompleteTextView actvIP= (AutoCompleteTextView)ipAdresse;  
        actvIP.setThreshold(1);//will start working from first character  
        actvIP.setAdapter(adapterIP);//setting the adapter data into the AutoCompleteTextView  
		
		//Creating the instance of ArrayAdapter containing list of language names  
        ArrayAdapter<String> adapterPORT = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,previousPORT);  
        //Getting the instance of AutoCompleteTextView  
        AutoCompleteTextView actvPORT= (AutoCompleteTextView)port;  
        actvPORT.setThreshold(1);//will start working from first character  
        actvPORT.setAdapter(adapterPORT);//setting the adapter data into the AutoCompleteTextView  
	}

	public void clickEnter(View v) {
		
		if (ipAdresse.getText().toString().length() != 0 && port.getText().toString().length() != 0) {
			Intent startClient = new Intent(ClientStartUp.this, MainClient.class);
			startClient.putExtra("ip", ipAdresse.getText().toString());
			startClient.putExtra("port", port.getText().toString());
			
			startActivity(startClient);
		} else {
			Toast.makeText(getApplicationContext(), "Enter a valid IP and/or PORT", Toast.LENGTH_LONG).show();
		}
	}
	
	public void clickClear1(View v) {
		
		ipAdresse.setText("");
		
	}
	
	public void clickClear2(View v) {
		
		port.setText("");
		
	}
	
	public void clickQuit(View v) {
		
		finish();
	}
	
}
