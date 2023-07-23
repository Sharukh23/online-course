package com.edstem.course.service;

import com.edstem.course.contract.Registrations;
import com.edstem.course.exception.CourseNotFoundException;
import com.edstem.course.exception.RegistrationNotFoundException;
import com.edstem.course.exception.SameStudentIdException;
import com.edstem.course.model.Course;
import com.edstem.course.model.Registration;
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

    public List<Registrations> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(registration -> modelMapper.map(registration, Registrations.class))
                .collect(Collectors.toList());
    }

    public Registrations getRegistrationById(Long id) {
        Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() -> new RegistrationNotFoundException(id));
        return modelMapper.map(registration, Registrations.class);
    }

    public Registrations addRegistration(Registrations registration) {
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
        Registration registrationEntity =
                registrationRepository.save(modelMapper.map(registration, Registration.class));
        return modelMapper.map(registrationEntity, Registrations.class);
    }

    public Registrations updateRegistrationById(Long id, Registrations updatedRegistration) {
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
        Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() -> new RegistrationNotFoundException(id));
        Registration updatedRegistrationEntity =
                new Registration(
                        id, updatedRegistration.getCourseId(), updatedRegistration.getStudentId());
        Registration savedRegistrationEntity =
                registrationRepository.save(updatedRegistrationEntity);
        return modelMapper.map(savedRegistrationEntity, Registrations.class);
    }

    public Long deleteRegistrationById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new RegistrationNotFoundException(id);
        }
        Optional<Registration> registration = registrationRepository.findById(id);
        registrationRepository.deleteById(id);
        return registration.get().getStudentId();
    }
}
