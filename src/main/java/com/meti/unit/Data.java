package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Declarations;
import com.meti.type.TypeStack;

public interface Data {
	Aliaser getAliaser();

	Declarations getManager();

	TypeStack getTypeStack();
}
