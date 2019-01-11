package org.brijframework.jpa.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.EntityModel;
import org.brijframework.jpa.util.InstanceUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityModelBuilder {

	private final static String REF = "@ref";

	private LinkedHashMap<String, EntityGroup> cache = new LinkedHashMap<>();

	public EntityModelBuilder loadEntities(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileInputStream reader = new FileInputStream(file)) {
			List<EntityModel> lst = mapper.readValue(reader, new TypeReference<List<EntityModel>>() {});
			lst.forEach(entityModel -> {
				EntityGroup entityGroup=new EntityGroup();
				entityGroup.setEntityModel(entityModel);
				getCache().put(entityModel.getId(), entityGroup);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public EntityModelBuilder build() {
		getCache().forEach((id, entityGroup) -> {
			Object entityObject = InstanceUtil.getInstance(entityGroup.getEntityModel().getEntity());
			entityGroup.getEntityModel().getProperties().forEach((key, val) -> {
				if (val == null) {
					InstanceUtil.setField(entityObject, key, val);
				} else {
					if (!(val instanceof Map) && !(val instanceof Collection)) {
						InstanceUtil.setField(entityObject, key, val);
					}
				}
			});
			entityGroup.setEntityObject(entityObject);
		});
		getCache().forEach((id, entityGroup) -> {
			entityGroup.getEntityModel().getProperties().forEach((key, val) -> {
				if (val instanceof Map) {
					Map<String, Object> mapVal = (Map<String, Object>) val;
					String ref = (String) mapVal.get(REF);
					if (ref != null) {
						val =getCache().containsKey(ref) ? getCache().get(ref).getEntityObject():null;
					} else {
						val = mapVal;
					}
					InstanceUtil.setField(entityGroup.getEntityObject(), key, val);
				}
			});
		});
		cache=getCache().entrySet().stream()
		        .sorted(new EntryComparator())
		        .collect(
		            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		            		LinkedHashMap::new));
		return this;

	}

	public LinkedHashMap<String, EntityGroup> getCache() {
		return cache;
	}
}

class EntryComparator implements Comparator<Map.Entry<String,EntityGroup>>{

	@Override
	public int compare(Map.Entry<String,EntityGroup> o1, Map.Entry<String,EntityGroup> o2) {
		List<Field> fields1=InstanceUtil.getAllField(o1.getValue().getEntityObject().getClass());
		for(Field field1: fields1) {
			if(o2.getClass().isAssignableFrom(field1.getType())) {
				return 1;
			}
		}
		List<Field> fields2=InstanceUtil.getAllField(o2.getValue().getEntityObject().getClass());
		for(Field field2: fields2) {
			if(o1.getClass().isAssignableFrom(field2.getType())) {
				return -1;
			}
		}
		return 0;
	}
}
