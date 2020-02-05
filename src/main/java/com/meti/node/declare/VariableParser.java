package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.struct.FieldNode;
import com.meti.node.struct.FunctionType;
import com.meti.node.struct.ObjectType;

import java.util.Optional;

public class VariableParser implements Parser {
    private final Declarations declarations;

    public VariableParser(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.contains(".")) {
            int period = trim.indexOf('.');
            String before = trim.substring(0, period);
            String after = trim.substring(period + 1);
            Type objectType = compiler.resolveValue(before);
            if (objectType instanceof ObjectType) {
                Declaration declaration = ((ObjectType) objectType).declaration();
                return buildField(declaration, after);
            } else {
                //object could be singleton
                if (objectType instanceof FunctionType) {
                    Type singletonType = compiler.resolveValue("_" + before);
                    if (singletonType instanceof ObjectType) {
                        Declaration declaration = ((ObjectType) singletonType).declaration();
                        return buildField(declaration, after);
                    } else {
                        throw new ParseException(before + " is not a singleton.");
                    }
                } else {
                    throw new ParseException(before + " is not an object.");
                }
            }
        } else {
            Declaration parent = declarations.parent();
            if (parent.hasChild(trim)) {
                if (parent.hasParameter(trim)) {
                    return buildField(parent, trim);
                } else {
                    return buildVariable(parent, trim);
                }
            }
            Declaration current = declarations.current();
            if (current.hasChild(trim) && !current.hasParameter(trim)) {
                return buildField(current, trim);
            } else {
                return buildVariable(declarations.parent(), trim);
            }
        }
    }

    private Optional<Node> buildVariable(Declaration parent, String trim) {
   /*     Optional<Declaration> child = current.child(trim);
        if(child.isPresent()) {
            return Optional.of(new FieldNode(current, trim));
        }*/
        Optional<Declaration> childOptional = parent.child(trim);
        if (childOptional.isPresent()) {
            Declaration child = childOptional.get();
            boolean functional = child.isFunctional();
            if (functional) {
                return Optional.of(new VariableNode(child.joinStack()));
            }
        }
        return Optional.of(new VariableNode(trim));
    }

    private Optional<Node> buildField(Declaration parent, String childName) {
        Declaration child = parent.child(childName)
                .orElseThrow(() -> new ParseException(parent.name() + "." + childName + " is " +
                        "not defined."));
        return child.isFunctional() ?
                Optional.of(new VariableNode(child.joinStack())) :
                Optional.of(new FieldNode(parent, childName));
    }

}
