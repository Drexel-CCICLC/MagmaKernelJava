package com.meti.unit.quantity.value;

import com.meti.declare.Declarations;
import com.meti.unit.ParentUnit;
import com.meti.unit.Unit;
import com.meti.unit.quantity.transform.TransformUnit;

import java.util.Collection;
import java.util.List;

public class QuantityUnit extends ParentUnit {
    public QuantityUnit(Declarations declarations) {
		this(List.of(new ValueUnit(), new TransformUnit(declarations)));
    }

    public QuantityUnit(Collection<Unit> children) {
        super(children);
    }
}
