package com.matafe.equino.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Animal implements Comparable<Animal>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 255)
	private String name;

	@Min(value = 0L)
	@NotNull
	@Column(nullable = false, length = 3)
	private Integer age;

	@NotNull
	@Convert(converter = GenderConverter.class)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Owner owner;

	@Column(name = "owner_id", insertable=false, updatable=false, nullable = false)
	private Long ownerId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "animal", fetch = FetchType.LAZY)
	private List<Odontogram> odontograms;

	public Animal() {
	}

	public Animal(Long id, @NotBlank String name, @NotNull Integer age, @NotNull Gender gender) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<Odontogram> getOdontograms() {
		return odontograms;
	}

	public void setOdontograms(List<Odontogram> odontograms) {
		this.odontograms = odontograms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int compareTo(Animal a) {
		return name.compareTo(a.getName());
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}

}
