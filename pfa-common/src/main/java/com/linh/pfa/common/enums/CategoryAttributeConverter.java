package com.linh.pfa.common.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class CategoryAttributeConverter implements AttributeConverter<Category, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Category attribute) {
        return attribute!=null ? attribute.getValue() : null;
    }
 
    @Override
    public Category convertToEntityAttribute(Integer dbData) {
        return dbData!=null ? Category.fromValue(dbData) : null;
    }
}
