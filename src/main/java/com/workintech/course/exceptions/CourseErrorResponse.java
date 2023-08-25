package com.workintech.course.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
