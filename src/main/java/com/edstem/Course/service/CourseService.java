package com.edstem.Course.service;

import com.edstem.Course.contract.Courses;
import com.edstem.Course.exception.CourseAlreadyExistsException;
import com.edstem.Course.exception.CourseNotFoundException;
import com.edstem.Course.exception.EnrollmentCapacityException;
import com.edstem.Course.model.Course;
import com.edstem.Course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public List<Courses> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, Courses.class))
                .collect(Collectors.toList());
    }

    public Courses addCourses(Course course) {
        Long courseId = course.getId();
//        if (course.getName().isEmpty()) {
//            throw new CourseNameNullException();
//        }
        if (course.getCurrentEnrollment() > course.getCapacity()) {
            throw new EnrollmentCapacityException(course.getCurrentEnrollment(), course.getCapacity());
        }
        boolean courseExists = courseRepository.existsCourseByName(course.getName());
        if (courseExists) {
            throw new CourseAlreadyExistsException(course.getName());
        }
        Course addedCourse = courseRepository.save(course);
        Courses courseResponse = modelMapper.map(course, Courses.class);
        courseResponse.setId(course.getId());
        return courseResponse;
    }

    
    public Courses updateCourseById(Long id, Course updatedCourse) {
        Optional<Course> oldCourseData = courseRepository.findById(id);
        if (updatedCourse.getCurrentEnrollment() > updatedCourse.getCapacity()) {
            throw new EnrollmentCapacityException(updatedCourse.getCurrentEnrollment(), updatedCourse.getCapacity());
        }
        boolean courseExists = courseRepository.existsCourseByName(updatedCourse.getName());
        if (courseExists) {
            throw new CourseAlreadyExistsException(updatedCourse.getName());
        }
        if (oldCourseData.isPresent()) {
            Course existingCourse = oldCourseData.get();
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setCapacity(updatedCourse.getCapacity());
            existingCourse.setCurrentEnrollment(updatedCourse.getCurrentEnrollment());
            Course savedCourse = courseRepository.save(updatedCourse);
            return modelMapper.map(savedCourse, Courses.class);
        } else {
            throw new CourseNotFoundException(id);
        }
    }


    public Courses getCourseById(Long id) {
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return modelMapper.map(course, Courses.class);
    }
}
