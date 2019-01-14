package org.brijframework.jpa.builder;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.util.InstanceUtil;

public class EntryComparator implements Comparator<Map.Entry<String,EntityGroup>>{

	@Override
	public int compare(Map.Entry<String,EntityGroup> o1, Map.Entry<String,EntityGroup> o2) {
		if(o2.getValue().getEntityObject()==null) {
			return -1;
		}
		if(o1.getValue().getEntityObject()==null) {
			return 1;
		}
		if(o1.getValue().getEntityObject().getClass().getName().equals(o2.getValue().getEntityObject().getClass().getName())) {
			return 0;
		}
		List<Field> fields1=InstanceUtil.getAllField(o1.getValue().getEntityObject().getClass());
		for(Field field1: fields1) {
			if(field1.getType().isAssignableFrom(o2.getValue().getEntityObject().getClass())) {
				return 1;
			}
		}
		return -1;
	}
}