package org.brijframework.jpa.model;

public class EntityRelation extends EntityField{
	String referencedColumnName;
    Class<?> targetEntity;
    String relation;
    String cascade;
    String mappedBy;
    boolean orphanRemoval;
    
    EntityForeignKey foreignKey;

	public String getReferencedColumnName() {
		return referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

	public Class<?> getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(Class<?> targetEntity) {
		this.targetEntity = targetEntity;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public boolean isOrphanRemoval() {
		return orphanRemoval;
	}

	public void setOrphanRemoval(boolean orphanRemoval) {
		this.orphanRemoval = orphanRemoval;
	}

	public EntityForeignKey getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(EntityForeignKey foreignKey) {
		this.foreignKey = foreignKey;
	}
    
}
