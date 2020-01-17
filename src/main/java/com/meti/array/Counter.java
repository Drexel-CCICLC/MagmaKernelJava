package com.meti.array;

public interface Counter {
	static Counter create() {
		return new CounterImpl();
	}

	int poll();

	class CounterImpl implements Counter {
		private int value = 0;

		@Override
		public int poll() {
			value++;
            return value - 1;
		}
	}
}
