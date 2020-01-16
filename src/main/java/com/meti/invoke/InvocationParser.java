package com.meti.invoke;

import com.meti.*;
import com.meti.Compiler;
import com.meti.variable.VariableNode;

import java.util.*;
import java.util.stream.Collectors;

public class InvocationParser implements Parser {
    private final Declarations declarations;

    public InvocationParser(Declarations declarations) {
        this.declarations = declarations;
    }

    private Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.contains("(") && trim.endsWith(")")) {
            int start = trim.indexOf('(');
            int end = trim.indexOf(')');
            String callerString = trim.substring(0, start).trim();
            String argumentString = trim.substring(start + 1, end);
            Type callerType = compiler.resolveValue(callerString);
            Node caller = compiler.parseSingle(callerString);
            String[] args = argumentString.split(",");
            List<Node> arguments = new ArrayList<>(Arrays.stream(args)
                    .filter(arg -> !arg.isBlank())
                    .map(compiler::parseSingle)
                    .collect(Collectors.toList()));
            //TODO: pass method as functional parameter using anonymous functions
            Optional<Declaration> declaration = declarations.relative(callerString);
            if(declaration.isPresent()) {
                List<Declaration> ancestors = new ArrayList<>();
                Optional<Declaration> current = declaration;
                while(current.isPresent()) {
                    Declaration actual = current.get();
                    ancestors.add(actual);
                    current = actual.parent();
                }
                ancestors.subList(1, ancestors.size() - 1)
                        .stream()
                        .map(Declaration::name)
                        .map(name -> name + "_")
                        .map(VariableNode::new)
                        .forEach(arguments::add);
            }
            return Optional.of(new InvocationNode(caller, arguments, callerType.doesReturnVoid()));
        }
        return Optional.empty();
    }

    @Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
