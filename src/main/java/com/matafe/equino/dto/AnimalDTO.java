package com.matafe.equino.dto;

public class AnimalDTO {

	private final Long id;

	private final String name;

	public AnimalDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "AnimalDTO [id=" + id + ", name=" + name + "]";
	}

}
