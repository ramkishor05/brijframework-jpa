package org.brijframework.jpa.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.brijframework.jpa.files.ModelData;
import org.brijframework.util.accessor.MetaAccessorUtil;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;
import org.brijframework.util.support.Access;

public class EntityDataBuilder {

	public static <T> T getDataObject( ModelData entityData ) {
		T dataObject = InstanceUtil.getInstance(entityData.getType());
		entityData.getProperties().forEach((key, val) -> {
			if (val == null) {
				PropertyAccessorUtil.setProperty(dataObject, key, val);
			} else {
				if(EntityConstants.CTD.equalsIgnoreCase(val.toString())) {
					val=new Date();
				}
				if (!(val instanceof Map) && !(val instanceof Collection)) {
					PropertyAccessorUtil.setProperty(dataObject, key, val);
				}
			}
		});
		return dataObject;
	}
	

	@SuppressWarnings("unchecked")
	public static <T> T buildObject(Map<String, Object> rowMap, Class<?> cls) {
		T entity = (T) InstanceUtil.getInstance(cls);
		Set<String> unmapped = new HashSet<>();
		rowMap.forEach((key, value) -> {
			Field colling = MetaAccessorUtil.getFieldMeta(entity.getClass(), key, Access.PRIVATE);
			if (colling != null) {
				if (ReflectionUtils.isProjectClass(colling.getType())) {

				}
				PropertyAccessorUtil.setProperty(entity, colling, value);
			} else {
				unmapped.add(key);
			}
		});
		System.err.println("Invalid unmapped keys " + unmapped);
		return entity;
	}

}
