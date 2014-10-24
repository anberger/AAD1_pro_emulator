package com.aad1.aad1_pro_emulator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FragmentAnimation extends Fragment {
	
//	public FragmentCommunicator frag;
	ImageView ImageForward, ImageForwardFrame, ImageForwardCover;
	ImageView ImageRight, ImageRightFrame, ImageRightCover;
	ImageView ImageBackward, ImageBackwardFrame, ImageBackwardCover;
	ImageView ImageLeft, ImageLeftFrame, ImageLeftCover;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.animation,container,false);
		
		ImageForward = (ImageView) view.findViewById(R.id.ivUp);
		ImageForwardFrame = (ImageView) view.findViewById(R.id.iVupframe);
		ImageForwardCover = (ImageView) view.findViewById(R.id.iVupcover);
		
		ImageRight = (ImageView) view.findViewById(R.id.ivRight);
		ImageRightFrame = (ImageView) view.findViewById(R.id.iVrightframe);
		ImageRightCover = (ImageView) view.findViewById(R.id.iVrightcover);
		
		ImageBackward = (ImageView) view.findViewById(R.id.ivDown);
		ImageBackwardFrame = (ImageView) view.findViewById(R.id.iVdownframe);
		ImageBackwardCover = (ImageView) view.findViewById(R.id.iVdowncover);
		
		ImageLeft = (ImageView) view.findViewById(R.id.ivLeft);
		ImageLeftFrame = (ImageView) view.findViewById(R.id.iVleftframe);
		ImageLeftCover = (ImageView) view.findViewById(R.id.iVleftcover);
		
		Bundle bundle = getArguments();
		
		if(bundle.containsKey("val")){
			long[] val = bundle.getLongArray("val");
			if(val[0]>0){
				direction_forwards(true, val[0]);
			}else{
				direction_forwards(false, val[0]);
			}
			if(val[1]>0){
				direction_right(true, val[1]);
			}else{
				direction_right(false, val[1]);
			}
			if(val[2]>0){
				direction_backwards(true, val[2]);
			}else{
				direction_backwards(false, val[2]);
			}
			if(val[3]>0){
				direction_left(true, val[3]);
			}else{
				direction_left(false, val[3]);
			}
		}
		
		return view;
	}
	
	public void direction_forwards(Boolean bool, long valS){
		if (bool) {
			valS = (long) (valS * 1.7);
			ImageForward.setVisibility(View.VISIBLE);
			ImageForwardFrame.setVisibility(View.VISIBLE);
			ImageForwardCover.setVisibility(View.VISIBLE);
			ImageForwardCover.animate().translationY(-valS);
		}else{
			ImageForward.setVisibility(View.INVISIBLE);
			ImageForwardFrame.setVisibility(View.INVISIBLE);
			ImageForwardCover.setVisibility(View.INVISIBLE);
			ImageForwardCover.setBottom(0);
		}
	}	
	
	public void direction_right(Boolean bool, long valS){
		if (bool) {
			valS = (long) (valS * 1.7);
			ImageRight.setVisibility(View.VISIBLE);
			ImageRightFrame.setVisibility(View.VISIBLE);
			ImageRightCover.setVisibility(View.VISIBLE);
			ImageRightCover.animate().translationX(valS);
		}else{
			ImageRight.setVisibility(View.INVISIBLE);
			ImageRightFrame.setVisibility(View.INVISIBLE);
			ImageRightCover.setVisibility(View.INVISIBLE);
			ImageRightCover.setLeft(0);
		}
	}
	
	public void direction_backwards(Boolean bool, long valS){
		if (bool) {
			valS = (long) (valS * 1.7);
			ImageBackward.setVisibility(View.VISIBLE);
			ImageBackwardFrame.setVisibility(View.VISIBLE);
			ImageBackwardCover.setVisibility(View.VISIBLE);
			ImageBackwardCover.animate().translationY(valS);
		}else{
			ImageBackward.setVisibility(View.INVISIBLE);
			ImageBackwardFrame.setVisibility(View.INVISIBLE);
			ImageBackwardCover.setVisibility(View.INVISIBLE);
			ImageBackwardCover.setBottom(0);
		}
	}
	
	public void direction_left(Boolean bool, long valS){
		if (bool) {
			valS = (long) (valS * 1.7);
			ImageLeft.setVisibility(View.VISIBLE);
			ImageLeftFrame.setVisibility(View.VISIBLE);
			ImageLeftCover.setVisibility(View.VISIBLE);
			ImageLeftCover.animate().translationX(-valS);
		}else{
			ImageLeft.setVisibility(View.INVISIBLE);
			ImageLeftFrame.setVisibility(View.INVISIBLE);
			ImageLeftCover.setVisibility(View.INVISIBLE);
			ImageLeftCover.setLeft(0);
		}
	}
	
//	public void rightON(){
//		ImageForward.animate().translationX(50);
//	}
//	
//	
//	@Override
//	public void onAttach(Activity activity) {
//	    super.onAttach(activity);
//	    try {
//	    	frag = (FragmentCommunicator) activity;
//	    } catch (ClassCastException e) {
//	        throw new ClassCastException(activity.toString() + " must implement FragmentCommunicator");
//	    }
//	}
//	
//	// Interface to communicate with the TCP Connection Activity
//	public interface FragmentCommunicator  {
//		public void rightON();
//	}	
}
