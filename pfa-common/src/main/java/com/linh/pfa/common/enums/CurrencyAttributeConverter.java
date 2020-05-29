package com.linh.pfa.common.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class CurrencyAttributeConverter implements AttributeConverter<Currency, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Currency attribute) {
        return attribute!=null ? attribute.getValue() : null;
    }
 
    @Override
    public Currency convertToEntityAttribute(Integer dbData) {
        return dbData!=null ? Currency.fromValue(dbData) : null;
    }
}
