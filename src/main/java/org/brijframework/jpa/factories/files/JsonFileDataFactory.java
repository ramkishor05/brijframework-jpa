package org.brijframework.jpa.factories.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.files.EntityData;
import org.brijframework.jpa.files.ModelData;
import org.brijframework.util.WatchFactory;
import org.brijframework.util.formatter.PrintUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonFileDataFactory {

	private LinkedHashMap<String, ModelData> cache = new LinkedHashMap<>();
	
	private static JsonFileDataFactory factory;
	
	private EntityContext entityContext;
	
	public static JsonFileDataFactory getFactory() {
		if(factory==null) {
			factory=new JsonFileDataFactory();
		}
		return factory;
	}
	
	public JsonFileDataFactory load(File file) {
		System.err.println("JsonFileDataFactory loading start...");
		loadDir(file);
		System.err.println("JsonFileDataFactory loading done...");
		File dir=file.isDirectory()? file: file.getParentFile();
		if(WatchFactory.getFactory().fetch(dir)==null) {
			WatchFactory.getFactory().register(dir,this,"loadFile");
		}
		return this;
	}
	
	public void loadDir(File file) {
		if(file.isDirectory()) {
			for(File sub:file.listFiles()) {
				loadDir(sub);
			}
		}else {
			loadFile(file);
		}
	}
	
	public void loadFile(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileInputStream reader = new FileInputStream(file)) {
			List<ModelData> lst = mapper.readValue(reader, TypeFactory.defaultInstance().constructCollectionLikeType(List.class, ModelData.class));
			lst.forEach(fileObject -> {
				System.err.println("Register fileObject :"+PrintUtil.getObjectInfo(fileObject));
				register(fileObject);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setContext(EntityContext entityContext) {
		this.entityContext=entityContext;
	}
	
	public EntityContext getContext() {
		return entityContext;
	}

	public void register(ModelData jsonObject) {
		getCache().put(jsonObject.getId(), jsonObject);
	}
	
	public ModelData find(String key) {
		return getCache().get(key);
	}
	
	public LinkedHashMap<String, ModelData> getCache() {
		return cache;
	}
	
	public void forType(String type, Consumer<? super ModelData> action) {
		getCache().forEach((key,model)->{
			if(model.getType().equals(type)) {
				action.accept(model);
			}
		});
	}

	public void setAdpter(String string, Class<EntityData> class1) {
		
	}
}
