package com.yong.serialize.annotation.process;

import java.lang.reflect.Field;
import java.util.Objects;

import com.yong.serialize.annotation.Serialize;
import com.yong.serialize.annotation.Transient;

public class SerializeProcessor {

	public String process(Object target) throws IllegalAccessException {
		Objects.requireNonNull(target.getClass().getAnnotation(Serialize.class));

		Class<?> clazz = target.getClass();

		StringBuilder builder = new StringBuilder("{");

		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			Transient t = field.getAnnotation(Transient.class);
			if(t != null) {
				continue;
			}

			builder.append("\"");
			builder.append(field.getName());
			builder.append("\"");
			builder.append(":");
			field.setAccessible(true);
			builder.append("\"");
			builder.append(field.get(target));
			builder.append("\",");
		}

		builder.replace(builder.length()-1, builder.length(), "");
		builder.append("}");

		return builder.toString();
	}
}
