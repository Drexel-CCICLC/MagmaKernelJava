package com.meti.string;

import com.meti.PrimitiveType;
import com.meti.character.CharType;
import com.meti.point.PointerType;

public class StringType extends PrimitiveType {
    @Override
    public String render() {
		return new PointerType(new CharType()).render();
    }
}
