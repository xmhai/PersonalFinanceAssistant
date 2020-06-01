package com.linh.pfa.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
	STOCKS(1), REITS(2), BONDS(3), CASH(4);

	private int value;
	Category(int intValue) { this.value = intValue; }
	
	@JsonValue
	public int getValue() { return value; }

	@JsonCreator
    public static Category fromValue(int value) {
        for (Category e : Category.values()) {
            if(e.getValue() == value) return e;
        }
        
        return null;
    }
}
