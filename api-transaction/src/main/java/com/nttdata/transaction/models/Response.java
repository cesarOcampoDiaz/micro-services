package com.nttdata.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Nullable
    private Object obj;
    @Nullable
    private String message;
    private String timestamp;
    @Nullable
    private List<String> errors;
    @Nullable
    private int status;

}
