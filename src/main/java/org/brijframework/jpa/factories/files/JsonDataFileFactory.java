package org.brijframework.jpa.factories.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.brijframework.jpa.files.ObjectJsonData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataFileFactory {

	private LinkedHashMap<String, ObjectJsonData> cache = new LinkedHashMap<>();
	
	private static JsonDataFileFactory factory;
	
	public static JsonDataFileFactory getFactory() {
		if(factory==null) {
			factory=new JsonDataFileFactory();
		}
		return factory;
	}
	
	public JsonDataFileFactory load(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileInputStream reader = new FileInputStream(file)) {
			List<ObjectJsonData> lst = mapper.readValue(reader, new TypeReference<List<ObjectJsonData>>() {});
			lst.forEach(fileObject -> {
				register(fileObject);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void register(ObjectJsonData jsonObject) {
		getCache().put(jsonObject.getId(), jsonObject);
	}
	
	public ObjectJsonData find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, ObjectJsonData> getCache() {
		return cache;
	}
}
