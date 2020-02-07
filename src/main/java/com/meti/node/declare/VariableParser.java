package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.struct.FieldNode;
import com.meti.node.struct.type.FunctionType;
import com.meti.node.struct.type.ObjectType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

import java.util.Optional;

public class VariableParser implements Parser {
    private final Declarations declarations;

    public VariableParser(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        return trim.contains(".") ?
                parseAccessor(compiler, trim) :
                parseSimple(trim);
    }

    private Optional<Node> parseAccessor(Compiler compiler, String trim) {
        String parent = trim.substring(0, trim.indexOf('.'));
        String child = trim.substring(trim.indexOf('.') + 1);
        Type parentType = compiler.resolveValue(parent);
        return parentType instanceof ObjectType ?
                parseObject(child, (ObjectType) parentType) :
                parseSingleton(compiler, parent, child, parentType);
    }

    private Optional<Node> parseObject(String child, ObjectType parentType) {
        Declaration declaration = parentType.declaration();
        return buildField(declaration, child);
    }

    private Optional<Node> parseSimple(String trim) {
        Optional<Node> parent = parseParameter(trim);
        return parent.isPresent() ? parent : parseChild(trim);
    }

    private Optional<Node> buildField(Declaration parent, String childName) {
        Declaration child = parent.child(childName).orElseThrow(
                () -> new ParseException(parent.name() + "." + childName + " is " + "not defined."));
        return child.isFunctional() ?
                Optional.of(new VariableNode(child.joinStack())) :
                Optional.of(new FieldNode(parent, childName));
    }

    private Optional<Node> parseSingleton(Compiler compiler, String before, String after, Type parentType) {
        if (parentType instanceof FunctionType) {
            return parseReference(compiler, before, after);
        } else {
            throw new ParseException(before + " is not an reference to a singleton.");
        }
    }

    private Optional<Node> parseReference(Compiler compiler, String before, String after) {
        Type singletonType = compiler.resolveValue("_" + before);
        if (singletonType instanceof ObjectType) {
            return parseObject(after, (ObjectType) singletonType);
        } else {
            throw new ParseException(before + " is not an instance of a singleton.");
        }
    }

    private Optional<Node> parseParameter(String childName) {
        return declarations.parent(childName)
                .filter(declaration -> !declarations.isRoot(declaration))
                .filter(declaration -> declaration.hasParameter(childName))
                .flatMap(declaration -> buildNode(declaration.isSuperStructure(), declaration, childName));
    }

    private Optional<Node> parseChild(String childName) {
        Declaration current = declarations.current();
        return !current.hasChild(childName) || declarations.isRoot(current) || current.hasParameter(childName) ?
                parseCurrentChild(childName) :
                buildNode(current.isSuperStructure(), current, childName);
    }

    private Optional<Node> parseCurrentChild(String childName) {
        Optional<Declaration> child = declarations.relative(childName);
        Declaration relative = child.orElseThrow(() -> new ParseException(childName + " is not defined."));
        return buildNode(relative.isParameter(), declarations.parent(childName).orElseThrow(), childName);
    }

    private Optional<Node> buildNode(boolean isField, Declaration parent, String child) {
        return isField ?
                buildField(parent, child) :
                buildVariable(child);
    }

    private Optional<Node> buildVariable(String trim) {
        String name = declarations.relative(trim)
                .filter(Declaration::isFunctional)
                .map(Declaration::joinStack)
                .orElse(trim);
        Node node = new VariableNode(name);
        return Optional.of(node);
    }
}
