package com.edstem.course.service;

import com.edstem.course.contract.Course;
import com.edstem.course.exception.CourseAlreadyExistsException;
import com.edstem.course.exception.CourseNotFoundException;
import com.edstem.course.exception.EnrollmentCapacityException;
import com.edstem.course.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = Logger.getLogger(CourseService.class.getName());

    @Autowired
    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public List<Course> getAllCourses() {
        List<com.edstem.course.model.Course> courses = this.courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, Course.class))
                .collect(Collectors.toList());
    }

    public Course addCourse(Course course) {
        if (course.getCurrentEnrollment() > course.getCapacity()) {
            throw new EnrollmentCapacityException(
                    course.getCurrentEnrollment(), course.getCapacity());
        }
        boolean courseExists = courseRepository.existsCourseByName(course.getName());
        if (courseExists) {
            throw new CourseAlreadyExistsException(course.getName());
        }
        com.edstem.course.model.Course courseEntity =
                courseRepository.save(
                        modelMapper.map(course, com.edstem.course.model.Course.class));
        return modelMapper.map(courseEntity, Course.class);
    }

    public Course updateCourseById(Long id, Course updatedCourse) {
        Optional<com.edstem.course.model.Course> oldCourseData = courseRepository.findById(id);
        if (updatedCourse.getCurrentEnrollment() > updatedCourse.getCapacity()) {
            throw new EnrollmentCapacityException(
                    updatedCourse.getCurrentEnrollment(), updatedCourse.getCapacity());
        }
        boolean courseExists = courseRepository.existsCourseByName(updatedCourse.getName());
        if (courseExists) {
            throw new CourseAlreadyExistsException(updatedCourse.getName());
        }
        if (oldCourseData.isPresent()) {
            com.edstem.course.model.Course existingCourse = oldCourseData.get();
            com.edstem.course.model.Course updatedExistingCourse =
                    new com.edstem.course.model.Course(
                            existingCourse.getId(),
                            updatedCourse.getName(),
                            updatedCourse.getCapacity(),
                            updatedCourse.getCurrentEnrollment());
            com.edstem.course.model.Course savedCourse =
                    courseRepository.save(updatedExistingCourse);
            return modelMapper.map(savedCourse, Course.class);
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    public Course getCourseById(Long id) {
        com.edstem.course.model.Course course =
                this.courseRepository
                        .findById(id)
                        .orElseThrow(() -> new CourseNotFoundException(id));
        return modelMapper.map(course, Course.class);
    }

    public String deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        Optional<com.edstem.course.model.Course> course = courseRepository.findById(id);
        courseRepository.deleteById(id);
        return course.get().getName();
    }
}
