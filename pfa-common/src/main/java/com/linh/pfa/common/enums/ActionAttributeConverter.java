package com.linh.pfa.common.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class ActionAttributeConverter implements AttributeConverter<Action, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Action attribute) {
        return attribute!=null ? attribute.getValue() : null;
    }
 
    @Override
    public Action convertToEntityAttribute(Integer dbData) {
        return dbData!=null ? Action.fromValue(dbData) : null;
    }
}
