package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.meti.Bucket.build;

public class StructureNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public StructureNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		//(value string)=>int:{}
		BucketManager manager = new QueueBucketManager(
				build().include('(').restrict(1),
				build().exclude(')'),
				build().include(')').restrict(1),
				build().exclude(':'),
				build().include(':'),
				build()
		).pour(value.trim());
		var hasOpen = manager.next().equals("(");
		if (!hasOpen) return Optional.empty();
		var paramString = manager.next();
		var hasClosed = manager.next().equals(")");
		var returnString = manager.next().trim();
		var hasImpl = manager.next().equals(":");
		var implString = manager.next();
		if (hasOpen || hasClosed) {
			var args = Arrays.stream(paramString.split(","))
					.filter(s -> !s.isBlank())
					.map(string -> string.trim().split(" "))
					.collect(Collectors.toMap(strings -> strings[0], strings -> parser.resolve(strings[1])));
			Struct returnType = null;
			if (returnString.startsWith("=>")) {
				returnType = parser.resolve(returnString.substring(2).trim());
			}
			Struct struct = new FunctionStruct(args.values(), returnType);
			var node = new FunctionNode(struct, args);
			if (hasImpl) {
				var params = args.keySet()
						.stream()
						.map((Function<String, Node>) s -> new DeclareNode(args.get(s), s))
						.collect(Collectors.toList());
				tree.appendAll(params);
				node.setContent(parser.parse(implString, node));
				tree.removeAll(params);
			}
			return Optional.of(node);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

}
