package org.brijframework.jpa.files;

public class EntityData extends ObjectJsonData{

	private Integer sequence;
	private String unique;
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public void setUnique(String unique) {
		this.unique = unique;
	}
	
	public String getUnique() {
		return unique;
	}
}
