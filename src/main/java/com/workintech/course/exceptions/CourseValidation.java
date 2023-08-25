package com.workintech.course.exceptions;

import com.workintech.course.entity.Course;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CourseValidation {
    public static void isCreditValid(int credit) {
        if (credit <= 0) {
            throw new CourseException("Credit can not be less than 0: " + credit, HttpStatus.BAD_REQUEST);
        }
    }

    public static void isCourseNotExist(List<Course> courses, String name) {
        for (Course course : courses) {
            if (!course.getName().equals(name)) {
                throw new CourseException("Course with given name is not exist: ", HttpStatus.CONFLICT);
            }
        }
    }
    public static void isCourseExist(List<Course> courses, String name) {
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                throw new CourseException("Course with the given name already exists: " + name, HttpStatus.CONFLICT);
            }
        }
    }
    public static void isCourseValid(Course course) {
        if ((course.getName() == null || course.getName().isEmpty()) ||
                (course.getCredit() <= 0 || course.getCredit() > 8) ||
                (course.getGrade().getCoefficient() <=0 || course.getGrade().getCoefficient() > 6 )) {
            throw new CourseException("Course credentials are not valid. Please check again", HttpStatus.BAD_REQUEST);
        }
    }

}
