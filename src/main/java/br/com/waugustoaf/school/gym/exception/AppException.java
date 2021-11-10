package br.com.waugustoaf.school.gym.exception;

import lombok.Data;

import java.util.HashMap;

@Data
public class AppException extends RuntimeException {
    private final String message;
    private final int statusCode;
    private final String errorKey;

    public AppException(String message) {
        super(message);

        this.message = message;
        this.statusCode = 500;
        this.errorKey = "error.generic";
    }

    public AppException(String message, String errorKey) {
        super(message);

        this.message = message;
        this.statusCode = 500;
        this.errorKey = errorKey;
    }

    public AppException(String message, int statusCode) {
        super(message);

        this.message = message;
        this.statusCode = statusCode;
        this.errorKey = "error.generic";
    }

    public AppException(String message, int statusCode, String errorKey) {
        super(message);

        this.message = message;
        this.statusCode = statusCode;
        this.errorKey = errorKey;
    }

    public HashMap<String, Object> getFormattedValues() {
        HashMap<String, Object> hashed = new HashMap<String, Object>();

        hashed.put("message", this.getMessage());
        hashed.put("statusCode", this.getStatusCode());
        hashed.put("errorKey", this.getErrorKey());

        return hashed;
    }
}
