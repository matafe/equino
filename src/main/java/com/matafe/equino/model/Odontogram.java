package com.matafe.equino.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Odontogram implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkUpDate;

	@Column(length = 2000)
	private String anamnesis;

	@Column(length = 255)
	private String procedures;

	@Column(length = 255)
	private String sideExcursionLeftBefore;

	@Column(length = 255)
	private String sideExcursionRightBefore;

	@Column(length = 255)
	private String sideExcursionLeftAfter;

	@Column(length = 255)
	private String sideExcursionRightAfter;

	@Column(length = 255)
	private String recommendations;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Animal animal;

	private Integer teethWolf;

	private Integer teethWolfPresent;

	private Integer teethWolfIncluded;

	private Integer teethWolfExtraction;

	// TODO

	public Odontogram() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCheckUpDate() {
		return checkUpDate;
	}

	public void setCheckUpDate(Date checkUpDate) {
		this.checkUpDate = checkUpDate;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public String getAnamnesis() {
		return anamnesis;
	}

	public void setAnamnesis(String anamnesis) {
		this.anamnesis = anamnesis;
	}

	public String getProcedures() {
		return procedures;
	}

	public void setProcedures(String procedures) {
		this.procedures = procedures;
	}

	public String getSideExcursionLeftBefore() {
		return sideExcursionLeftBefore;
	}

	public void setSideExcursionLeftBefore(String sideExcursionLeftBefore) {
		this.sideExcursionLeftBefore = sideExcursionLeftBefore;
	}

	public String getSideExcursionRightBefore() {
		return sideExcursionRightBefore;
	}

	public void setSideExcursionRightBefore(String sideExcursionRightBefore) {
		this.sideExcursionRightBefore = sideExcursionRightBefore;
	}

	public String getSideExcursionLeftAfter() {
		return sideExcursionLeftAfter;
	}

	public void setSideExcursionLeftAfter(String sideExcursionLeftAfter) {
		this.sideExcursionLeftAfter = sideExcursionLeftAfter;
	}

	public String getSideExcursionRightAfter() {
		return sideExcursionRightAfter;
	}

	public void setSideExcursionRightAfter(String sideExcursionRightAfter) {
		this.sideExcursionRightAfter = sideExcursionRightAfter;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	public Integer getTeethWolf() {
		return teethWolf;
	}

	public void setTeethWolf(Integer teethWolf) {
		this.teethWolf = teethWolf;
	}

	public Integer getTeethWolfPresent() {
		return teethWolfPresent;
	}

	public void setTeethWolfPresent(Integer teethWolfPresent) {
		this.teethWolfPresent = teethWolfPresent;
	}

	public Integer getTeethWolfIncluded() {
		return teethWolfIncluded;
	}

	public void setTeethWolfIncluded(Integer teethWolfIncluded) {
		this.teethWolfIncluded = teethWolfIncluded;
	}

	public Integer getTeethWolfExtraction() {
		return teethWolfExtraction;
	}

	public void setTeethWolfExtraction(Integer teethWolfExtraction) {
		this.teethWolfExtraction = teethWolfExtraction;
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
		Odontogram other = (Odontogram) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Odontogram [id=" + id + ", checkUpDate=" + checkUpDate + "]";
	}

}
