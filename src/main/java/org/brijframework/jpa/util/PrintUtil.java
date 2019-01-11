package org.brijframework.jpa.util;

import java.lang.reflect.Field;
import java.util.List;

public class PrintUtil {

	public static String getObjectInfo(Object object) {
		StringBuilder builder=new StringBuilder(object.getClass().getSimpleName());
		builder.append("(");
		List<Field> fields=InstanceUtil.getAllField(object.getClass());
		int len=fields.size();
		for (Field field : fields) {
			Object value=InstanceUtil.getField(object, field);
			if(value!=null && object.getClass().getPackage().getName().equals(value.getClass().getPackage().getName())) {
				builder.append(field.getName()+"="+getObjectInfo(value));
			}else {
				builder.append(field.getName()+"="+value);
			}
			if(len>1) {
			 len--;
			 builder.append(",");
			}
		}
		builder.append(")");
		return builder.toString();
	}
}
