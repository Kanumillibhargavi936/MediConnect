package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
import org.springframework.stereotype.Service;
 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
 
@Service("patientServiceImplArrayList")
public class PatientServiceImplArraylist implements PatientService {
 
    private static final List<Patient> patientList = new ArrayList<>();
 
    static {
        if (patientList.isEmpty()) {
            patientList.add(new Patient(101, "John", (Date) null, "9000000001", "john@example.com", "Chennai"));
            patientList.add(new Patient(102, "Mike", (Date) null, "9000000002", "mike@example.com", "Hyderabad"));
            patientList.add(new Patient(103, "Zara", (Date) null, "9000000003", "zara@example.com", "Bengaluru"));
        }
    }
 
    public void emptyArrayList() {
        patientList.clear();
    }
 
    @Override
    public List<Patient> getAllPatients() throws Exception {
        return new ArrayList<>(patientList);
    }
 
    @Override
    public Integer addPatient(Patient patient) throws Exception {
        patientList.add(patient);
        return patient.getPatientId();
    }
 
    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        List<Patient> copy = new ArrayList<>(patientList);
        copy.sort(
            Comparator.comparing(
                (Patient p) -> {
                    String name = p.getFullName();
                    return (name == null) ? null : name.trim();
                },
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            ).thenComparingInt(Patient::getPatientId)
        );
        return copy;
    }
 
    @Override
    public void updatePatient(Patient patient) throws Exception {
        if (patient == null) return;
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientId() == patient.getPatientId()) {
                Patient p = patientList.get(i);
                p.setFullName(patient.getFullName());
                p.setDateOfBirth(patient.getDateOfBirth());
                p.setContactNumber(patient.getContactNumber());
                p.setEmail(patient.getEmail());
                p.setAddress(patient.getAddress());
                return;
            }
        }
    }
 
    @Override
    public void deletePatient(int patientId) throws Exception {
        patientList.removeIf(p -> p.getPatientId() == patientId);
    }
 
    @Override
    public Patient getPatientById(int patientId) throws Exception {
        for (Patient p : patientList) {
            if (p.getPatientId() == patientId) {
                return p;
            }
        }
        return null;
    }
}
 
 