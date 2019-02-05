package org.brijframework.jpa.builder;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.brijframework.jpa.files.ModelData;
import org.brijframework.jpa.util.EntityConstants;
import org.brijframework.util.accessor.PropertyAccessorUtil;
import org.brijframework.util.reflect.InstanceUtil;

public class DataBuilder {

	public static <T> T getDataObject( ModelData entityData ) {
		T dataObject = InstanceUtil.getInstance(entityData.getType());
		entityData.getProperties().forEach((key, val) -> {
			if (val == null) {
				PropertyAccessorUtil.setProperty(dataObject, key, val);
			} else {
				if(EntityConstants.CTD.equalsIgnoreCase(val.toString())) {
					val=new Date();
				}
				if (!(val instanceof Map) && !(val instanceof Collection)) {
					PropertyAccessorUtil.setProperty(dataObject, key, val);
				}
			}
		});
		return dataObject;
	}
}
