package com.meti.declare;

import com.meti.node.Type;
import com.meti.node.other.AnyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface TreeDeclarationBuilder {
	static TreeDeclarationBuilder create() {
		return new TreeDeclarationBuilderImpl();
	}

	Declaration build();

	TreeDeclarationBuilder withChildren(List<Declaration> children);

	TreeDeclarationBuilder withDeclarations(Declarations declarations);

	TreeDeclarationBuilder withParameter(boolean parameter);

	TreeDeclarationBuilder withStack(List<String> stack);

	TreeDeclarationBuilder withType(Type type);

	final class TreeDeclarationBuilderImpl implements TreeDeclarationBuilder {
		private final List<Declaration> children;
		private final Declarations declarations;
		private final boolean parameter;
		private final List<String> stack;
		private final Type type;

		private TreeDeclarationBuilderImpl() {
			this(null, false, Collections.emptyList(), AnyType.INSTANCE, new ArrayList<Declaration>());
		}

		private TreeDeclarationBuilderImpl(Declarations declarations, boolean parameter, List<String> stack,
		                                   Type type, List<Declaration> children) {
			this.declarations = declarations;
			this.parameter = parameter;
			this.stack = stack;
			this.type = type;
			this.children = children;
		}

		@Override
		public TreeDeclarationBuilder withChildren(List<Declaration> children) {
			return new TreeDeclarationBuilderImpl(declarations, parameter, stack, type, children);
		}

		@Override
		public Declaration build() {
			return new TreeDeclaration(type, parameter, declarations, stack, children);
		}

		@Override
		public TreeDeclarationBuilder withDeclarations(Declarations declarations) {
			return new TreeDeclarationBuilderImpl(declarations, parameter, stack, type, children);
		}

		@Override
		public TreeDeclarationBuilder withParameter(boolean parameter) {
			return new TreeDeclarationBuilderImpl(declarations, parameter, stack, type, children);
		}

		@Override
		public TreeDeclarationBuilder withStack(List<String> stack) {
			return new TreeDeclarationBuilderImpl(declarations, parameter, stack, type, children);
		}

		@Override
		public TreeDeclarationBuilder withType(Type type) {
			return new TreeDeclarationBuilderImpl(declarations, parameter, stack, type, children);
		}
	}
}
