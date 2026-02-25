package com.edutech.progressive;

import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.exception.DoctorAlreadyExistsException;
import com.edutech.progressive.exception.PatientAlreadyExistsException;
import com.edutech.progressive.exception.PatientNotFoundException;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.impl.DoctorServiceImplJpa;
import com.edutech.progressive.service.impl.PatientServiceImplJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MediConnectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DayNineTest {

   @Autowired
   PatientRepository patientRepository;
   @Autowired
   DoctorRepository doctorRepository;
   @Autowired
   PatientServiceImplJpa patientServiceImplJpa;
   @Autowired
   DoctorServiceImplJpa doctorServiceImplJpa;

   @BeforeEach
   public void setUp() throws SQLException {
       String url = "jdbc:mysql://localhost:3306/mydb";
       String user = "root";
       String password = "root";
       try (Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement()) {
           String deleteQuery = "DELETE FROM doctor";
           statement.executeUpdate(deleteQuery);

           deleteQuery = "DELETE FROM patient";
           statement.executeUpdate(deleteQuery);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       MockitoAnnotations.openMocks(this);
   }

   Patient getPatientObject(Integer id, String patientName) throws ParseException {
       Patient patient = new Patient();
       if (id != null) {
           patient.setPatientId(id.intValue());
       }
       patient.setFullName(patientName);
       patient.setContactNumber("9876543210");
       patient.setEmail("mary@gmail.com");
       patient.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1985-06-15"));
       return patient;
   }

   Doctor getDoctorObject(Integer id, String name, int experience) {
       Doctor doctor = new Doctor();
       if (id != null) {
           doctor.setDoctorId(id.intValue());
       }
       doctor.setFullName(name);
       doctor.setContactNumber("9876543210");
       doctor.setEmail("john@gmail.com");
       doctor.setSpecialty("Orthopedic");
       doctor.setYearsOfExperience(experience);
       return doctor;
   }

   @Test
   void testAddPatientThrowsPatientAlreadyExistsException_Day9() throws ParseException {
       Patient patient = getPatientObject(null, "John Keats");
       patientRepository.save(patient);

       assertThrows(PatientAlreadyExistsException.class, () -> patientServiceImplJpa.addPatient(patient));
   }

   @Test
   void testGetPatientByIdThrowsPatientNotFoundException_Day9() {
       int patientId = 1;

       assertThrows(PatientNotFoundException.class, () -> patientServiceImplJpa.getPatientById(patientId));
   }

   @Test
   void testAddDoctorThrowsDoctorAlreadyExistsException_Day9() {
       Doctor doctor = getDoctorObject(null, "John Keats", 10);
       doctorRepository.save(doctor);

       assertThrows(DoctorAlreadyExistsException.class, () -> doctorServiceImplJpa.addDoctor(doctor));
   }
}
