package org.brijframework.jpa.container;

import static org.brijframework.jpa.util.EntityConstants.REF;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.brijframework.jpa.builder.DataBuilder;
import org.brijframework.jpa.builder.RelationComparator;
import org.brijframework.jpa.builder.SequenceComparator;
import org.brijframework.jpa.context.EntityContext;
import org.brijframework.jpa.factories.EntityModelFactory;
import org.brijframework.jpa.group.EntityDataGroup;
import org.brijframework.jpa.model.EntityModel;
import org.brijframework.jpa.processor.EntityProcessor;
import org.brijframework.jpa.util.EntityConstants;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;

public final class EntityDataContainer {

	private EntityContext context;
	
	private LinkedHashMap<String, EntityDataGroup> cache = new LinkedHashMap<>();
	
	private static EntityDataContainer container;
	
	public static EntityDataContainer getContainer() {
		if(container==null) {
			container=new EntityDataContainer();
		}
		return container;
	}
	
	public void setContext(EntityContext context) {
		this.context = context;
	}
	
	public EntityContext getContext() {
		return context;
	}
	
	public LinkedHashMap<String, EntityDataGroup> getCache() {
		return cache;
	}
	
	public void register(EntityDataGroup model) {
		getCache().put(model.getId(), model);
	}

	public EntityDataGroup find(String model) {
		return getCache().get(model);
	}
	
	@SuppressWarnings("unchecked")
	public EntityDataContainer build() {
		getCache().forEach((id, entityGroup) -> {
			Object entityObject=DataBuilder.getDataObject(entityGroup.getEntityData());
			EntityModel entityModel=EntityModelFactory.getFactory().find(entityObject.getClass().getSimpleName());
			entityGroup.setEntityModel(entityModel);
			entityGroup.setEntityObject(entityObject);
		});
		getCache().forEach((id, entityGroup) -> {
			entityGroup.getEntityData().getProperties().forEach((key, val) -> {
				if (val instanceof Map) {
					Map<String, Object> mapVal = (Map<String, Object>) val;
					String ref = (String) mapVal.get(REF);
					if (ref != null) {
						val =getCache().containsKey(ref) ? getCache().get(ref).getEntityObject():null;
					} else {
						val = mapVal;
					}
					PropertyAccessorUtil.setProperty(entityGroup.getEntityObject(), key, val);
				}
			});
		});
		return this;
	}
	
	public EntityProcessor getEntityProcessor() {
		EntityProcessor processor =(EntityProcessor) getContext().getObject(EntityConstants.IMPORT_ADPTER_OBJECT);
		if(processor==null) {
			String adpter_class=getContext().getProperty(EntityConstants.IMPORT_ADPTER_CLASS);
			if(adpter_class==null || adpter_class.isEmpty()) {
				System.err.println("Please config "+EntityConstants.IMPORT_ADPTER_CLASS);
				return null;
			}
			processor=InstanceUtil.getInstance(adpter_class);
		}
		return processor;
	}

	public EntityDataContainer procced() {
		EntityProcessor processor=getEntityProcessor();
		if(processor==null) {
			System.err.println("Invalid config "+EntityConstants.IMPORT_ADPTER_CLASS);
			return this;
		}
		Collection<EntityDataGroup> values=getCache().values();
		this.laodEntities(processor,values);
		return this;
	}
	
	public EntityDataGroup forObject(Object object) {
		for (EntityDataGroup entityDataGroup : getCache().values()) {
			if(object.equals(entityDataGroup.getEntityObject())) {
				return entityDataGroup;
			}
		}
		return null;
	}
	
	public void laodEntities(EntityProcessor processor,Collection<EntityDataGroup> groups) {
		if (groups == null) {
			return;
		}
		System.err.println("EntityManager entities laoding...");
		processor.init();
		System.err.println("Sequence loading .....");
		groups.stream().filter(group->group.getEntityData().getSequence()!=null).sorted(new SequenceComparator()).forEach(group -> {
			if (!processor.constains(group.getEntityData(),group.getEntityModel(), group.getEntityObject())) {
				processor.persist(group.getEntityData(), group.getEntityModel(), group.getEntityObject());
			}else {
				processor.update(group.getEntityData(), group.getEntityModel(), group.getEntityObject());
			}
		});
		
		System.err.println("Relational loading .....");
		groups.stream().filter(group->group.getEntityData().getSequence()==null).sorted(new RelationComparator()).forEach(group -> {
			if (!processor.constains(group.getEntityData(),group.getEntityModel(), group.getEntityObject())) {
				processor.persist(group.getEntityData(),group.getEntityModel(), group.getEntityObject());
			}else {
				processor.update(group.getEntityData(),group.getEntityModel(), group.getEntityObject());
			}
		});
		
		System.err.println("Mapping loading .....");
		groups.forEach(group -> {
			
		});
		processor.finish();
		System.err.println("EntityManager entities laoded ");
	}
}
