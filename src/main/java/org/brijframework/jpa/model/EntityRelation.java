package org.brijframework.jpa.model;

import java.lang.annotation.Annotation;

public class EntityRelation extends EntityField{
	
    Class<?> targetEntity;
    Annotation relation;
    Enum<?>[] cascade;
    Enum<?>[] fetch;
    boolean optional;

}
