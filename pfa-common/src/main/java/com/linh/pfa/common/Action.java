package com.linh.pfa.common;

public enum Action {
	BUY(1), SELL(2);

	private int value;

	Action(int intValue) {
		this.value = intValue;
	}

	public int getValue() {
		return value;
	}
}
