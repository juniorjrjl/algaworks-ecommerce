package com.algaworks.ecommerce.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToSimNaoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(final Boolean attribute) {
        return attribute ? "SIM" : "NAO";
    }

    @Override
    public Boolean convertToEntityAttribute(final String dbData) {
        return dbData.equals("SIM");
    }

}
