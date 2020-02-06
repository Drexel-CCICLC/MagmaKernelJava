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
import java.util.function.Supplier;

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
		int period = trim.indexOf('.');
		String before = trim.substring(0, period);
		String after = trim.substring(period + 1);
		Type objectType = compiler.resolveValue(before);
		if (objectType instanceof ObjectType) {
			Declaration declaration = ((ObjectType) objectType).declaration();
			return buildField(declaration, after);
		} else {
			return parseSingleton(compiler, before, after, objectType);
		}
	}

	private Optional<Node> parseSimple(String trim) {
		Optional<Node> parent = parseParameter(trim);
		return parent.isPresent() ? parent : parseNormal(trim);
	}

	private Optional<Node> buildField(Declaration parent, String childName) {
		Declaration child = parent.child(childName)
				.orElseThrow(() -> new ParseException(parent.name() + "." + childName + " is " +
						"not defined."));
		return child.isFunctional() ?
				Optional.of(new VariableNode(child.joinStack())) :
				Optional.of(new FieldNode(parent, childName));
	}

	private Optional<Node> parseSingleton(Compiler compiler, String before, String after, Type objectType) {
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

	private Optional<Node> parseParameter(String childName) {
		Optional<Declaration> parentOptional = declarations.parent(childName);
		if (parentOptional.isPresent()) {
			Declaration parent = parentOptional.get();
			if (!declarations.isRoot(parent) && parent.hasParameter(childName)) {
				return parent.isSuperStructure() ?
						buildField(parent, childName) :
						buildVariable(parent, childName);
			}
		}
		return Optional.empty();
	}

	private Optional<Node> parseNormal(String trim) {
		Declaration current = declarations.current();
		if (current.hasChild(trim))
			if (!declarations.isRoot(current)) {
				if (!current.hasParameter(trim)) {
					if (current.isSuperStructure()) {
						return buildField(current, trim);
					} else {
						return buildVariable(current, trim);
					}
				}
			}
		Declaration relative = declarations.relative(trim)
				.orElseThrow((Supplier<ParseException>) () -> {
					throw new ParseException(trim + " is not defined.");
				});
		if (relative.isParameter()) {
			return buildField(declarations.parent(trim).orElseThrow(), trim);
		} else {
			return buildVariable(declarations.parent(), trim);
		}
	}

	private Optional<Node> buildVariable(Declaration parent, String trim) {
   /*     Optional<Declaration> child = current.child(trim);
        if(child.isPresent()) {
            return Optional.of(new FieldNode(current, trim));
        }*/
		Optional<Declaration> sibling = declarations.relative(trim);
		if (sibling.isPresent()) {
			Declaration child = sibling.get();
			boolean functional = child.isFunctional();
			if (functional) {
				return Optional.of(new VariableNode(child.joinStack()));
			}
		}
		return Optional.of(new VariableNode(trim));
	}

}
