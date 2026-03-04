package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.repository.UserRepository;
import com.edutech.progressive.service.BillingService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
 
import java.util.Collections;
 
@Primary
@Service
public class UserLoginServiceImpl implements UserDetailsService {
 
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
 
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 
    @Autowired
    public UserLoginServiceImpl(UserRepository userRepository,
                                PatientRepository patientRepository,
                                DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }
 
    public void registerUser(UserRegistrationDTO dto) throws Exception {
        if (dto == null || dto.getUsername() == null || dto.getPassword() == null || dto.getRole() == null) {
            throw new Exception("Username, password, and role are required");
        }
 
        User existing = userRepository.findByUsername(dto.getUsername());
        if (existing != null) {
            throw new Exception("Username already exists");
        }
 
        String role = dto.getRole().trim().toUpperCase();
        if (!role.equals("PATIENT") && !role.equals("DOCTOR")) {
            throw new Exception("Invalid role. Allowed: PATIENT, DOCTOR");
        }
 
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);
 
        if (role.equals("PATIENT")) {
            Patient p = new Patient();
            p.setFullName(dto.getFullName());
            p.setDateOfBirth(dto.getDateOfBirth());
            p.setContactNumber(dto.getContactNumber());
            p.setEmail(dto.getEmail());
            p.setAddress(dto.getAddress());
            Patient savedP = patientRepository.save(p);
            user.setPatient(savedP);
        } else if (role.equals("DOCTOR")) {
            Doctor d = new Doctor();
            d.setFullName(dto.getFullName());
            d.setSpecialty(dto.getSpecialty());
            d.setContactNumber(dto.getContactNumber());
            d.setEmail(dto.getEmail());
            d.setYearsOfExperience(dto.getYearsOfExperience());
            Doctor savedD = doctorRepository.save(d);
            user.setDoctor(savedD);
        }
 
        userRepository.save(user);
    }
 
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
 
    public User getUserDetails(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(identifier);
        if (user == null) throw new UsernameNotFoundException("User not found: " + identifier);
 
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
 