package com.clockin.app.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    String errorCode;
    String errorReason;
    String errorDetails; // Additional error details, if needed
    LocalDateTime timestamp; // Timestamp for when the error occurred
    String path; // Request path where the error occurred
}
