package com.meti;

import com.meti.exception.ImmutableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssignTest extends CompileTest {
	@Test
	void compile() {
		try{
			compiler.compile("val x = 10; x = 20;");
			fail();
		} catch (Exception e){
			assertSame(ImmutableException.class, e.getCause().getClass());
		}
	}
}
