package com.zup.comics.comicsmanager.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class ApiResponse {
    public static ResponseEntity<Map<String, Object>> create(Page page) {
        Map<String, Object> content = new LinkedHashMap<>();

        content.put("content", page.getContent());
        content.put("pages", page.getTotalPages());
        content.put("total", page.getTotalElements());
        content.put("first", page.isFirst());
        content.put("last", page.isLast());
        content.put("empty", page.isEmpty());
        content.put("sort", page.getSort());

        Pageable pageable = page.getPageable();
        Map<String, Object> pageableMap = new LinkedHashMap<>();

        pageableMap.put("offset", pageable.getOffset());
        pageableMap.put("pageNumber", pageable.getPageNumber());
        pageableMap.put("pageSize", pageable.getPageSize());
        pageableMap.put("paged", pageable.isPaged());
        pageableMap.put("unpaged", pageable.isUnpaged());

        content.put("pageable", pageableMap);

        return ResponseEntity.ok(content);
    }

    public static ResponseEntity<Map<String, Object>> create(Exception ex, String message) {
        Map<String, Object> content = new LinkedHashMap<>();

        content.put("message", message);
        content.put("content", ex.getMessage());

        return ResponseEntity.internalServerError().body(content);
    }

    public static ResponseEntity<Map<String, Object>> create(Errors errors, String message) {
        Map<String, Object> content = new LinkedHashMap<>();
        
        content.put("message", message);

        Set<String> errorSet = errors.getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toSet());

        content.put("errors", errorSet);

        return ResponseEntity.badRequest().body(content);
    }

    public static ResponseEntity<Map<String, Object>> create(Set<ConstraintViolation<Object>> errors, String message) {
        Map<String, Object> content = new LinkedHashMap<>();
        
        content.put("message", message);

        Set<String> errorSet = errors.stream().map(error -> error.getMessage()).collect(Collectors.toSet());

        content.put("errors", errorSet);

        return ResponseEntity.badRequest().body(content);
    }

    public static ResponseEntity<Map<String, Object>> create(String message, HttpStatus status) {
        Map<String, Object> content = new LinkedHashMap<>();

        content.put("message", message);

        return new ResponseEntity<>(content, status);
    }

    public static ResponseEntity<Map<String, Object>> create(Object object, HttpStatus status) {
        Map<String, Object> content = new LinkedHashMap<>();

        content.put("content", object);

        return new ResponseEntity<>(content, status);
    }
}
