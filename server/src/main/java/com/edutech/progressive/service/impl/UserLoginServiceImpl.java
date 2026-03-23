package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginServiceImpl(UserRepository userRepository,
                                PatientRepository patientRepository,
                                DoctorRepository doctorRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO) throws Exception {
        String role = userRegistrationDTO.getRole();
        String email = userRegistrationDTO.getEmail();
        String username = userRegistrationDTO.getUsername();

        // Validate role
        if (!role.equalsIgnoreCase("PATIENT") && !role.equalsIgnoreCase("DOCTOR")) {
            throw new Exception("Invalid role. Only 'PATIENT' or 'DOCTOR' allowed.");
        }

        // Check if username already exists
        if (userRepository.findByUsername(username) != null) {
            throw new Exception("Username '" + username + "' already exists.");
        }

        // Check if email already exists
        if (patientRepository.findByEmail(email) != null || doctorRepository.findByEmail(email) != null) {
            throw new Exception("Email '" + email + "' already exists.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRole(role.toUpperCase());

        if (role.equalsIgnoreCase("PATIENT")) {
            Patient patient = new Patient();
            patient.setFullName(userRegistrationDTO.getFullName());
            patient.setContactNumber(userRegistrationDTO.getContactNumber());
            patient.setEmail(email);
            patient.setAddress(userRegistrationDTO.getAddress());
            patient.setDateOfBirth(userRegistrationDTO.getDateOfBirth());

            Patient savedPatient = patientRepository.save(patient);
            user.setPatient(savedPatient);

        } else if (role.equalsIgnoreCase("DOCTOR")) {
            Doctor doctor = new Doctor();
            doctor.setFullName(userRegistrationDTO.getFullName());
            doctor.setSpecialty(userRegistrationDTO.getSpecialty());
            doctor.setYearsOfExperience(userRegistrationDTO.getYearsOfExperience());
            doctor.setEmail(email);
            doctor.setContactNumber(userRegistrationDTO.getContactNumber());

            Doctor savedDoctor = doctorRepository.save(doctor);
            user.setDoctor(savedDoctor);
        }

        userRepository.save(user);
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserDetails(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user;
        if (identifier.matches("\\d+")) {
            user = userRepository.findById(Integer.parseInt(identifier))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + identifier));
        } else {
            user = userRepository.findByUsername(identifier);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + identifier);
            }
        }

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getUserId()),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}