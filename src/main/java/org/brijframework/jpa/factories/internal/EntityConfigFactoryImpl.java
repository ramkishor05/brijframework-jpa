package org.brijframework.jpa.factories.internal;

import java.io.File;

import org.brijframework.jpa.factories.EntityConfigFactory;
import org.brijframework.jpa.factories.files.JsonFileDataFactory;
import org.brijframework.jpa.util.EntityDataBuilder;
import org.brijframework.jpa.util.EntityConstants;

public class EntityConfigFactoryImpl extends EntityConfigFactory{

	private static EntityConfigFactoryImpl factory;
	
	public static EntityConfigFactoryImpl getFactory() {
		if (factory == null) {
			factory = new EntityConfigFactoryImpl();
		}
		return factory;
	}
	
	@Override
	public void loadFactory() {
		JsonFileDataFactory cfg=JsonFileDataFactory.getFactory();
		cfg.setContext(getContext());
		String configration=getContext().getProperty(EntityConstants.IMPORT_CONFIG_PATH);
		if(configration==null) {
			System.err.println("configration not found : "+EntityConstants.IMPORT_CONFIG_PATH);
			return ;
		}
		File config_path=new File(configration);
		if(!config_path.exists()) {
			System.err.println("configration not exists : "+configration);
			return ;
		}
		cfg.load(config_path);
		JsonFileDataFactory.getFactory().getCache().forEach((id, configData)->{
			try {
				Object configModel=EntityDataBuilder.getDataObject(configData);
				this.register(id,configModel);
			}catch (Exception e) {
			}
		});
		JsonFileDataFactory.getFactory().getCache().forEach((id, configData)->{
			try {
				Object configModel=find(id);
				this.mergeRelationship(configData, configModel);
			}catch (Exception e) {
			}
		});
	}
}
