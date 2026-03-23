
package com.edutech.progressive.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Patient;

// @Repository("patientDAOImpl")
public class PatientDAOImpl implements PatientDAO {

    public PatientDAOImpl()
    {
        
    }

    @Override
    public int addPatient(Patient patient) throws SQLException {
        final String sql = "INSERT INTO patient (full_name, date_of_birth, contact_number, email, address) VALUES (?,?,?,?,?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getFullName());

            Date utilDate = patient.getDateOfBirth();
            if (utilDate == null) {
                ps.setNull(2, Types.DATE);
            } else {
                ps.setDate(2, new java.sql.Date(utilDate.getTime()));
            }

            ps.setString(3, patient.getContactNumber());
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getAddress());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    patient.setPatientId(id);
                    return id;
                }
            }

            return -1; // No generated key returned
        }
    }

    @Override
    public Patient getPatientById(int patientId) throws SQLException {
        final String sql = "SELECT patient_id, full_name, date_of_birth, contact_number, email, address FROM patient WHERE patient_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date sqlDate = rs.getDate("date_of_birth");
                    Date utilDate = (sqlDate == null) ? null : new Date(sqlDate.getTime());

                    return new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        utilDate,
                        rs.getString("contact_number"),
                        rs.getString("email"),
                        rs.getString("address")
                    );
                }
            }

            return null;
        }
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        final String sql = "UPDATE patient SET full_name = ?, date_of_birth = ?, contact_number = ?, email = ?, address = ? WHERE patient_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, patient.getFullName());

            Date utilDate = patient.getDateOfBirth();
            if (utilDate == null) {
                ps.setNull(2, Types.DATE);
            } else {
                ps.setDate(2, new java.sql.Date(utilDate.getTime()));
            }

            ps.setString(3, patient.getContactNumber());
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getAddress());
            ps.setInt(6, patient.getPatientId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deletePatient(int patientId) throws SQLException {
        final String sql = "DELETE FROM patient WHERE patient_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        final String sql = "SELECT patient_id, full_name, date_of_birth, contact_number, email, address FROM patient";

        List<Patient> patients = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("date_of_birth");
                Date utilDate = (sqlDate == null) ? null : new Date(sqlDate.getTime());

                Patient p = new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("full_name"),
                    utilDate,
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                patients.add(p);
            }
        }

               return patients;
    }
}
