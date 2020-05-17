package com.uefa.fifamanager.model;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter (autoApply = false)
public class BooleanConverter implements AttributeConverter<Boolean, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {
		if (attribute) return 1;
		return 0;
	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		return dbData == 1;
	}

}