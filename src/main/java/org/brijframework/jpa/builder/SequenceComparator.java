package org.brijframework.jpa.builder;

import java.util.Comparator;

import org.brijframework.jpa.group.EntityDataGroup;

public class SequenceComparator implements Comparator<EntityDataGroup>{

	@Override
	public int compare(EntityDataGroup o1, EntityDataGroup o2) {
		return o1.getEntityData().getSequence().compareTo(o2.getEntityData().getSequence());
	}
}
