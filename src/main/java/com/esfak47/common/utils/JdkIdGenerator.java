package com.esfak47.common.utils;

import java.util.UUID;

public class JdkIdGenerator implements IdGenerator {

	@Override
	public UUID generateUUID() {
		return UUID.randomUUID();
	}

}