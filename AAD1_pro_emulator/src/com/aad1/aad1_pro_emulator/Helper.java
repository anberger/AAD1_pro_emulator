package com.aad1.aad1_pro_emulator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import org.apache.http.conn.util.InetAddressUtils;
import android.annotation.SuppressLint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Helper {
	
	/**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    @SuppressLint("DefaultLocale") 
    public static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
                            if (isIPv4) 
                                return sAddr;
                    }
                }
            }
        } catch (Exception ex) { }
        return "";
    }
	
	public JsonObject packageBuilder(String origin, String destination, String type, String message){
		
		JsonObject jObject = new JsonObject();
		
		jObject.addProperty("origin", origin);		
		jObject.addProperty("destination", destination);
		jObject.addProperty("type", type);
		jObject.addProperty("message", message);
		
		return jObject;
	}
	
	public ParserPackages messageParser(String sObject){
		JsonObject jObject = new JsonParser().parse(sObject).getAsJsonObject();
		
		Gson gson = new GsonBuilder().create();
		ParserPackages parsed = gson.fromJson( jObject , ParserPackages.class);	
    	return parsed;
    }
}
