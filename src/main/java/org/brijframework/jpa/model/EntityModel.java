package org.brijframework.jpa.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityModel {

	private String id;
	private String entity;
	private String table;
	private String name;
	private String catalog;
	private String schema;
	
	private List<EntityConstraint> constraints;
	private List<EntityIndex> indexes;
	private Map<String, EntityField> properties;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<EntityConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<EntityConstraint> constraints) {
		this.constraints = constraints;
	}

	public List<EntityIndex> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<EntityIndex> indexes) {
		this.indexes = indexes;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Map<String, EntityField> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, EntityField> properties) {
		this.properties = properties;
	}
	
	public Map<String, EntityRelation> getRelations(){
		Map<String, EntityRelation> relation=new HashMap<>();
		this.getProperties().forEach((key,field)->{
			if(field instanceof EntityRelation) {
			 relation.put(key, (EntityRelation)field);
			}
		});
		return relation;
	}
}
