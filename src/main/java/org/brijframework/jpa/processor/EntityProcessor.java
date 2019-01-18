package org.brijframework.jpa.processor;

import org.brijframework.jpa.model.EntityData;
import org.brijframework.jpa.model.EntityModel;

public interface EntityProcessor {

	public abstract boolean constains(EntityData entityData, EntityModel entityModel, Object object);

	public abstract boolean finish();

	public abstract boolean init();

	public abstract boolean process(EntityData entityData, EntityModel entityModel, Object object);
}
