package org.brijframework.jpa.builder;
import static org.brijframework.jpa.util.EntityConstants.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.data.EntityData;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityModelBuilder {

	private LinkedHashMap<String, EntityGroup> cache = new LinkedHashMap<>();

	public EntityModelBuilder loadEntities(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileInputStream reader = new FileInputStream(file)) {
			List<EntityData> lst = mapper.readValue(reader, new TypeReference<List<EntityData>>() {});
			lst.forEach(entityModel -> {
				EntityGroup entityGroup=new EntityGroup();
				entityGroup.setEntityData(entityModel);
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

	public LinkedHashMap<String, EntityGroup> getCache() {
		return cache.entrySet().stream()
		        .sorted(new EntryComparator())
		        .collect(
		            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		            		LinkedHashMap::new));
	}
}


