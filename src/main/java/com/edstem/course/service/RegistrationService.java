package com.edstem.course.service;

import com.edstem.course.contract.Registrations;
import com.edstem.course.exception.CourseNotFoundException;
import com.edstem.course.exception.RegistrationNotFoundException;
import com.edstem.course.exception.SameStudentIdException;
import com.edstem.course.model.Course;
import com.edstem.course.model.Registration;
import com.edstem.course.repository.CourseRepository;
import com.edstem.course.repository.RegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegistrationService {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationService(CourseRepository courseRepository, RegistrationRepository registrationRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
    }

    public List<Registrations> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(this::convertToRegistrationResponse)
                .collect(Collectors.toList());
    }

    public Registrations getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));
        return convertToRegistrationResponse(registration);
    }

    public Registrations addRegistration(Registration registration) {
        Long courseId = registration.getCourseId();
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }
        Long studentId = registration.getStudentId();
        boolean isStudentRegistered = registrationRepository.existsByStudentId(studentId);
        if (isStudentRegistered) {
            throw new SameStudentIdException(studentId);
        }
        Registration addedRegistration = registrationRepository.save(registration);
        return convertToRegistrationResponse(addedRegistration);
    }

    public Registrations updateRegistrationById(Long id, Registration updatedRegistration) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));
        registration.setStudentId(updatedRegistration.getStudentId());
        registration.setCourseId(updatedRegistration.getCourseId());
        Registration updatedRegistrationEntity = registrationRepository.save(registration);
        return convertToRegistrationResponse(updatedRegistrationEntity);
    }

    public void deleteRegistrationById(Long id) {
        registrationRepository.deleteById(id);
    }

    private Registrations convertToRegistrationResponse(Registration registration) {
        Course course = courseRepository.findById(registration.getCourseId())
                .orElseThrow(() -> new RegistrationNotFoundException(registration.getCourseId()));
        Registrations registrationResponse = modelMapper.map(registration, Registrations.class);
        registrationResponse.setCourseId(course.getId());
        return registrationResponse;
    }
}