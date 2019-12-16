package com.meti;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.ANY;

final class FunctionNode extends AbstractNode {
	private final Map<String, Struct> parameters;
	private Node content = null;

	protected FunctionNode(Struct struct, Map<String, Struct> parameters) {
		super(struct);
		this.parameters = parameters;
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		if (content == null) {
			throw new IllegalStateException("Function is abstract.");
		}
		var contentString = content.compile(aliaser, tree);
		if (!(content instanceof ParentNode)) {
			contentString = "{" + contentString + "}";
		}
		return "function(" + parameters.keySet()
				.stream()
				.map(aliaser::alias)
				.collect(Collectors.joining(",")) + ")" + contentString;
	}

	@Override
	public Node transform() {
		content.transform();
		var parent = getParent();
		if (parent.isPresent()) {
			var parentNode = parent.get();
			if (parentNode instanceof NamedNode) {
				var name = ((NamedNode) parentNode).name();
				var structName = ((FunctionStruct) struct).getReturnType().name();
				if (name.equals(structName)) {
					ParentNode block;
					if (content instanceof ParentNode) {
						block = (ParentNode) content;
					} else {
						block = new BlockNode(new ArrayList<>());
						block.children().add(content);
					}
					var params = block.children().stream()
							.filter(node -> node instanceof DeclareNode)
							.map(node -> ((NamedNode) node).name())
							.map((Function<String, Node>) s -> new VariableNode(ANY, s))
							.collect(Collectors.toList());
					block.children().add(new ReturnNode(new ArrayNode(ANY, params)));
					content = block;
				}
			}
		}
		return this;
	}

	public void setContent(Node content) {
		this.content = content;
	}

	public Node getContent() {
		return content;
	}
}
