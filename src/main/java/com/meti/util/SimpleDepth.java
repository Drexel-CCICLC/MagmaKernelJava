package com.meti.util;

public class SimpleDepth implements Depth {
	private int value = 0;

	@Override
	public void level(){
		this.value = 0;
	}

	@Override
	public boolean isLevel() {
		return value == 0;
	}

	@Override
	public void sink() {
		value++;
	}

	@Override
	public void surface() {
		value--;
	}
}
