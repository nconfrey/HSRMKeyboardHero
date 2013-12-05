package model;

public enum Stroke {
	F1((byte)(1<<0)),
	F2((byte)(1<<1)),
	F3((byte)(1<<2)),
	F4((byte)(1<<3)),
	F5((byte)(1<<4));
	
	private final byte value;
	
	private Stroke(byte value) {
		this.value = value;
	}
	
	public byte getValue() {
		return value;
	}
}
