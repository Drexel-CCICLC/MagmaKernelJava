package com.meti;

public interface Depth {
	static Depth create() {
		return new DepthImpl();
	}

	boolean isClose();

	boolean isLevel();

	void sink();

	void surface();

	final class DepthImpl implements Depth {
		private int value = 0;

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

		@Override
		public boolean isClose() {
			return value == 1;
		}
	}
}
