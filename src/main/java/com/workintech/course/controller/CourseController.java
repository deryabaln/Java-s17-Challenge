package com.workintech.course.controller;

import com.workintech.course.entity.*;
import com.workintech.course.exceptions.CourseValidation;
import com.workintech.course.mapping.CourseResponse;
import com.workintech.course.mapping.FactoryTotalGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {
    private List<Course> courses;
    private CourseGpa lowCourseGpa;
    private CourseGpa mediumCourseGpa;
    private CourseGpa highCourseGpa;

    @PostConstruct
    public void init() {
        Course course = new Course("derya", 3, new Grade(3, "BA"));
        courses = new LinkedList<>();
        courses.add(course);
    }

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") LowCourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") MediumCourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @GetMapping("/")
    public List<Course> getAll() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course get(@PathVariable String name) {
        Optional<Course> optionalCourse = courses.stream()
                .filter(course -> course.getName().equals(name))
                .findFirst();
        if (optionalCourse.isPresent()) {
            CourseValidation.isCreditValid(optionalCourse.get().getCredit());
            return optionalCourse.get();
        } else {
            CourseValidation.isCourseNotExist(courses, name);
            return null;
        }
    }

    @PostMapping("/")
    public CourseResponse save(@RequestBody Course course) {
        CourseValidation.isCreditValid(course.getCredit());
        CourseValidation.isCourseExist(courses, course.getName());
        CourseValidation.isCourseValid(course);
        courses.add(course);
        CourseResponse courseResponse = new CourseResponse(course, FactoryTotalGpa.calculationTotalGpa(course,
                lowCourseGpa, mediumCourseGpa, highCourseGpa));
        return courseResponse;
    }

    @PutMapping("/{name}")
    public CourseResponse update(@PathVariable String name, @RequestBody Course updatedCourse) {
        CourseValidation.isCreditValid(updatedCourse.getCredit());
        CourseValidation.isCourseExist(courses, updatedCourse.getName());
        CourseValidation.isCourseValid(updatedCourse);
        Optional<Course> courseOptional = courses.stream()
                .filter(course -> course.getName().equals(name))
                .findFirst();
        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            int index = courses.indexOf(existingCourse);
            courses.set(index, updatedCourse);
        } else {
            CourseValidation.isCourseNotExist(courses, updatedCourse.getName());
        }
        CourseResponse courseResponse = new CourseResponse(updatedCourse, FactoryTotalGpa.calculationTotalGpa(updatedCourse,
                lowCourseGpa, mediumCourseGpa, highCourseGpa));
        return courseResponse;
    }

    @DeleteMapping("/{name}")
    public Course delete(@PathVariable String name) {
        Optional<Course> optionalCourse = courses.stream()
                .filter(course -> course.getName().equals(name))
                .findFirst();

        if (optionalCourse.isPresent()) {
            Course courseToDelete = optionalCourse.get();
            courses.remove(courseToDelete);
            return courseToDelete;
        } else {
            CourseValidation.isCourseNotExist(courses, name);
            return null;
        }
    }
}
