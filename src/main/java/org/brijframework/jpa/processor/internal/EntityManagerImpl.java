package org.brijframework.jpa.processor.internal;

import org.brijframework.jpa.container.EntityDataContainer;
import org.brijframework.jpa.container.EntityModelContainer;
import org.brijframework.jpa.group.EntityDataGroup;
import org.brijframework.jpa.model.EntityData;
import org.brijframework.jpa.model.EntityModel;
import org.brijframework.jpa.processor.EntityManager;
import org.brijframework.jpa.util.EntityConstants;
import org.brijframework.support.enums.Access;
import org.brijframework.util.accessor.PropertyAccessorUtil;

public class EntityManagerImpl<T>  implements EntityManager<T>{
	private EntityModelContainer entityModelContainer=EntityModelContainer.getContainer();
	private EntityDataContainer entityDataContainer=EntityDataContainer.getContainer();
	
	@Override
	public T update(T entity) {
		EntityModel entityModel= entityModelContainer.find(entity.getClass().getSimpleName());
		if(entityModel!=null) {
			entityModel.getRelations().forEach((key,field)->{
				if(EntityConstants.RELATION_ONE_TO_ONE.equals(field.getRelation()) && field.getMappedBy()!=null && !field.getMappedBy().isEmpty()) {
					Object mappedBy=PropertyAccessorUtil.getProperty(entity, field.getName(), Access.PRIVATE);
					if(mappedBy!=null) {
						EntityDataGroup mappedDataGroup=entityDataContainer.forObject(mappedBy);
						PropertyAccessorUtil.setProperty(mappedBy, field.getMappedBy(), Access.PRIVATE,entity);
						this.update(mappedDataGroup.getEntityData(), mappedDataGroup.getEntityModel(), mappedBy);
					}
				}
			});
		}
		return null;
	}
	
	@Override
	public Object update(EntityData entityData, EntityModel entityModel,Object entity) {
		if(entityDataContainer.getEntityProcessor()!=null)
		entityDataContainer.getEntityProcessor().update(entityData, entityModel, entity);
		return entity;
	}
	
	@Override
	public T update(EntityModel entityModel, T entity) {
		entityModel.getRelations().forEach((key,field)->{
			if(EntityConstants.RELATION_ONE_TO_ONE.equals(field.getRelation()) && field.getMappedBy()!=null && !field.getMappedBy().isEmpty()) {
				Object mappedBy=PropertyAccessorUtil.getProperty(entity, field.getName(), Access.PRIVATE);
				if(mappedBy!=null) {
					EntityDataGroup mappedDataGroup=entityDataContainer.forObject(mappedBy);
					PropertyAccessorUtil.setProperty(mappedBy, field.getMappedBy(), Access.PRIVATE,entity);
					this.update(mappedDataGroup.getEntityData(), mappedDataGroup.getEntityModel(), mappedBy);
				}
			}
		});
		return entity;
	}
	
	@Override
	public T merge(T entity) {
		EntityModel entityModel= entityModelContainer.find(entity.getClass().getSimpleName());
		if(entityModel!=null) {
			entityModel.getRelations().forEach((key,field)->{
				if(EntityConstants.RELATION_ONE_TO_ONE.equals(field.getRelation()) && field.getMappedBy()!=null && !field.getMappedBy().isEmpty()) {
					Object mappedBy=PropertyAccessorUtil.getProperty(entity, field.getName(), Access.PRIVATE);
					if(mappedBy!=null) {
						EntityDataGroup mappedDataGroup=entityDataContainer.forObject(mappedBy);
						PropertyAccessorUtil.setProperty(mappedBy, field.getMappedBy(), Access.PRIVATE,entity);
						this.update(mappedDataGroup.getEntityData(), mappedDataGroup.getEntityModel(), mappedBy);
					}
				}
			});
		}
		return entity;
	}

	@Override
	public T add(T entity) {
		return null;
	}

	@Override
	public T remove(T entity) {
		return null;
	}

	@Override
	public T get(T entity) {
		return null;
	}

	@Override
	public boolean contains(String namedQuery) {
		return false;
	}

	@Override
	public boolean contains(T namedQuery) {
		return false;
	}

	@Override
	public T getUniueObject(String namedQuery) {
		return null;
	}
	
}
