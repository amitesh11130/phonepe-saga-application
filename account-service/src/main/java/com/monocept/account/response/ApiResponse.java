package com.monocept.account.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private Meta meta;
    private Object data;
    private Object error;


    @Data
    @Builder
    public static class Meta {

        private int code;
        private boolean status;
        private String description;
    }
}
