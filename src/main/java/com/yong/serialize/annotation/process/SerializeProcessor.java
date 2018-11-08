package com.yong.serialize.annotation.process;

import static java.util.stream.Collectors.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import com.yong.serialize.annotation.Serialize;
import com.yong.serialize.annotation.Transient;

public class SerializeProcessor {

	public String process(Object target) throws Exception {
		Class<?> aClass = target.getClass();
		Objects.requireNonNull(aClass.getAnnotation(Serialize.class));

		BeanInfo beanInfo = Introspector.getBeanInfo(aClass);
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

		return Arrays.stream(descriptors)
			.filter(desc -> !desc.getName().equals("class"))
			.filter(desc -> !hasTransient(aClass, desc))
			.map(desc -> fieldToJsonAsString(desc, target))
			.collect(joining(",", "{", "}"));
	}

	private Field extractField(Class<?> aClass, PropertyDescriptor desc) {
		try {
			return aClass.getDeclaredField(desc.getName());
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	private Object invokeMethod(Method method, Object target) {
		try {
			return method.invoke(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean hasTransient(Class<?> aClass, PropertyDescriptor desc) {
		Field field = extractField(aClass, desc);
		return field.getAnnotation(Transient.class) != null;
	}

	private String fieldToJsonAsString(PropertyDescriptor desc, Object target) {
		return "\"" + desc.getName() + "\":\"" + invokeMethod(desc.getReadMethod(), target) + "\"";
	}
}
