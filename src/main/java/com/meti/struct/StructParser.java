package com.meti.struct;

import com.meti.Compiler;
import com.meti.*;
import com.meti.block.BlockNode;

import java.util.*;
import java.util.stream.Collectors;

public class StructParser implements Parser {
	private final Declarations declarations;
	private final List<Node> functions;

	public StructParser(Declarations declarations, List<Node> functions) {
		this.declarations = declarations;
		this.functions = functions;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("(")) {
			int paramStart = trim.indexOf('(') + 1;
			int paramEnd = trim.indexOf(')');
			String paramsString = trim.substring(paramStart, paramEnd);
			Map<String, Type> parameters = new HashMap<>(Arrays.stream(paramsString.split(","))
					.map(String::trim)
					.filter(paramString -> !paramString.isBlank())
					.map(paramString -> paramString.split(" "))
					.collect(Collectors.toMap(
							strings -> strings[1],
							strings -> compiler.resolveName(strings[0]))));
			parameters.forEach((name1, type) -> declarations.define(name1, type, true));
			int returnTypeStart = trim.indexOf("=>") + 2;
			int returnTypeEnd = trim.contains(":") ? trim.indexOf(':') : trim.length();
			String returnTypeString = trim.substring(returnTypeStart, returnTypeEnd);
			Type returnType = compiler.resolveName(returnTypeString);
			Node block;
			if (trim.contains(":")) {
				String blockString = trim.substring(trim.indexOf(':') + 1);
				int prevSize = functions.size();
				Node impl = compiler.parse(blockString);
				if (impl.isParent()) {
					block = impl;
				} else {
					block = new BlockNode(Collections.singleton(impl));
				}
				int nowSize = functions.size();
				if (nowSize > prevSize) {
					String name = declarations.current().name();
					Node size = compiler.parse("val " + name + "_=Array<Any*>(" + parameters.size() + ")");
					List<String> list = new ArrayList<>(parameters.keySet());
					for (int i = 0; i < list.size(); i++) {
						String parameter = list.get(i);
						Node assign = compiler.parse(name + "_[" + i + "]=&" + parameter);
						block.children().addFirst(assign);
					}
					block.children().addFirst(size);
				}
			} else {
				throw new UnsupportedOperationException("Abstract methods are not supported yet.");
			}
			List<Declaration> list = declarations.stackStream().collect(Collectors.toList());
			for (int i = 1; i < list.size() - 1; i++) {
				Declaration declaration = list.get(i);
				String name = declaration.name();
				parameters.put(name + "_", new ObjectType(declaration.childMap()));
			}
			functions.add(new StructNode(returnType, declarations.current().name(), parameters, block));
			return Optional.of(new EmptyNode());
		}
		return Optional.empty();
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
