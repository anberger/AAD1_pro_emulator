package com.aad1.aad1_pro_emulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentClient extends Fragment {
	
	ProgressBar spinnerBar;
	ImageView connectionOK;
	TextView connectionStatusText;
	boolean spinner = true;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.client,container,false);
		
		spinnerBar = (ProgressBar) view.findViewById(R.id.pBconnection);
		connectionOK = (ImageView) view.findViewById(R.id.iVconnected);
		connectionStatusText = (TextView) view.findViewById(R.id.tVconnectionStatus);
		
		Bundle bundle = getArguments();
	
		//handles the state of the spinner
		if(bundle.containsKey("spinner")){
			spinner = bundle.getBoolean("spinner");
			if (!spinner) {
				spinnerBar.setVisibility(View.INVISIBLE);
				connectionOK.setVisibility(View.VISIBLE);
				connectionStatusText.setText("Connection Established");
			}else if (spinner) {
				spinnerBar.setVisibility(View.VISIBLE);
				connectionOK.setVisibility(View.INVISIBLE);
				connectionStatusText.setText("Awaiting Connection");
			}
		}
	return view;
	}
}
