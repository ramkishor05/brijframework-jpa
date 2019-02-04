package org.brijframework.jpa.processor;

import org.brijframework.jpa.files.EntityData;
import org.brijframework.jpa.model.EntityModel;

public interface EntityManager<T> {

	public T update(T entity);

	public T add(T entity) ;

	public T remove(T entity);

	public T get(T entity) ;
	
	public boolean contains(String namedQuery);
	
	public boolean contains(T namedQuery);

	public T getUniueObject(String namedQuery);

	public T merge(T entity);

	public Object update(EntityData entityData, EntityModel entityModel, Object entity);

	public T update(EntityModel entityModel, T entity);

}
