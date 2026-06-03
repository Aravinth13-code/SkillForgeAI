package com.skillforge.exception;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        log.warn("API exception: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ErrorResponse.of(ex.getMessage(), ex.getStatus().value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(joining(", "));
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(message, 400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unhandled exception", ex);
        // Never expose stack trace to client
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of("An unexpected error occurred", 500));
    }
}