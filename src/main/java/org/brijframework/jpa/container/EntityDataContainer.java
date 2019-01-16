package org.brijframework.jpa.container;

import static org.brijframework.jpa.util.EntityConstants.CTD;
import static org.brijframework.jpa.util.EntityConstants.REF;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.builder.EntryComparator;
import org.brijframework.jpa.persist.EntityProcessor;
import org.brijframework.jpa.util.EntityConstants;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;

public class EntityDataContainer {

	private LinkedHashMap<String, EntityGroup> cache = new LinkedHashMap<>();
	
	private static EntityDataContainer container;
	
	public static EntityDataContainer getContainer() {
		if(container==null) {
			container=new EntityDataContainer();
		}
		return container;
	}
	
	public LinkedHashMap<String, EntityGroup> getCache() {
		return cache;
	}
	
	public void register(EntityGroup model) {
		getCache().put(model.getId(), model);
	}

	public EntityGroup find(String model) {
		return getCache().get(model);
	}
	
	@SuppressWarnings("unchecked")
	public EntityDataContainer build() {
		getCache().forEach((id, entityGroup) -> {
			Object entityObject = InstanceUtil.getInstance(entityGroup.getEntityData().getEntity());
			entityGroup.getEntityData().getProperties().forEach((key, val) -> {
				if (val == null) {
					PropertyAccessorUtil.setProperty(entityObject, key, val);
				} else {
					if(CTD.equalsIgnoreCase(val.toString())) {
						val=new Date();
					}
					if (!(val instanceof Map) && !(val instanceof Collection)) {
						PropertyAccessorUtil.setProperty(entityObject, key, val);
					}
				}
			});
			entityGroup.setEntityObject(entityObject);
		});
		getCache().forEach((id, entityGroup) -> {
			entityGroup.getEntityData().getProperties().forEach((key, val) -> {
				if (val instanceof Map) {
					Map<String, Object> mapVal = (Map<String, Object>) val;
					String ref = (String) mapVal.get(REF);
					if (ref != null) {
						val =getCache().containsKey(ref) ? getCache().get(ref).getEntityObject():null;
					} else {
						val = mapVal;
					}
					PropertyAccessorUtil.setProperty(entityGroup.getEntityObject(), key, val);
				}
			});
		});
		return this;
	}

	public EntityDataContainer procced() {
		String adpter=System.getProperty(EntityConstants.IMPORT_ADPTER);
		if(adpter==null || adpter.isEmpty()) {
			System.err.println("Please config "+EntityConstants.IMPORT_ADPTER);
			return this;
		}
		EntityProcessor processor=InstanceUtil.getInstance(adpter);
		if(processor==null) {
			System.err.println("Invalid config "+EntityConstants.IMPORT_ADPTER);
			return this;
		}
		Collection<EntityGroup> values=getCache().entrySet().stream().sorted(new EntryComparator()).collect(
	            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
	                LinkedHashMap::new)).values();
		this.laodEntities(processor,values);
		return this;
	}
	
	public void laodEntities(EntityProcessor processor,Collection<EntityGroup> groups) {
		if (groups == null) {
			return;
		}
		System.err.println("EntityManager entities laoding...");
		processor.init();
		groups.forEach(group -> {
			if (!processor.constains(group.getEntityData(), group.getEntityObject())) {
				processor.process(group.getEntityData(), group.getEntityObject());
			}
		});
		processor.finish();
		System.err.println("EntityManager entities laoded ");
	}
}
