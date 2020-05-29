package com.linh.pfa.common.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class ExchangeAttributeConverter implements AttributeConverter<Exchange, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Exchange attribute) {
        return attribute!=null ? attribute.getValue() : null;
    }
 
    @Override
    public Exchange convertToEntityAttribute(Integer dbData) {
        return dbData!=null ? Exchange.fromValue(dbData) : null;
    }
}
