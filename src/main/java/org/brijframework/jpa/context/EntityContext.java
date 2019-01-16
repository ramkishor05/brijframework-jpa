package org.brijframework.jpa.context;

import org.brijframework.jpa.container.EntityDataContainer;
import org.brijframework.jpa.container.EntityModelContainer;
import org.brijframework.jpa.factories.AnnoEntityModelFactory;
import org.brijframework.jpa.factories.EntityDataFactory;
import org.brijframework.jpa.factories.EntityModelFactory;
import org.brijframework.jpa.factories.JsonEntityDataFactory;

public class EntityContext {
  
	
	public void start() {
		EntityModelContainer entityModelContainer=EntityModelContainer.getContainer(); 
		EntityModelFactory entityModelFactory=EntityModelFactory.getFactory();
		entityModelFactory.setContainer(entityModelContainer);
		entityModelFactory.loadFactory();
		EntityModelFactory annoEntityDataFactory=AnnoEntityModelFactory.getFactory();
		annoEntityDataFactory.setContainer(entityModelContainer);
		annoEntityDataFactory.loadFactory();
		entityModelContainer.build();
		entityModelContainer.procced();
		EntityDataContainer entityDataContainer=EntityDataContainer.getContainer();
		EntityDataFactory entityDataFactory=EntityDataFactory.getFactory();
		entityDataFactory.setContainer(entityDataContainer);
		entityDataFactory.loadFactory();
		EntityDataFactory jsonEntityDataFactory=JsonEntityDataFactory.getFactory();
		jsonEntityDataFactory.setContainer(entityDataContainer);
		jsonEntityDataFactory.loadFactory();
		entityDataContainer.build();
		entityDataContainer.procced();
	}
	
	public void stop() {
		EntityDataContainer entityDataContainer=EntityDataContainer.getContainer();
		entityDataContainer.getCache().clear();
		EntityDataFactory entityDataFactory=EntityDataFactory.getFactory();
		entityDataFactory.getCache().clear();
		EntityDataFactory jsonEntityDataFactory=JsonEntityDataFactory.getFactory();
		jsonEntityDataFactory.getCache().clear();
	}
}
