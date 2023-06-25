package com.matafe.equino.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

	@Override
	public String convertToDatabaseColumn(Gender gender) {
		return gender.getCode();
	}

	@Override
	public Gender convertToEntityAttribute(String genderCodeFromDatabase) {
		return Gender.fromCode(genderCodeFromDatabase);
	}

}
