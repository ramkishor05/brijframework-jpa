package org.brijframework.jpa.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.brijframework.jpa.model.EntityData;
import org.brijframework.jpa.model.FileObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileObjectFactory {

	public static final String APP_CONFIG_PATH = "provider.cfg.json";
	
	private LinkedHashMap<String, FileObject> cache = new LinkedHashMap<>();
	
	private static FileObjectFactory factory;
	
	public static FileObjectFactory getFactory() {
		if(factory==null) {
			factory=new FileObjectFactory();
		}
		return factory;
	}
	
	public void loadFactory() {
		File cfg=new File(APP_CONFIG_PATH);
		if(cfg.exists()) {
			load(cfg);
		}
	}
	
	public FileObjectFactory load(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileInputStream reader = new FileInputStream(file)) {
			List<FileObject> lst = mapper.readValue(reader, new TypeReference<List<EntityData>>() {});
			lst.forEach(entityModel -> {
				register(entityModel);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void register(FileObject model) {
		getCache().put(model.getId(), model);
	}
	
	public FileObject find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, FileObject> getCache() {
		return cache;
	}
}
