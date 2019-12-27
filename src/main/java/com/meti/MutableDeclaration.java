package com.meti;

import com.meti.type.Type;
import com.meti.unit.Declaration;

public interface MutableDeclaration extends Declaration {
	void setType(Type type);
}
