package org.brijframework.jpa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InstanceUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String className) {
		try {
			Class<?> cls = Class.forName(className);
			return (T) cls.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setField(Object object, String key, Object value) {
		try {
			Field field = object.getClass().getDeclaredField(key);
			field.setAccessible(true);
			field.set(object, cast(field,value));
		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static Object cast(Field field, Object value) {
		if(Long.class.isAssignableFrom(field.getType())) {
			return Long.valueOf(value.toString());
		}
		return value;
	}

	public static List<Field> getAllField(Class<? extends Object> cls) {
		List<Field> fields=new ArrayList<>();
		for(Field field:cls.getDeclaredFields()) {
			fields.add(field);
		}
		return fields;
	}
	
	public static Object getField(Object object, String field) {
		try {
			return getField(object,object.getClass().getDeclaredField(field));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getField(Object object, Field field) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalArgumentException | SecurityException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setField(Object object, Field field,Object val) {
		try {
			field.setAccessible(true);
			field.set(object,val);
		} catch (IllegalArgumentException | SecurityException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
