package miwm.job4me.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiException {
    private String source;
    private String title;
    private String details;
    private LocalDateTime timestamp;

    @Builder
    public ApiException(String source, String title, String details) {
        this.source = source;
        this.title = title;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
