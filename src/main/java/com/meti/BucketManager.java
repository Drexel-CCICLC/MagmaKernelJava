package com.meti;

public interface BucketManager {
	boolean isValid();

	String next();

	BucketManager pour(String value);
}
