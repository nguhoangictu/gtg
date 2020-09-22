package com.htngu.gtg.data.response;

import lombok.*;


@Data
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
public class CommonResponse<T> {
    private Boolean status;
    private String message;
    private T result;
}
