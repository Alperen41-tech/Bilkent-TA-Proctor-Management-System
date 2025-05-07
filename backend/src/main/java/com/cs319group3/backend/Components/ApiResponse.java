package com.cs319group3.backend.Components;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private List<String> errors;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.errors = Collections.emptyList();
    }
}