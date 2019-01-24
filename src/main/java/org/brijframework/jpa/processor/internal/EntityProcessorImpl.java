package org.brijframework.jpa.processor.internal;

import org.brijframework.jpa.model.EntityData;
import org.brijframework.jpa.model.EntityModel;
import org.brijframework.jpa.processor.EntityProcessor;
import org.brijframework.util.formatter.PrintUtil;

public class EntityProcessorImpl implements EntityProcessor{

	@Override
	public boolean constains(EntityData entityData, EntityModel entityModel,  Object object) {
		return false;
	}
	
	@Override
	public boolean init() {
		return false;
	}
	
	@Override
	public boolean persist(EntityData entityData, EntityModel entityModel, Object entity) {
		System.out.println("PersistModel :"+PrintUtil.getObjectInfo(entity));
		return false;
	}
	
	@Override
	public boolean finish() {
		return false;
	}

	@Override
	public boolean update(EntityData entityData, EntityModel entityModel, Object entity) {
		System.out.println("UpdateModel :"+PrintUtil.getObjectInfo(entity));
		return false;
	}

	@Override
	public boolean delete(EntityData entityData, EntityModel entityModel, Object entity) {
		System.out.println("DeleteModel :"+PrintUtil.getObjectInfo(entity));
		return false;
	}

}
