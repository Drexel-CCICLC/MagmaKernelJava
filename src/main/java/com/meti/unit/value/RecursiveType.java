package com.meti.unit.value;

import com.meti.type.Type;
import com.meti.unit.Declaration;

public interface RecursiveType extends Type {
	Declaration parentDeclaration();
}
