package com.nttdata.apliclient.models;

import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

	@Nullable
	private Object obj;
	@Nullable
	private String message;
	@Nullable
	private String errors;
	private String timestamp;
	@Nullable
	private String status;
}
