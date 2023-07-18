package com.edstem.Course.service;

import com.edstem.Course.contract.Course;
import com.edstem.Course.exception.CourseNotFoundException;
import com.edstem.Course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.modelmapper.ModelMapper;

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

    public List<Course> getAllCourses() {
        List<com.edstem.Course.model.Course> courses = this.courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, Course.class))
                .collect(Collectors.toList());
    }
    public Course getCourseById(Long id) {
        com.edstem.Course.model.Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return modelMapper.map(course, Course.class);
    }
}
