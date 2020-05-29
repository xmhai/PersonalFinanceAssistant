package com.linh.pfa.common.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class ActionAttributeConverter implements AttributeConverter<Action, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Action action) {
        return action!=null ? action.getValue() : null;
    }
 
    @Override
    public Action convertToEntityAttribute(Integer value) {
        return value!=null ? Action.fromValue(value) : null;
    }
}
