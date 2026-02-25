package com.edutech.progressive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.repository.*;
import com.edutech.progressive.service.impl.BillingServiceImpl;
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

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MediConnectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DayElevenTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @Autowired
   private BillingServiceImpl billingService;

   @Autowired
   private BillingRepository billingRepository;
   @Autowired
   private PatientRepository patientRepository;
   @Autowired
   private AppointmentRepository appointmentRepository;
   @Autowired
   ClinicRepository clinicRepository;
   @Autowired
   DoctorRepository doctorRepository;

   private Patient patient;

   @BeforeEach
   void setUp() {
       // Clean database before each test
       billingRepository.deleteAll();
       appointmentRepository.deleteAll();
       patientRepository.deleteAll();
       clinicRepository.deleteAll();
       doctorRepository.deleteAll();
       // Add sample patient
       patient = new Patient();
       patient.setFullName("John Doe");
       patient.setEmail("john.doe@example.com");
       patientRepository.save(patient);
   }

   // Helper method to create Billing object
   private Billing createBilling(Patient patient, double amount, Date dateOfIssue, Date dueDate, String status) {
       Billing billing = new Billing();
       billing.setPatient(patient);
       billing.setAmount(amount);
       billing.setDateOfIssue(dateOfIssue);
       billing.setDueDate(dueDate);
       billing.setStatus(status);
       return billing;
   }

   /**
    * Test API: POST /billing
    */
   @Test
   void testCreateBilling_Day11() throws Exception {
       Billing billing = createBilling(patient, 200.50, new Date(), null, "Pending");

       mockMvc.perform(post("/billing")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(billing)))
               .andExpect(status().isCreated());

       List<Billing> billingList = billingRepository.findAll();
       assertEquals(1, billingList.size());
       assertEquals("Pending", billingList.get(0).getStatus());
   }

   /**
    * Test API: GET /billing
    */
   @Test
   void testGetAllBills_Day11() throws Exception {
       billingRepository.save(createBilling(patient, 200.50, new Date(), null, "Pending"));

       mockMvc.perform(get("/billing")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
   }

   /**
    * Test API: GET /billing/patient/{patientId}
    */
   @Test
   void testGetBillsByPatientId_Day11() throws Exception {
       billingRepository.save(createBilling(patient, 200.50, new Date(), null, "Pending"));

       mockMvc.perform(get("/billing/patient/{patientId}", patient.getPatientId())
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
   }

   /**
    * Test Service: Get Bill By ID
    */
   @Test
   void testGetBillByIdService_Day11() throws Exception {
       Billing billing = billingRepository.save(createBilling(patient, 200.50, new Date(), null, "Pending"));

       Billing fetchedBilling = billingService.getBillById(billing.getBillingId());
       assertNotNull(fetchedBilling);
       assertEquals("Pending", fetchedBilling.getStatus());
   }

   /**
    * Test Service: Delete Billing
    */
   @Test
   void testDeleteBillingService_Day11() throws Exception {
       Billing billing = billingRepository.save(createBilling(patient, 200.50, new Date(), null, "Pending"));

       billingService.deleteBill(billing.getBillingId());
       assertFalse(billingRepository.existsById(billing.getBillingId()));
   }

}
