package com.meti;

import com.meti.array.ArrayResolver;
import com.meti.character.CharResolver;
import com.meti.integer.IntResolver;
import com.meti.invoke.InvocationResolver;
import com.meti.other.AnyResolver;
import com.meti.other.VoidResolver;
import com.meti.point.PointerResolver;
import com.meti.string.StringResolver;
import com.meti.struct.StructResolver;
import com.meti.variable.VariableResolver;

import java.util.Arrays;
import java.util.Collection;

class MagmaResolver extends ParentResolver {
    MagmaResolver(Declarations declarations) {
        this(
                new PointerResolver(),
                new InvocationResolver(),
                new ArrayResolver(),
                new StructResolver(),
                new StringResolver(),
                new AnyResolver(),
                new VoidResolver(),
                new CharResolver(),
                new IntResolver(),
                new VariableResolver(declarations)
        );
    }

    private MagmaResolver(Resolver... children) {
        this(Arrays.asList(children));
    }

    private MagmaResolver(Collection<Resolver> children) {
        super(children);
    }

}
