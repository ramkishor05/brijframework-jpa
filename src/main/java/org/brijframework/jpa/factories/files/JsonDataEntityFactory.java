package org.brijframework.jpa.factories.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.brijframework.jpa.factories.EntityDataFactory;
import org.brijframework.jpa.files.EntityData;
import org.brijframework.jpa.util.EntityConstants;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataEntityFactory extends EntityDataFactory{
	
	private static JsonDataEntityFactory factory;

	public static JsonDataEntityFactory getFactory() {
		if (factory == null) {
			factory = new JsonDataEntityFactory();
		}
		return factory;
	}
	
	@Override
	public JsonDataEntityFactory loadFactory() {
		String fileNames=getContext().getProperty(EntityConstants.IMPORT_FILES);
		if(fileNames==null || fileNames.isEmpty()) {
			return this;
		}
		List<File> dbFiles=new ArrayList<>();
		for(String fileName:fileNames.split(",")) {
			dbFiles.add(new File(fileName));
		}
		if(!dbFiles.isEmpty()) {
			System.err.println("EntityModelBuilder loading from - >"+fileNames);
			for(File file:dbFiles) {
				loadFile(file);
			}
		}else {
			System.err.println("EntityModelBuilder does not config 'jpa.properties.import_files'");
		}
		return this;
	}
	
	private void loadFile(File file) {
		if(file.isDirectory()) {
			for(File sub:file.listFiles()) {
				this.loadFile(sub);
			}
		}else {
			this.loadEntities(file);
		}
	}
	
	public JsonDataEntityFactory loadEntities(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileInputStream reader = new FileInputStream(file)) {
			List<EntityData> lst = mapper.readValue(reader, new TypeReference<List<EntityData>>() {});
			lst.forEach(entityModel -> {
				register(entityModel);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}