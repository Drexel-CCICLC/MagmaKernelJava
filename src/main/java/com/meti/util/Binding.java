package com.meti.util;

public interface Binding<T> {
	static <T> Binding<T> empty() {
		return of(null);
	}

	static <T> Binding<T> of(T value) {
		return new BindingImpl<>(value);
	}

	T get();

	void set(T value);

	final class BindingImpl<T> implements Binding<T> {
		private T value;

		private BindingImpl(T value) {
			this.value = value;
		}

		@Override
		public T get() {
			return value;
		}

		@Override
		public void set(T value) {
			this.value = value;
		}
	}
}
