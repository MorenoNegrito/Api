package com.example.api_desarrolladores.Data.ResponsesGenerales;

import lombok.*;

@Data
@Builder
@NoArgsConstructor

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}