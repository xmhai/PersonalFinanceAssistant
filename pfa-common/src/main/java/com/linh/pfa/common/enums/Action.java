package com.linh.pfa.common.enums;

public enum Action {
	BUY(1), SELL(2);

	private int value;
	Action(int intValue) { this.value = intValue; }
	public int getValue() { return value; }

    public static Action fromValue(int value) {
        for (Action e : Action.values()) {
            if(e.getValue() == value) return e;
        }
        
        return null;
    }
}
