package com.edutech.progressive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MediConnectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DayTenTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   AppointmentServiceImpl appointmentService;

   @Autowired
   AppointmentRepository appointmentRepository;

   @Autowired
   PatientRepository patientRepository;

   @Autowired
   ClinicRepository clinicRepository;

   @Autowired
   DoctorRepository doctorRepository;

   @Autowired
   private ObjectMapper objectMapper;

   @BeforeEach
   public void setUp() throws SQLException {
       objectMapper = new ObjectMapper();
       appointmentRepository.deleteAll();
       clinicRepository.deleteAll();
       doctorRepository.deleteAll();
       patientRepository.deleteAll();
   }

   // Helper classes to create Objects
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

   Clinic getClinicObject(Integer id, Doctor doctor, String name) {
       Clinic clinic = new Clinic();
       if (id != null) {
           clinic.setClinicId(id);
       }
       setDynamicProperty(clinic, "doctor", doctor);
       clinic.setLocation("Austin");
       clinic.setClinicName(name);
       clinic.setContactNumber("9876543210");
       clinic.setEstablishedYear(2020);
       return clinic;
   }

   Appointment getAppointmentObject(Integer id, Patient patient, Clinic clinic) throws ParseException {
       Appointment appointment = new Appointment();
       appointment.setAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-27"));
       appointment.setStatus("Scheduled");
       appointment.setPurpose("Routine Checkup");
       setDynamicProperty(appointment, "patient", patient);
       setDynamicProperty(appointment, "clinic", clinic);
       return appointment;
   }

   public void setDynamicProperty(Object entity, String propertyName, Object value) {
       try {
           Field field = entity.getClass().getDeclaredField(propertyName);
           field.setAccessible(true);
           field.set(entity, value);
       } catch (Exception e) {
           // Handle exception
       }
   }

   @Test
   void testServiceGetAllAppointments_Day10() throws ParseException {
       Patient patient1 = getPatientObject(null, "Test patient");
       patient1.setPatientId(patientRepository.save(patient1).getPatientId());

       Patient patient2 = getPatientObject(null, "Mary");
       patient2.setPatientId(patientRepository.save(patient1).getPatientId());

       Doctor doctor1 = getDoctorObject(null, "John", 10);
       doctor1.setDoctorId(doctorRepository.save(doctor1).getDoctorId());

       Clinic clinic = getClinicObject(null, doctor1, "Derma");
       clinic.setClinicId(clinicRepository.save(clinic).getClinicId());

       Appointment appointment1 = getAppointmentObject(null, patient1, clinic);
       Appointment appointment2 = getAppointmentObject(null, patient2, clinic);

       appointmentRepository.save(appointment1);
       appointmentRepository.save(appointment2);

       List<Appointment> appointments = appointmentService.getAllAppointments();

       assertEquals(2, appointments.size());
   }

   @Test
   void testServiceGetAppointmentById_Day10() throws ParseException {
       Patient patient1 = getPatientObject(null, "Test patient");
       patient1.setPatientId(patientRepository.save(patient1).getPatientId());

       Doctor doctor1 = getDoctorObject(null, "John", 10);
       doctor1.setDoctorId(doctorRepository.save(doctor1).getDoctorId());

       Clinic clinic = getClinicObject(null, doctor1, "Derma");
       clinic.setClinicId(clinicRepository.save(clinic).getClinicId());

       Appointment appointment1 = getAppointmentObject(null, patient1, clinic);
       int id = appointmentRepository.save(appointment1).getAppointmentId();
       Appointment fetchedAppointment = appointmentService.getAppointmentById(id);

       assertNotNull(fetchedAppointment);
       assertEquals(appointment1.getAppointmentId(), fetchedAppointment.getAppointmentId());
   }


   @Test
   void testControllerCreateAppointment_Day10() throws Exception {
       Patient patient1 = getPatientObject(null, "Test patient");
       patient1.setPatientId(patientRepository.save(patient1).getPatientId());

       Doctor doctor1 = getDoctorObject(null, "John", 10);
       doctor1.setDoctorId(doctorRepository.save(doctor1).getDoctorId());

       Clinic clinic = getClinicObject(null, doctor1, "Derma");
       clinic.setClinicId(clinicRepository.save(clinic).getClinicId());

       Appointment appointment = getAppointmentObject(null, patient1, clinic);

       mockMvc.perform(post("/appointment")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(appointment)))
               .andExpect(status().isCreated());
   }

   @Test
   void testUpdateAppointment_Day10() throws Exception {
       Patient patient1 = getPatientObject(null, "Test patient");
       patient1.setPatientId(patientRepository.save(patient1).getPatientId());

       Doctor doctor1 = getDoctorObject(null, "John", 10);
       doctor1.setDoctorId(doctorRepository.save(doctor1).getDoctorId());

       Clinic clinic = getClinicObject(null, doctor1, "Derma");
       clinic.setClinicId(clinicRepository.save(clinic).getClinicId());
       Appointment appointment = getAppointmentObject(null, patient1, clinic);
       appointment.setAppointmentId(appointmentRepository.save(appointment).getAppointmentId());

       Appointment updatedAppointment = new Appointment();
       updatedAppointment.setAppointmentDate(appointment.getAppointmentDate()); // Keep the existing date
       updatedAppointment.setStatus("Completed");
       updatedAppointment.setPurpose("Updated Purpose");
       updatedAppointment.setPatient(patient1); // Ensure these are non-null
       updatedAppointment.setClinic(clinic);

       mockMvc.perform(put("/appointment/{appointmentId}", appointment.getAppointmentId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(updatedAppointment)))
               .andExpect(status().isOk());

       Appointment fetchedAppointment = appointmentRepository.findById(appointment.getAppointmentId()).orElse(null);
       assertNotNull(fetchedAppointment);
       assertEquals("Completed", fetchedAppointment.getStatus());
       assertEquals("Updated Purpose", fetchedAppointment.getPurpose());
       assertEquals(appointment.getAppointmentDate(), fetchedAppointment.getAppointmentDate());
   }


}
