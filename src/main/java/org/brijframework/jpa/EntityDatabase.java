package org.brijframework.jpa;

import java.util.ArrayList;
import java.util.List;

public class EntityDatabase {

	private String id;
	private String name;
	private String driver;
	private String sid;
	private String url;
	private String serviceID;
	private String service;
	private String username;
	private String password;
	private String vendor;
	private String ssl;
	
	private EntityNetwork network;
	
	private List<EntitySchema> schemas=new  ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public EntityNetwork getNetwork() {
		return network;
	}

	public void setNetwork(EntityNetwork network) {
		this.network = network;
	}

	public List<EntitySchema> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<EntitySchema> schemas) {
		this.schemas = schemas;
	}
	
}
