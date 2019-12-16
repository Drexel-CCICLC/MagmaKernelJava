package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		var caller = manager.next().split("\\.");
		var hasOpen = manager.next().equals("(");
		var args = manager.next();
		var hasClosed = args.endsWith(")");
		if (hasOpen && hasClosed) {
			if(caller.length == 0) {
				throw new IllegalArgumentException("Caller cannot be empty for value: " + value);
			}
			var declaration = tree.locateDeclaration(caller)
					.orElseThrow((Supplier<IllegalStateException>) () -> {
						throw new IllegalStateException(Arrays.toString(caller) + " is not defined.");
					});
			if (declaration instanceof InheritedNode) {
				var decValue = ((InheritedNode) declaration).value();
				var struct = decValue.struct();

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
				children.add(declaration);
				split.stream()
						.filter(s -> !s.isBlank())
						.map(parser::parse)
						.forEach(children::add);
				return Optional.of(new InvocationNode(struct, caller, children));
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private static final class InvocationNode extends AbstractParentNode {
		private final String[] caller;

		public InvocationNode(Struct struct, String[] caller, List<Node> children) {
			super(struct, children);
			this.caller = caller;
		}

		@Override
		public String compile(Aliaser aliaser) {
			var argString = IntStream.range(1, children.size())
					.mapToObj(children::get)
					.map(node -> node.compile(aliaser))
					.collect(Collectors.joining(","));
			var ending = (struct == null) ? ";" : "";
			return aliaser.alias(caller) + "(" + argString + ")" + ending;
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
