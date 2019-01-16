package org.brijframework.jpa.persist;

import org.brijframework.jpa.data.EntityData;

public interface EntityProcessor {

	public abstract boolean constains(EntityData entityModel, Object object);

	public abstract boolean finish();

	public abstract boolean init();

	public abstract boolean process(EntityData entityModel, Object object);
}
