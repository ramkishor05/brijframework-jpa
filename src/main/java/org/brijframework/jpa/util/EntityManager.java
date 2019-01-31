package org.brijframework.jpa.util;

public class EntityManager {
	
	private static EntityManager manager;
	public EntityManager getManager() {
		if(manager==null) {
			manager=new EntityManager();
		}
		return manager;
	}
}
