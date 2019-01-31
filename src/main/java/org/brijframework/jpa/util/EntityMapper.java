package org.brijframework.jpa.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.brijframework.jpa.model.EntityField;
import org.brijframework.jpa.model.EntityModel;
import org.brijframework.jpa.model.EntityRelation;
import org.brijframework.support.enums.Access;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.AnnotationUtil;
import org.brijframework.util.reflect.ClassUtil;
import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.reflect.ReflectionUtils;

public class EntityMapper {

	public static EntityModel getEntityModel(Class<?> cls,Class<? extends Annotation> entity) {
		EntityModel model=new EntityModel();
		Map<String, Object> entityMap=getEntityMap(cls);
		PropertyAccessorUtil.setProperties(model, entityMap);
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
		EntityField entityField=ReflectionUtils.isProjectClass(field.getType()) || Collection.class.isAssignableFrom(field.getType())? new EntityRelation() :  new EntityField();
		entityField.setId(model.getId()+"_"+field.getName());
		entityField.setName(field.getName());
		entityField.setModel(model);
		PropertyAccessorUtil.setProperties(entityField, colMap);
		return entityField;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getEntityMap(Class<?> cls) {
		Map<String, Object> entityMap=new HashMap<>();
		entityMap.put("name", cls.getSimpleName());
		entityMap.put("id", cls.getSimpleName());
		if(ClassUtil.isClass(EntityConstants.JPA_ENTITY_ANNOTATION)) {
			Class<? extends Annotation> entity=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_ENTITY_ANNOTATION);
			if(entity!=null && cls.isAnnotationPresent(entity)) {
				Map<String, Object> entityAnnotationMap=AnnotationUtil.getAnnotationData(cls.getAnnotation(entity));
				String name=(String) entityAnnotationMap.remove("name");
				if(name!=null) {
					entityAnnotationMap.put("entity", name);
				}else {
					entityAnnotationMap.put("entity", cls.getSimpleName());
				}
				entityAnnotationMap.remove("uniqueConstraints");
				entityMap.putAll(entityAnnotationMap);
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_TABLE_ANNOTATION)) {
			Class<? extends Annotation> table=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_TABLE_ANNOTATION);
			if(table!=null && cls.isAnnotationPresent(table)) {
				Map<String, Object> tableAnnotationMap=AnnotationUtil.getAnnotationData(cls.getAnnotation(table));
				String name=(String) tableAnnotationMap.remove("name");
				if(name!=null) {
					tableAnnotationMap.put("table", name);
				}else {
					tableAnnotationMap.put("table", cls.getSimpleName());
				}
				tableAnnotationMap.remove("uniqueConstraints");
				entityMap.putAll(tableAnnotationMap);
			}
		}
		return entityMap;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getColumnMap(Field field) {
		Map<String, Object> colMap=new HashMap<>();
		if(ClassUtil.isClass(EntityConstants.JPA_COLUMN_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_COLUMN_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> colAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				Object column=colAnnotationMap.remove("name");
				if(column!=null && !column.toString().isEmpty()) {
					colAnnotationMap.put("column", column);
				}else {
					colAnnotationMap.put("column", field.getName());
				}
				colMap.putAll(colAnnotationMap);;
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_BASIC_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_BASIC_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> basicAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				colMap.putAll(basicAnnotationMap);;
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_JOIN_COLUMN_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_JOIN_COLUMN_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> joinColAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				Object column=joinColAnnotationMap.remove("name");
				if(column!=null && !column.toString().isEmpty()) {
					joinColAnnotationMap.put("column", column);
				}else {
					joinColAnnotationMap.put("column", field.getName());
				}
				colMap.putAll(joinColAnnotationMap);;
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_MANY_TO_ONE_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_MANY_TO_ONE_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> manyToOneAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				manyToOneAnnotationMap.put("relation", EntityConstants.RELATION_MANY_TO_ONE);
				colMap.putAll(manyToOneAnnotationMap);;
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_ONE_TO_MANY_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_ONE_TO_MANY_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> oneToManyAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				oneToManyAnnotationMap.put("relation", EntityConstants.RELATION_ONE_TO_MANY);
				colMap.putAll(oneToManyAnnotationMap);;
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_MANY_TO_MANY_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_MANY_TO_MANY_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> manyToManyAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				manyToManyAnnotationMap.put("relation", EntityConstants.RELATION_MANY_TO_MANY);
				colMap.putAll(manyToManyAnnotationMap);;
			}
		}
		if(ClassUtil.isClass(EntityConstants.JPA_ONE_TO_ONE_ANNOTATION)) {
			Class<? extends Annotation> col=(Class<? extends Annotation>) ClassUtil.getClass(EntityConstants.JPA_ONE_TO_ONE_ANNOTATION);
			if(col!=null && field.isAnnotationPresent(col)) {
				Map<String, Object> oneToOneAnnotationMap=AnnotationUtil.getAnnotationData(field.getAnnotation(col));
				oneToOneAnnotationMap.put("relation", EntityConstants.RELATION_ONE_TO_ONE);
				colMap.putAll(oneToOneAnnotationMap);;
			}
		}
		return colMap;
	}
	
}
