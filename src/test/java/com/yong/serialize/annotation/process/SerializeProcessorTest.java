package com.yong.serialize.annotation.process;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.yong.serialize.Person;

public class SerializeProcessorTest {
	private SerializeProcessor processor;

	@Before
	public void setUp() {
		processor = new SerializeProcessor();
	}

	@Test(expected = NullPointerException.class)
	public void annotation_없으면_예외 () throws Exception {
		processor.process(new Object());
	}

	@Test
	public void json_serialize() throws Exception {
		String json = processor.process(new Person(30, "changyong"));

		assertThat(json, is("{\"age\":\"30\",\"name\":\"changyong\"}"));
	}

	@Test
	public void transient_필드는_무효() throws Exception {
		String json = processor.process(new Person(30, "changyong"));

		assertThat(json, is("{\"age\":\"30\",\"name\":\"changyong\"}"));
	}
}