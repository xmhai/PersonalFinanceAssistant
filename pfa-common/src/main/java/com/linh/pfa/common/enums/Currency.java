package com.linh.pfa.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {
	SGD(1), RMB(2), USD(3), HKD(4);

	private int value;
	Currency(int intValue) { this.value = intValue; }
	
	@JsonValue
	public int getValue() { return value; }

	@JsonCreator
    public static Currency fromValue(int value) {
        for (Currency e : Currency.values()) {
            if(e.getValue() == value) return e;
        }
        
        return null;
    }
}
