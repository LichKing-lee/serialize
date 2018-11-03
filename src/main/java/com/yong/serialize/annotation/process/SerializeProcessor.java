package com.yong.serialize.annotation.process;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yong.serialize.annotation.Serialize;

public class SerializeProcessor {
	private ObjectMapper objectMapper;

	public SerializeProcessor() {
		objectMapper = new ObjectMapper();
	}

	public String process(Object target) throws JsonProcessingException {
		Objects.requireNonNull(target.getClass().getAnnotation(Serialize.class));

		return objectMapper.writeValueAsString(target);
	}
}
