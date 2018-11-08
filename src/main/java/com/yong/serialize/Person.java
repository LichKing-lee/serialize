package com.yong.serialize;

import com.yong.serialize.annotation.Serialize;
import com.yong.serialize.annotation.Transient;

@Serialize
public class Person {
	private int age;
	private String name;
	@Transient
	private String email;
	private String address;

	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public Person(int age, String name, String address) {
		this.age = age;
		this.name = name;
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
