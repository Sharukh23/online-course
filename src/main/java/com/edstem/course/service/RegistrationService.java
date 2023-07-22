package com.edstem.course.service;

import com.edstem.course.contract.Registration;
import com.edstem.course.exception.CourseNotFoundException;
import com.edstem.course.exception.RegistrationNotFoundException;
import com.edstem.course.exception.SameStudentIdException;
import com.edstem.course.model.Course;
import com.edstem.course.repository.CourseRepository;
import com.edstem.course.repository.RegistrationRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationService {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationService(
            CourseRepository courseRepository,
            RegistrationRepository registrationRepository,
            ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
    }

    public List<Registration> getAllRegistrations() {
        List<com.edstem.course.model.Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(registration -> modelMapper.map(registration, Registration.class))
                .collect(Collectors.toList());
    }

    public Registration getRegistrationById(Long id) {
        com.edstem.course.model.Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() -> new RegistrationNotFoundException(id));
        return modelMapper.map(registration, Registration.class);
    }

    public Registration addRegistration(Registration registration) {
        Long courseId = registration.getCourseId();
        Long studentId = registration.getStudentId();
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }
        boolean isStudentRegisteredForCourse =
                registrationRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (isStudentRegisteredForCourse) {
            Optional<Course> course = courseRepository.findById(courseId);
            String courseName = course.get().getName();
            throw new SameStudentIdException(studentId, courseName);
        }
        com.edstem.course.model.Registration registrationEntity =
                registrationRepository.save(
                        modelMapper.map(registration, com.edstem.course.model.Registration.class));
        return modelMapper.map(registrationEntity, Registration.class);
    }

    public Registration updateRegistrationById(Long id, Registration updatedRegistration) {
        Long courseId = updatedRegistration.getCourseId();
        Long studentId = updatedRegistration.getStudentId();
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }
        boolean isStudentRegisteredForCourse =
                registrationRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (isStudentRegisteredForCourse) {
            Optional<Course> course = courseRepository.findById(courseId);
            String courseName = course.get().getName();
            throw new SameStudentIdException(studentId, courseName);
        }
        com.edstem.course.model.Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() -> new RegistrationNotFoundException(id));
        com.edstem.course.model.Registration updatedRegistrationEntity =
                new com.edstem.course.model.Registration(
                        id, updatedRegistration.getCourseId(), updatedRegistration.getStudentId());
        com.edstem.course.model.Registration savedRegistrationEntity =
                registrationRepository.save(updatedRegistrationEntity);
        return modelMapper.map(savedRegistrationEntity, Registration.class);
    }

    public Long deleteRegistrationById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new RegistrationNotFoundException(id);
        }
        Optional<com.edstem.course.model.Registration> registration =
                registrationRepository.findById(id);
        registrationRepository.deleteById(id);
        return registration.get().getStudentId();
    }
}
