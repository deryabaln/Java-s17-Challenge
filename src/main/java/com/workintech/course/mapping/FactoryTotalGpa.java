package com.workintech.course.mapping;

import com.workintech.course.entity.*;

public class FactoryTotalGpa {
    public static int calculationTotalGpa(Course course,
                                          CourseGpa lowCourseGpa,
                                          CourseGpa mediumCourseGpa,
                                          CourseGpa highCourseGpa){
        int totalGpa = 0;
        if (course.getCredit() <=2){
            totalGpa= course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        }
        if (course.getCredit() == 3 ){
            totalGpa= course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        }
        if (course.getCredit() == 4 ){
            totalGpa=  course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }
        return totalGpa;
    }
}
