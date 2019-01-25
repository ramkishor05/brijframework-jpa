package org.brijframework.jpa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EntityNetwork {

	private String id;
	private String host;
	private String ip;
	private Integer port;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isLive() {
		try {
			URL url= new URL(ip+":"+port);
			try {
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			if(connection.getResponseCode()>400) {
				return true;
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return id+"";
	}
	public Object uniqueID() {
		return this.id;
	}
	
}
