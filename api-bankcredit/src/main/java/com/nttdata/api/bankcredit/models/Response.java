package com.nttdata.api.bankcredit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Response {

    @Nullable
    private Object obj;
    @Nullable
    private String message;
    private Date timestamp;
}
