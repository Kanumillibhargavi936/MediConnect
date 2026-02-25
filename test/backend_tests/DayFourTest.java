package com.edutech.progressive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edutech.progressive.controller.PatientController;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.impl.PatientServiceImplArraylist;
import com.edutech.progressive.service.impl.PatientServiceImplJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class DayFourTest {
   @Mock
   private PatientServiceImplJpa patientServiceImplJpa;

   @Mock
   private PatientServiceImplArraylist patientServiceImplArraylist;

   @InjectMocks
   private PatientController patientController;

   private ObjectMapper objectMapper;

   private MockMvc mockMvc;

   @BeforeEach
   public void setUp() {
       mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
       objectMapper = new ObjectMapper();
       String url = "jdbc:mysql://localhost:3306/mydb";
       String user = "root";
       String password = "root";
       try (Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement()) {
           List<String> tableNames = List.of("patient", "doctor", "clinic");
           for (String tableName : tableNames) {
               String deleteQuery = "DELETE FROM " + tableName;
               statement.executeUpdate(deleteQuery);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   // Helper classes to create Objects
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

   // Day - 4
   @Test
   public void testMainMethodOutput_Day4() {
       SpringApplication app = new SpringApplication(MediConnectApplication.class);
       app.setDefaultProperties(Collections.singletonMap("server.port", "9999"));
       ConfigurableApplicationContext context = app.run();
       assertNotNull(context);
       context.close();
   }

   // Day - 5 : Test cases for ArrayList Service methods
//    @Test
//    void testAddPatientToArrayList_Day5() throws Exception {
//        Patient patient = getPatientObject(1, "John");
//        given(patientServiceImplArraylist.addPatient(patient)).willReturn(1);

//        mockMvc.perform(post("/patient/toArrayList")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(patient)))
//                .andExpect(status().isCreated());
//    }

//    @Test
//    void testGetAllPatientsSortedByNameFromArrayList_Day5() throws Exception {
//        PatientServiceImplArraylist patientServiceImplArray = new PatientServiceImplArraylist();
//        patientServiceImplArray.emptyArrayList();
//        Patient patient1 = getPatientObject(1, "John");
//        Patient patient2 = getPatientObject(2, "Mary");
//        Patient patient3 = getPatientObject(3, "Smith");
//        patientServiceImplArray.addPatient(patient1);
//        patientServiceImplArray.addPatient(patient2);
//        patientServiceImplArray.addPatient(patient3);

//        List<Patient> result = patientServiceImplArray.getAllPatientSortedByName();

//        given(patientServiceImplArraylist.getAllPatientSortedByName()).willReturn(result);
//        mockMvc.perform(get("/patient/fromArrayList/sorted"))
//                .andExpect(jsonPath("$[0].fullName").value(patient1.getFullName()));
//    }

//    @Test
//    void testGetAllPatient_Day5() throws Exception {
//        PatientServiceImplArraylist patientServiceImplArray = new PatientServiceImplArraylist();
//        patientServiceImplArray.emptyArrayList();
//        Patient patient1 = getPatientObject(1, "John");
//        Patient patient2 = getPatientObject(2, "Mary");
//        Patient patient3 = getPatientObject(3, "Smith");
//        patientServiceImplArray.addPatient(patient1);
//        patientServiceImplArray.addPatient(patient2);
//        patientServiceImplArray.addPatient(patient3);

//        List<Patient> result = patientServiceImplArray.getAllPatients();
//        given(patientServiceImplArraylist.getAllPatients()).willReturn(result);

//        mockMvc.perform(get("/patient/fromArrayList"))
//                .andExpect(jsonPath("$.size()").value(3));
//    }

}



