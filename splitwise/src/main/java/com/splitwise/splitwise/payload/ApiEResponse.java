package com.splitwise.splitwise.payload;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiEResponse<T> {
    private Boolean success;
    private String messsage;
    private T data;
    @Builder.Default
    private Instant timeStamp = Instant.now();

    public static <T> ApiEResponse<T> success(T data){
        return ApiEResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ApiEResponse<T> success(String message, T data){
        return ApiEResponse.<T>builder()
                .success(true)
                .messsage(message)
                .data(data)
                .build();
    }

    public static <T> ApiEResponse<T> success(String messsage){
        return ApiEResponse.<T>builder()
                .success(true)
                .messsage(messsage)
                .build();
    }


}
