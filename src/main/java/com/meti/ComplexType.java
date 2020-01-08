package com.meti;

public interface ComplexType extends StructType, PointerType {

	@Override
	default boolean isVariable() {
		return render().equals(".");
	}
}
