package com.meti.unit.external;

import com.meti.unit.ParentUnit;
import com.meti.unit.PointerUnit;
import com.meti.unit.VarArgsUnit;

import java.util.List;

public class ExternalUnit extends ParentUnit {
	public ExternalUnit() {
		super(List.of(new PointerUnit(), new VarArgsUnit()));
	}
}
