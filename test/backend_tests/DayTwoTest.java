package com.edutech.progressive;

import com.edutech.progressive.dao.PatientDAOImpl;
import com.edutech.progressive.dao.ClinicDAOImpl;
import com.edutech.progressive.dao.DoctorDAOImpl;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.PatientService;
import com.edutech.progressive.service.ClinicService;
import com.edutech.progressive.service.DoctorService;
import com.edutech.progressive.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DayTwoTest {
  private DoctorService doctorService;
  private DoctorServiceImplArraylist doctorServiceImplArraylist;
  private PatientService patientService;
  private PatientServiceImplArraylist patientServiceImplArraylist;

  @BeforeEach
  public void setUp() {
      String url = "jdbc:mysql://localhost:3306/mydb";
      String user = "root";
      String password = "root";
      try (Connection connection = DriverManager.getConnection(url, user, password);
           Statement statement = connection.createStatement()) {
          List<String> tableNames = List.of("doctor", "patient", "clinic");
          for (String tableName : tableNames) {
              String deleteQuery = "DELETE FROM " + tableName;
              statement.executeUpdate(deleteQuery);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
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

  Clinic getClinicObject(Integer id, int doctor, String name) {
      Clinic clinic = new Clinic();
      if ( id != null) {
          clinic.setClinicId(id);
      }
      clinic.setDoctorId(doctor);
      clinic.setLocation("Austin");
      clinic.setClinicName(name);
      clinic.setContactNumber("9876543210");
      clinic.setEstablishedYear(2020);
      return clinic;
  }
//Day2 Testcases
  @Test
  public void testAddDoctorToArrayList_Day2() throws SQLException {
      doctorServiceImplArraylist = new DoctorServiceImplArraylist();
      doctorServiceImplArraylist.emptyArrayList();
      Doctor doctor = getDoctorObject(1, "Mary", 5);
      int result = doctorServiceImplArraylist.addDoctor(doctor);
      assertNotNull(result);
      assertEquals(result, 1);
  }

  @Test
  public void testGetAllDoctorsSortedByExperienceFromArrayList_Day2() throws SQLException {
      doctorServiceImplArraylist = new DoctorServiceImplArraylist();
      doctorServiceImplArraylist.emptyArrayList();
      Doctor doctor1 = getDoctorObject(1, "John", 5);
      Doctor doctor2 = getDoctorObject(2, "Mary", 3);
      Doctor doctor3 = getDoctorObject(3, "Sophie", 10);

      doctorServiceImplArraylist.addDoctor(doctor1);
      doctorServiceImplArraylist.addDoctor(doctor2);
      doctorServiceImplArraylist.addDoctor(doctor3);

      List<Doctor> result = doctorServiceImplArraylist.getDoctorSortedByExperience();
      assertNotNull(result);
      assertFalse(result.isEmpty());

      assertTrue(result.get(0).getYearsOfExperience() <= result.get(1).getYearsOfExperience());
  }

  @Test
  public void testGetAllDoctors_Day2() throws SQLException {
      doctorServiceImplArraylist = new DoctorServiceImplArraylist();
      doctorServiceImplArraylist.emptyArrayList();
      Doctor doctor1 = getDoctorObject(1, "John", 5);
      Doctor doctor2 = getDoctorObject(2, "Mary", 3);
      Doctor doctor3 = getDoctorObject(3, "Sophie", 10);
      doctorServiceImplArraylist.addDoctor(doctor1);
      doctorServiceImplArraylist.addDoctor(doctor2);
      doctorServiceImplArraylist.addDoctor(doctor3);
      List<Doctor> result = doctorServiceImplArraylist.getAllDoctors();
      assertNotNull(result);
      assertEquals(3, result.size());
  }

  @Test
  public void testGetAllPatientsSortedByName_Day2() throws SQLException, ParseException {
      patientServiceImplArraylist = new PatientServiceImplArraylist();
      patientServiceImplArraylist.emptyArrayList();
      Patient patient1 = getPatientObject(1,  "Mark");
      Patient patient2 = getPatientObject(2, "Smith");
      Patient patient3 = getPatientObject(3, "John");

      patientServiceImplArraylist.addPatient(patient1);
      patientServiceImplArraylist.addPatient(patient2);
      patientServiceImplArraylist.addPatient(patient3);
      List<Patient> result = patientServiceImplArraylist.getAllPatientSortedByName();

      assertNotNull(result);
      assertFalse(result.isEmpty());

      // Check if the list is sorted by patient full name
      assertTrue(result.get(0).getFullName().equals(patient3.getFullName()));
      assertTrue(result.get(1).getFullName().equals(patient1.getFullName()));
  }

  @Test
  public void testAddPatientToArrayList_Day2() throws SQLException, ParseException {
      patientServiceImplArraylist = new PatientServiceImplArraylist();
      patientServiceImplArraylist.emptyArrayList();
      Patient patient1 = getPatientObject(1, "Sazan");
      int result = patientServiceImplArraylist.addPatient(patient1);
      assertNotNull(result);
      assertEquals(1, result);
  }
//Day 3 Testcases
//   @Test
//   public void testGetAllDoctors_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       Doctor newDoctor = getDoctorObject(null, "John", 10);
//       Doctor doctor2 = getDoctorObject(null, "Mary", 3);
//       doctorService.addDoctor(newDoctor);
//       doctorService.addDoctor(doctor2);

//       List<Doctor> doctor = doctorService.getAllDoctors();

//       assertNotNull(doctor);
//       assertEquals(2, doctor.size()); // Adjust as per your test data
//   }


//   @Test
//   public void testAddDoctor_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());

//       Doctor newDoctor = getDoctorObject(null, "John", 10);

//       int id = doctorService.addDoctor(newDoctor);

//       Doctor doctor = doctorService.getDoctorById(id);
//       assertNotNull(doctor);
//       assertEquals(newDoctor.getDoctorId(), doctor.getDoctorId());
//       assertEquals(newDoctor.getFullName(), doctor.getFullName());
//   }

//   @Test
//   public void testUpdateDoctor_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       Doctor newDoctor = getDoctorObject(null, "John", 10);
//       int id = doctorService.addDoctor(newDoctor);

//       Doctor updatedDoctors = getDoctorObject(id, "John Keats", 10);

//       doctorService.updateDoctor(updatedDoctors);

//       Doctor retrievedDoctor = doctorService.getDoctorById(id);

//       assertNotNull(retrievedDoctor);
//       assertEquals(updatedDoctors.getFullName(), retrievedDoctor.getFullName());
//   }

//   @Test
//   public void testDeleteDoctor_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       Doctor newDoctor = getDoctorObject(null, "John", 10);
//       int id = doctorService.addDoctor(newDoctor);
//       Doctor savedDoctor = doctorService.getDoctorById(id);

//       assertNotNull(savedDoctor);

//       doctorService.deleteDoctor(id);

//       Doctor deletedDoctor = doctorService.getDoctorById(id);

//       assertNull(deletedDoctor);
//   }

//   @Test
//   public void testGetAllDoctorsSortedByNameFromArrayList_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       Doctor newDoctor = getDoctorObject(null, "John", 10);
//       Doctor doctor2 = getDoctorObject(2, "Mary", 3);
//       doctorService.addDoctor(newDoctor);
//       doctorService.addDoctor(doctor2);

//       List<Doctor> sortedDoctor = doctorService.getDoctorSortedByExperience();

//       assertNotNull(sortedDoctor);
//       assertEquals(2, sortedDoctor.size());
//       assertTrue(sortedDoctor.get(0).getYearsOfExperience() <= sortedDoctor.get(1).getYearsOfExperience());
//   }

//   @Test
//   public void testAddPatient_Day3() throws Exception {
//       patientService = new PatientServiceImplJdbc(new PatientDAOImpl());

//       Patient patient1 = getPatientObject(1, "Sazan");
//       int generatedAccountId = patientService.addPatient(patient1);

//       Patient retrievedPatient = patientService.getPatientById(generatedAccountId);

//       assertNotNull(retrievedPatient);
//       assertEquals(generatedAccountId, retrievedPatient.getPatientId());
//   }

//   @Test
//   public void testUpdatePatient_Day3() throws Exception {
//       patientService = new PatientServiceImplJdbc(new PatientDAOImpl());

//       Patient patient = getPatientObject(null, "Sazan");

//       int generatedPatientId = patientService.addPatient(patient);
//       patient.setPatientId(generatedPatientId);
//       patient.setFullName("Sazan Hendrix");
//       patientService.updatePatient(patient);

//       Patient updatedPatient = patientService.getPatientById(generatedPatientId);

//       assertNotNull(updatedPatient);
//       assertEquals(generatedPatientId, updatedPatient.getPatientId());
//       assertEquals("Sazan Hendrix", updatedPatient.getFullName());
//   }

//   @Test
//   public void testDeletePatient_Day3() throws Exception {
//       patientService = new PatientServiceImplJdbc(new PatientDAOImpl());

//       Patient patientsToAdd = getPatientObject(null, "Sazan");
//       int generatedPatientId = patientService.addPatient(patientsToAdd);

//       patientService.deletePatient(generatedPatientId);

//       assertNull(patientService.getPatientById(generatedPatientId));
//   }

//   @Test
//   public void testGetAllPatients_Day3() throws Exception {
//       patientService = new PatientServiceImplJdbc(new PatientDAOImpl());

//       Patient patients1 = getPatientObject(null, "Sazan");
//       Patient patients2 = getPatientObject(null, "Stevie");
//       Patient patients3 = getPatientObject(null, "Valencia");
//       patientService.addPatient(patients1);
//       patientService.addPatient(patients2);
//       patientService.addPatient(patients3);

//       List<Patient> allPatient = patientService.getAllPatients();

//       assertNotNull(allPatient);
//       assertTrue(allPatient.size() == 3);
//   }

//   @Test
//   public void testGetAllPatientsSortedByName_Day3() throws Exception {
//       patientService = new PatientServiceImplJdbc(new PatientDAOImpl());

//       List<Patient> unsortedPatient = new ArrayList<>();
//       Patient patients1 = getPatientObject(null, "Sazan");
//       Patient patients2 = getPatientObject(null, "Amari");
//       unsortedPatient.add(patients1);
//       unsortedPatient.add(patients2);
//       for (Patient patient : unsortedPatient) {
//           patientService.addPatient(patient);
//       }

//       List<Patient> sortedPatients = patientService.getAllPatientSortedByName();

//       assertFalse(sortedPatients.isEmpty());
//       assertTrue(sortedPatients.get(0).getFullName().equals(patients2.getFullName()));
//       assertTrue(sortedPatients.get(1).getFullName().equals(patients1.getFullName()));
//   }

//   @Test
//   public void testGetAllClinices_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       ClinicService clinicService = new ClinicServiceImplJdbc(new ClinicDAOImpl());

//       Doctor doctor1 = getDoctorObject(null, "John", 10);
//       int doctorId1 = doctorService.addDoctor(doctor1);

//       Doctor doctor2 = getDoctorObject(null, "Mary", 5);
//       int doctorId2 = doctorService.addDoctor(doctor2);

//       List<Clinic> clinices = new ArrayList<>();
//       clinices.add(getClinicObject(null, doctorId1, "Derma Clinic"));
//       clinices.add(getClinicObject(null, doctorId2, "Skin Art"));

//       for (Clinic clinic : clinices) {
//           clinicService.addClinic(clinic);
//       }

//       List<Clinic> retrievedClinices = clinicService.getAllClinics();
//       assertNotNull(retrievedClinices);
//       assertEquals(retrievedClinices.size(), 2);
//   }

//   @Test
//   public void testGetClinicById_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       ClinicService clinicService = new ClinicServiceImplJdbc(new ClinicDAOImpl());

//       Doctor doctor1 = getDoctorObject(null, "John", 10);
//       int doctorId1 = doctorService.addDoctor(doctor1);

//       Clinic clinic = getClinicObject(null, doctorId1, "Derma Clinic");
//       int clinicId = clinicService.addClinic(clinic);

//       Clinic retrievedClinic = clinicService.getClinicById(clinicId);

//       assertNotNull(retrievedClinic);
//       assertEquals(clinicId, retrievedClinic.getClinicId());
//   }

//   @Test
//   public void testUpdateClinic_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       ClinicService clinicService = new ClinicServiceImplJdbc(new ClinicDAOImpl());

//       Doctor doctor1 = getDoctorObject(null, "John", 10);
//       int doctorId1 = doctorService.addDoctor(doctor1);

//       Clinic clinic = getClinicObject(null, doctorId1, "Derma Clinic");
//       int clinicId = clinicService.addClinic(clinic);

//       clinic.setClinicId(clinicId);
//       clinic.setClinicName("Derma clinic AUS");
//       clinicService.updateClinic(clinic);

//       Clinic updatedClinic = clinicService.getClinicById(clinicId);
//       assertNotNull(updatedClinic);
//       assertEquals("Derma clinic AUS", updatedClinic.getClinicName());
//   }

//   @Test
//   public void testDeleteClinic_Day3() throws Exception {
//       doctorService = new DoctorServiceImplJdbc(new DoctorDAOImpl());
//       patientService = new PatientServiceImplJdbc(new PatientDAOImpl());
//       ClinicService clinicService = new ClinicServiceImplJdbc(new ClinicDAOImpl());

//       Doctor doctor1 = getDoctorObject(null, "John", 10);
//       int doctorId1 = doctorService.addDoctor(doctor1);

//       Clinic clinic = getClinicObject(null, doctorId1, "Derma Clinic");
//       int clinicId = clinicService.addClinic(clinic);

//       assertNotNull(clinicId);

//       assertNotEquals(-1, clinicId);

//       clinicService.deleteClinic(clinicId);

//       assertNull(clinicService.getClinicById(clinicId));
//   }
}
