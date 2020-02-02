package com.meti.core;

import com.meti.Resolver;

public class MagmaResolver extends ParentResolver {
	public static final Resolver INSTANCE = new MagmaResolver(

	);

	private MagmaResolver(Resolver... children) {
		super(children);
	}
}
