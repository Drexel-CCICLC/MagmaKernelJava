package com.meti;

import java.util.Map;

public interface GenericStruct extends Struct {
	Map<String, Struct> generics();
}
