package com.edutech.progressive.service;
import com.edutech.progressive.entity.Clinic;
import java.util.List;
public interface ClinicService {
    public List<Clinic> getAllClinics();
    public Clinic getClinicById(int clinicId);
    public Integer addClinic(Clinic clinic);
    public void updateClinic(Clinic clinic);
    public void deleteClinic(int clinicId);
    //Do not implement these methods in ClinicServiceImplJdbc.java class
    default public List<Clinic> getAllClinicByLocation(String location) { return null; }
    default public List<Clinic> getAllClinicByDoctorId(int doctorId) { return null; }
}