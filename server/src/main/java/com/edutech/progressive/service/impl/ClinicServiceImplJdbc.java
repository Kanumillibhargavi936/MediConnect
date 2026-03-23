package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;


import com.edutech.progressive.dao.ClinicDAO;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.ClinicService;

public class ClinicServiceImplJdbc implements ClinicService {

    private final ClinicDAO clinicDAO;

    public ClinicServiceImplJdbc(ClinicDAO clinicDAO) {
        this.clinicDAO = clinicDAO;
    }

    @Override
    public List<Clinic> getAllClinics() throws Exception {
        try {
            return clinicDAO.getAllClinics();
        } catch (SQLException e) {
            throw new Exception("Error fetching all clinics", e);
        }
    }

    @Override
    public Clinic getClinicById(int clinicId) throws Exception {
        try {
            Clinic clinic = clinicDAO.getClinicById(clinicId);
            return clinic;
        } catch (Exception e) {
            throw new Exception("Error fetching clinic with ID " + clinicId, e);
        }
    }

    @Override
    public Integer addClinic(Clinic clinic) throws Exception {
        try {
            return clinicDAO.addClinic(clinic);
        } catch (SQLException e) {
            throw new Exception("Error adding clinic: " + clinic.getClinicName(), e);
        }
    }

    @Override
    public void updateClinic(Clinic clinic) throws Exception {
        try {
            clinicDAO.updateClinic(clinic);
        } catch (SQLException e) {
            throw new Exception("Error updating clinic with ID " + clinic.getClinicId(), e);
        }
    }

    @Override
    public void deleteClinic(int clinicId) throws Exception {
        try {
            clinicDAO.deleteClinic(clinicId);
        } catch (SQLException e) {
            throw new Exception("Error deleting clinic with ID " + clinicId, e);
        }
    }
}