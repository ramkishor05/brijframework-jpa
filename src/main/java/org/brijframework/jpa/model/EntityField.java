package org.brijframework.jpa.model;

public class EntityField {
	 String id;
	/**
	 * (Optional) The name of the column. Defaults to the property or field name.
	 */
	String name;

	/**
	 * (Optional) Whether the column is a unique key. This is a shortcut for the
	 * <code>UniqueConstraint</code> annotation at the table level and is useful for
	 * when the unique key constraint corresponds to only a single column. This
	 * constraint applies in addition to any constraint entailed by primary key
	 * mapping and to constraints specified at the table level.
	 */
	boolean unique;

	/**
	 * (Optional) Whether the database column is nullable.
	 */
	boolean nullable;

	/**
	 * (Optional) Whether the column is included in SQL INSERT statements generated
	 * by the persistence provider.
	 */
	boolean insertable;

	/**
	 * (Optional) Whether the column is included in SQL UPDATE statements generated
	 * by the persistence provider.
	 */
	boolean updatable;

	/**
	 * (Optional) The SQL fragment that is used when generating the DDL for the
	 * column.
	 * <p>
	 * Defaults to the generated SQL to create a column of the inferred type.
	 */
	String columnDefinition;

	/**
	 * (Optional) The name of the table that contains the column. If absent the
	 * column is assumed to be in the primary table.
	 */
	String table;

	/**
	 * (Optional) The column length. (Applies only if a string-valued column is
	 * used.)
	 */
	int length;

	/**
	 * (Optional) The precision for a decimal (exact numeric) column. (Applies only
	 * if a decimal column is used.) Value must be set by developer if used when
	 * generating the DDL for the column.
	 */
	int precision;

	/**
	 * (Optional) The scale for a decimal (exact numeric) column. (Applies only if a
	 * decimal column is used.)
	 */
	int scale;

	EntityModel model;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public String getColumnDefinition() {
		return columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		this.columnDefinition = columnDefinition;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EntityModel getModel() {
		return model;
	}
	
	public void setModel(EntityModel model) {
		this.model = model;
	}
	

}
