package org.brijframework.jpa.processor;

public class EntityManager<T> {

	public T update(T entity) {
		return entity;
	}

	public T add(T entity) {
		return entity;
	}

	public T remove(T entity) {
		return entity;
	}

	public T get(T entity) {
		return entity;
	}
	
	public boolean contains(String namedQuery) {
		return null != null;
	}
	
	public boolean contains(T namedQuery) {
		return null != null;
	}


	public T getUniueObject(String namedQuery) {
		return null;
	}

}
