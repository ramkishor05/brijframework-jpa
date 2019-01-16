package org.brijframework.jpa.util;

import org.brijframework.jpa.container.EntityDataContainer;
import org.brijframework.jpa.context.EntityContext;

public class TestMain {

	public static void main(String[] args) {
		String import_files="src/main/resources/db";
		String import_adpter="org.brijframework.jpa.persist.EntityProcessorImpl";
		System.setProperty("jpa.properties.import_files", import_files);
		System.setProperty("jpa.properties.import_adpter", import_adpter);
		EntityContext context=new EntityContext();
		context.start();
		System.out.println(EntityDataContainer.getContainer().getCache());
		context.stop();
	}

}
