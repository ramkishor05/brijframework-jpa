package org.brijframework.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityProvider {
	
	public String id;
	public String providerName;
	public String connectionURL;
	
	public Map<String, String> properties=new HashMap<String, String>();
	
	public List<EntitySchema> schemas=new  ArrayList<>();
}
