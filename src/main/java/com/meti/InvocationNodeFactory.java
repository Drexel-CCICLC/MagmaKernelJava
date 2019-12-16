package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.meti.Bucket.build;

public class InvocationNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public InvocationNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		BucketManager manager = new QueueBucketManager(
				build().exclude('('),
				build().include('(').restrict(1),
				build()
		).pour(value.trim());
		if (!manager.isValid()) {
			return Optional.empty();
		}
		var caller = manager.next();
		var hasOpen = manager.next().equals("(");
		var args = manager.next();
		var hasClosed = args.endsWith(")");
		if (hasOpen && hasClosed) {
			if (caller.isBlank()) {
				throw new IllegalStateException("Caller cannot be blank.");
			}
			var variable = parser.parse(caller);
			if (variable.struct() instanceof FunctionStruct) {
				var struct = ((FunctionStruct) variable.struct()).getReturnType();

				var withoutParenthesis = args.substring(0, args.length() - 1);
				List<String> split = new ArrayList<>();
				var current = new StringBuilder();
				var depth = 0;
				for (char c : withoutParenthesis.toCharArray()) {
					if (c == ',' && depth == 0) {
						split.add(current.toString());
						current = new StringBuilder();
					} else if (c == '(') {
						depth++;
					} else if (c == ')') {
						depth--;
					} else {
						current.append(c);
					}
				}
				split.add(current.toString());
				List<Node> children = new ArrayList<>();
				children.add(variable);
				split.stream()
						.filter(s -> !s.isBlank())
						.map(parser::parse)
						.forEach(children::add);
				return Optional.of(new InvocationNode(struct, variable, children));
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

}
