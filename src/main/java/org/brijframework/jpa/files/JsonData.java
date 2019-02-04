package org.brijframework.jpa.files;

import java.util.Map;

public interface JsonData {

	public void setId(String id);

	public String getId();

	public Map<String, Object> getProperties();

	public void setProperties(Map<String, Object> properties);

	public String getType();

	public void setType(String type);

}
