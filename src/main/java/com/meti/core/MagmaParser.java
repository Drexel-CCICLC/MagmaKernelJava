package com.meti.core;

import com.meti.Parser;

public class MagmaParser extends ParentParser {
	public static final Parser INSTANCE = new MagmaParser(

	);

	private MagmaParser(Parser... parsers) {
		super(parsers);
	}
}
