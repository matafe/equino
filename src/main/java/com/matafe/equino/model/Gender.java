package com.matafe.equino.model;

public enum Gender {

	MALE("M"), FEMALE("F");

	private String code;

	private Gender(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Gender fromCode(String code) {
		switch (code) {
		case "M":
			return Gender.MALE;
		case "F":
			return Gender.FEMALE;
		default:
			throw new IllegalArgumentException("Gender with code [" + code + "] not supported.");
		}
	}
}
