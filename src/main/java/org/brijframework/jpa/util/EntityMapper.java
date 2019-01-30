package org.brijframework.jpa.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.brijframework.jpa.model.EntityField;
import org.brijframework.jpa.model.EntityModel;
import org.brijframework.support.enums.Access;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.AnnotationUtil;
import org.brijframework.util.reflect.ClassUtil;
import org.brijframework.util.reflect.FieldUtil;

public class EntityMapper {

	public static EntityModel getEntityModel(Class<?> cls,Class<? extends Annotation> entity) {
		EntityModel model=new EntityModel();
		Map<String, Object> entityMapper=new HashMap<>();
		entityMapper.put("name", cls.getSimpleName());
		entityMapper.put("id", cls.getSimpleName());
		Map<String, Object> entityMap=getEntityMap(cls);
		if(entityMap!=null && entityMap.get("name")!=null && !entityMap.get("name").toString().isEmpty()) {
			entityMapper.put("entity", entityMap.get("name"));
		}else {
			entityMapper.put("entity", cls.getSimpleName());
		}
		Map<String, Object> tableMap=getTableMap(cls);
		if(tableMap!=null && tableMap.get("name")!=null && !tableMap.get("name").toString().isEmpty()) {
			entityMapper.put("table", tableMap.get("name"));
		}else {
			entityMapper.put("table", cls.getSimpleName());
		}
		PropertyAccessorUtil.setProperties(model, entityMapper);
		Map<String,EntityField> properties=new HashMap<>();
		for(Field field:FieldUtil.getAllField(cls, Access.PRIVATE_NO_STATIC_FINAL)) {
			Map<String, Object>  colMap=getColumnMap(field);
			if(colMap==null) {
				continue;
			}
			EntityField entityField=getEntityField(model,field,colMap);
			properties.put(entityField.getId(), entityField);
		}
		model.setProperties(properties);
		return model;
	}
		
	private static EntityField getEntityField(EntityModel model, Field field, Map<String, Object> colMap) {
		EntityField entityField=new EntityField();
		entityField.setId(model.getId()+"_"+field.getName());
		entityField.setName(field.getName());
		entityField.setModel(model);
		Object column=colMap.remove("name");
		colMap.put("column", column);
		PropertyAccessorUtil.setProperties(entityField, colMap);
		return entityField;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getTableMap(Class<?> cls) {
		if(ClassUtil.isClass(EntityConstants.JPA_TABLE)) {
			Class<? extends Annotation> table=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_TABLE);
			if(table!=null && cls.isAnnotationPresent(table)) {
				return AnnotationUtil.getAnnotationData(cls.getAnnotation(table));
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getEntityMap(Class<?> cls) {
		if(ClassUtil.isClass(EntityConstants.JPA_ENTITY)) {
			Class<? extends Annotation> entity=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_ENTITY);
			if(entity!=null && cls.isAnnotationPresent(entity)) {
			   return AnnotationUtil.getAnnotationData(cls.getAnnotation(entity));
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getColumnMap(Field field) {
		if(ClassUtil.isClass(EntityConstants.JPA_COLUMN)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_COLUMN);
			if(col!=null && field.isAnnotationPresent(col)) {
				return AnnotationUtil.getAnnotationData(field.getAnnotation(col));
			}
		}
		return null;
	}
	
}
