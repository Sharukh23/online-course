package com.edstem.course.service;

import com.edstem.course.contract.RegistrationDto;
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

    public List<RegistrationDto> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(registration -> modelMapper.map(registration,RegistrationDto.class)).collect(Collectors.toList());
    }

    public RegistrationDto getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));
        return modelMapper.map(registration, RegistrationDto.class);
    }

    public RegistrationDto addRegistration(Registration registration) {
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
        return modelMapper.map(addedRegistration, RegistrationDto.class);
    }

    public RegistrationDto updateRegistrationById(Long id, Registration updatedRegistration) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));
        registration.setStudentId(updatedRegistration.getStudentId());
        registration.setCourseId(updatedRegistration.getCourseId());
        Registration updatedRegistrationEntity = registrationRepository.save(registration);
        return modelMapper.map(updatedRegistrationEntity,RegistrationDto.class);
    }


    public void deleteRegistrationById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new RegistrationNotFoundException(id);
        }
        registrationRepository.deleteById(id);
    }
}