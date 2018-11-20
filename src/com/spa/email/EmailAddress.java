package com.spa.email;

public class EmailAddress implements java.io.Serializable {
	private String address;
	private String name;

	public EmailAddress() {
	}

	public EmailAddress(String address) {
		this.address = address;
	}

	public EmailAddress(String address, String name) {
		this.address = address;
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
