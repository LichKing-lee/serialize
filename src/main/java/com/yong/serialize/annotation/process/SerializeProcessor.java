package com.yong.serialize.annotation.process;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import com.yong.serialize.annotation.Serialize;
import com.yong.serialize.annotation.Transient;

public class SerializeProcessor {

	public String process(Object target) throws Exception {
		Class<?> aClass = target.getClass();
		Objects.requireNonNull(aClass.getAnnotation(Serialize.class));

		StringBuilder builder = new StringBuilder("{");

		BeanInfo beanInfo = Introspector.getBeanInfo(aClass);
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

		for(PropertyDescriptor descriptor : descriptors) {
			if(descriptor.getName().equals("class")) {
				continue;
			}

			Field field = aClass.getDeclaredField(descriptor.getName());
			Transient t = field.getAnnotation(Transient.class);
			if(t != null) {
				continue;
			}

			builder.append("\"");
			builder.append(descriptor.getName());
			builder.append("\"");
			builder.append(":");
			builder.append("\"");
			builder.append(descriptor.getReadMethod().invoke(target));
			builder.append("\",");
		}

		builder.replace(builder.length()-1, builder.length(), "");
		builder.append("}");

		return builder.toString();
	}
}
