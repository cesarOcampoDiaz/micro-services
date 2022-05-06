package com.nttdata.apliclient.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
