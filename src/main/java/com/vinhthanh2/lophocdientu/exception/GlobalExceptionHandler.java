package com.vinhthanh2.lophocdientu.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- 1. AppException tuỳ chỉnh ---
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", ex.getMessage(),
                        "errorCode", ex.getErrorCode(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    // --- 2. Lỗi validation @Valid ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", "Dữ liệu không hợp lệ",
                        "errors", errors,
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    // --- 3. Thiếu tham số ---
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", "Thiếu tham số: " + ex.getParameterName(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    // --- 4. Sai kiểu dữ liệu ---
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", String.format("Sai kiểu dữ liệu cho tham số '%s': %s", ex.getName(), ex.getValue()),
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    // --- 5. JSON không hợp lệ ---
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", "JSON không hợp lệ hoặc sai định dạng dữ liệu đầu vào.",
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
        String message = "Dữ liệu không hợp lệ!";
        String errorCode = "DATA_INTEGRITY";

        Throwable rootCause = ex.getRootCause();
        if (rootCause != null) {
            String rootMsg = rootCause.getMessage();

            if (rootMsg.contains("username_key")) {
                message = "Tên đăng nhập đã tồn tại!";
                errorCode = "USERNAME_DUPLICATE";
            } else if (rootMsg.contains("email_key")) {
                message = "Email đã tồn tại!";
                errorCode = "EMAIL_DUPLICATE";
            } else if (rootMsg.contains("some_foreign_key")) {
                message = "Bản ghi liên quan không tồn tại!";
                errorCode = "FOREIGN_KEY_VIOLATION";
            } else if (rootMsg.contains("check")) {
                message = "Dữ liệu không hợp lệ!";
                errorCode = "CHECK_CONSTRAINT";
            }
        }

        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", message,
                        "errorCode", errorCode,
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    // --- 7. Lỗi database ---
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDatabaseError(DataAccessException ex) {

        Throwable root = ex.getMostSpecificCause();
        String rawMsg = root != null ? root.getMessage() : ex.getMessage();

        // Lấy dòng đầu tiên của thông báo lỗi
        String clean = rawMsg.split("\n")[0]
                .replace("ERROR:", "")        // bỏ từ ERROR:
                .replace("Lỗi truy cập cơ sở dữ liệu:", "")
                .trim();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "success", false,
                        "message", clean,    // chỉ còn: Tỉnh với id 2 không tồn tại
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    // --- 8. Các lỗi còn lại ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpected(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "success", false,
                        "message", "Lỗi hệ thống: " + ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }
}
