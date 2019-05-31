package org.brijframework.jpa.factories.data.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.brijframework.jpa.factories.data.EntityDataFactory;
import org.brijframework.jpa.files.EntityData;
import org.brijframework.jpa.util.EntityConstants;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonEntityDataFactory extends EntityDataFactory{
	
	private static JsonEntityDataFactory factory;

	public static JsonEntityDataFactory getFactory() {
		if (factory == null) {
			factory = new JsonEntityDataFactory();
		}
		return factory;
	}
	
	@Override
	public JsonEntityDataFactory loadFactory() {
		String fileNames=getContext().getProperty(EntityConstants.IMPORT_IMPORT_FILES);
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
	
	public JsonEntityDataFactory loadEntities(File file) {
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