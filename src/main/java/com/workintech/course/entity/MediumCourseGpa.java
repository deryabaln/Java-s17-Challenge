package com.workintech.course.entity;

import com.workintech.course.entity.CourseGpa;
import org.springframework.stereotype.Component;

@Component
public class MediumCourseGpa implements CourseGpa {
    @Override
    public int getGpa() {
        return 5;
    }
}
