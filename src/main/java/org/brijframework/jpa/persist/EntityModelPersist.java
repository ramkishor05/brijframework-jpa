package org.brijframework.jpa.persist;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.brijframework.jpa.EntityGroup;
import org.brijframework.jpa.EntityModel;
import org.brijframework.jpa.util.InstanceUtil;

public abstract class EntityModelPersist {

	public void laodEntities(Collection<EntityGroup> groups) {
		if(groups==null) {
			return ;
		}
		System.err.println("EntityManager laoding entities ...");
		this.init();
		groups.forEach(group->{
			if(!isConstains(group.getEntityModel(), group.getEntityObject())) {
				this.process(group.getEntityModel(), group.getEntityObject());
			}
		});
		this.finish();
		System.err.println("EntityManager laoded ");
	}
	public abstract boolean isConstains(EntityModel entityModel,Object object);
	public abstract void finish();
	public abstract void init();
	public abstract void process(EntityModel entityModel,Object object);
}

class EntityModelComparator implements Comparator<Object>{

	@Override
	public int compare(Object o1, Object o2) {
		List<Field> fields1=InstanceUtil.getAllField(o1.getClass());
		for(Field field1: fields1) {
			if(o2.getClass().isAssignableFrom(field1.getType())) {
				return 1;
			}
		}
		List<Field> fields2=InstanceUtil.getAllField(o2.getClass());
		for(Field field2: fields2) {
			if(o1.getClass().isAssignableFrom(field2.getType())) {
				return -1;
			}
		}
		return 0;
	}
}