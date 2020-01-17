package com.meti.node.value.primitive.string;

import com.meti.node.PrimitiveType;
import com.meti.node.value.primitive.character.CharType;
import com.meti.node.value.primitive.point.PointerType;

public class StringType extends PrimitiveType {
    @Override
    public String render() {
		return new PointerType(new CharType()).render();
    }
}
