package com.example.product_service.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToShortConverter implements AttributeConverter<Boolean, Short> {

    @Override
    public Short convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute ? (short) 1 : (short) 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData != 0;
    }
}

