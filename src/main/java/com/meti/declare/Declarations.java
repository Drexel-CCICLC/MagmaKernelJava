package com.meti.declare;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Supplier;

public interface Declarations {
    Declaration absolute(Collection<String> names);

	Declaration current();

	Declaration define(DeclarationBuilder builder);

    Declaration parent();

    Optional<Declaration> relative(String name);

    Declaration root();

    @Deprecated
    Stack<String> stack();

    String complete(Supplier<String> action);

    String renderInParentScope(Supplier<String> action);
}
