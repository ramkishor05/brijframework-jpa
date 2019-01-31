package org.brijframework.jpa.util;

public final class EntityConstants {

	public final static String REF = "@ref";
	public final static String CTD="@currentDate";
	public static String IMPORT_CONFIG_PATH = "jpa.configration.path";
	public static String IMPORT_PACKAGES = "jpa.properties.packages";
	public static String IMPORT_FILES = "jpa.properties.import_files";
	public static String IMPORT_ADPTER_CLASS = "jpa.properties.import_adpter_class";
	public static String IMPORT_ADPTER_OBJECT = "jpa.properties.import_adpter_object";
	
	public static String JPA_TABLE_ANNOTATION="javax.persistence.Table";
	public static String JPA_ENTITY_ANNOTATION="javax.persistence.Entity";
	public static String JPA_COLUMN_ANNOTATION="javax.persistence.Column";
	public static String JPA_BASIC_ANNOTATION="javax.persistence.Basic";
	public static String JPA_JOIN_COLUMN_ANNOTATION="javax.persistence.JoinColumn";
	public static String JPA_MANY_TO_ONE_ANNOTATION="javax.persistence.ManyToOne";
	public static String JPA_MANY_TO_MANY_ANNOTATION="javax.persistence.ManyToMany";
	public static String JPA_ONE_TO_MANY_ANNOTATION="javax.persistence.OneToMany";
	public static String JPA_ONE_TO_ONE_ANNOTATION="javax.persistence.OneToOne";
	
	public static String RELATION_MANY_TO_ONE="MANY_TO_ONE";
	public static String RELATION_MANY_TO_MANY="MANY_TO_MANY";
	public static String RELATION_ONE_TO_ONE="ONE_TO_ONE";
	public static String RELATION_ONE_TO_MANY="ONE_TO_MANY";
	
}
