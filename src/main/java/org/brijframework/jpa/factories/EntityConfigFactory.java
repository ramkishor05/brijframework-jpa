package org.brijframework.jpa.factories;

import static org.brijframework.jpa.util.EntityConstants.REF;

import java.util.LinkedHashMap;
import java.util.Map;

import org.brijframework.jpa.container.EntityConfigContainer;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.files.ModelData;
import org.brijframework.util.accessor.PropertyAccessorUtil;

public class EntityConfigFactory{
	
	private LinkedHashMap<String, Object> cache = new LinkedHashMap<>();
	
	private EntityConfigContainer container=EntityConfigContainer.getContainer();
	
	private EntityContext entityContext;

	public void setContext(EntityContext entityContext) {
		this.entityContext=entityContext;
	}
	
	public EntityContext getContext() {
		return entityContext;
	}
	
	public void register(String id,Object model) {
		getCache().put(id, model);
		loadInContainer(id, model);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T find(String key) {
		T t=(T) getCache().get(key);
		if(t!=null) {
			return t;
		}
		return findInContainer(key);
	}

	@SuppressWarnings("unchecked")
	public void mergeRelationship(ModelData data,Object model){
		data.getProperties().forEach((key, val) -> {
			if (val instanceof Map) {
				Map<String, Object> mapVal = (Map<String, Object>) val;
				String ref = (String) mapVal.get(REF);
				if (ref != null) {
					val =getContainer().find(ref);
				} else {
					val = mapVal;
				}
				PropertyAccessorUtil.setProperty(model, key, val);
			}
		});
	}
	
	public void loadInContainer(String id,Object model) {
		if(getContainer()==null) {
			return;
		}
		if(getContainer().find(id)==null) {
			getContainer().register(id, model);
		}
	}
	
	public <T> T findInContainer(String model) {
		if(getContainer()==null) {
			return null;
		}
		return getContainer().find(model);
	}
	
	public EntityConfigContainer getContainer() {
		return container;
	}
	
	public void setContainer(EntityConfigContainer container) {
		this.container = container;
	}
	
	public LinkedHashMap<String, Object> getCache() {
		return cache;
	}

	public void loadFactory() {
	}
}
