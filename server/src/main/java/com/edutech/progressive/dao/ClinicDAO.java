package com.edutech.progressive.dao;
 
import com.edutech.progressive.entity.Clinic;
 
import java.sql.SQLException;
import java.util.List;
 
public interface ClinicDAO {
    int addClinic(Clinic clinic) throws SQLException;
    Clinic getClinicById(int clinicId) throws SQLException;
    void updateClinic(Clinic clinic) throws SQLException;
    void deleteClinic(int clinicId) throws SQLException;
    List<Clinic> getAllClinics() throws SQLException;
}