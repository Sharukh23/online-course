package com.edstem.course.service;

import com.edstem.course.contract.CourseDto;
import com.edstem.course.exception.CourseAlreadyExistsException;
import com.edstem.course.exception.CourseNotFoundException;
import com.edstem.course.exception.EnrollmentCapacityException;
import com.edstem.course.model.Course;
import com.edstem.course.repository.CourseRepository;
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

    public List<CourseDto> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }

    public CourseDto addCourses(Course course) {
        Long courseId = course.getId();
        if (course.getCurrentEnrollment() > course.getCapacity()) {
            throw new EnrollmentCapacityException(course.getCurrentEnrollment(), course.getCapacity());
        }
        boolean courseExists = courseRepository.existsCourseByName(course.getName());
        if (courseExists) {
            throw new CourseAlreadyExistsException(course.getName());
        }
        Course addedCourse = courseRepository.save(course);
        CourseDto courseResponse = modelMapper.map(course, CourseDto.class);
        courseResponse.setId(course.getId());
        return courseResponse;
    }

        public CourseDto updateCourseById(Long id, CourseDto updatedCourse) {
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
                Course updatedExistingCourse = new Course(
                        existingCourse.getId(),
                        updatedCourse.getName(),
                        updatedCourse.getCapacity(),
                        updatedCourse.getCurrentEnrollment()
                );
                Course savedCourse = courseRepository.save(updatedExistingCourse);
                return modelMapper.map(savedCourse, CourseDto.class);
        } else {
            throw new CourseNotFoundException(id);
        }
    }



    public CourseDto getCourseById(Long id) {
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return modelMapper.map(course, CourseDto.class);
    }


    public void deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }
}
