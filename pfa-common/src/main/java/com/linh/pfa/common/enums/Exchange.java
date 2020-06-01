package com.linh.pfa.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Exchange is used for stock code generation for different API provider
public enum Exchange {
	SGX(1), CN(2), NYSE(3), HKEX(4), NASDAQ(5);

	private int value;
	Exchange(int intValue) { this.value = intValue; }
	
	@JsonValue
	public int getValue() { return value; }

	@JsonCreator
    public static Exchange fromValue(int value) {
        for (Exchange e : Exchange.values()) {
            if(e.getValue() == value) return e;
        }
        
        return null;
    }
}
