package com.edutech.progressive.dao;
 
import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Patient;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class PatientDAOImpl implements PatientDAO {
 
    @Override
    public int addPatient(Patient patient) throws SQLException {
        final String sql = "INSERT INTO patient (full_name, date_of_birth, contact_number, email, address) " +
                           "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setString(1, patient.getFullName());
            if (patient.getDateOfBirth() != null) {
                ps.setDate(2, new java.sql.Date(patient.getDateOfBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, patient.getContactNumber());
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getAddress());
 
            ps.executeUpdate();
 
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            return -1; // No key generated
        }
    }
 
    @Override
    public Patient getPatientById(int patientId) throws SQLException {
        final String sql = "SELECT patient_id, full_name, date_of_birth, contact_number, email, address " +
                           "FROM patient WHERE patient_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        }
    }
 
    @Override
    public void updatePatient(Patient patient) throws SQLException {
        final String sql = "UPDATE patient SET full_name = ?, date_of_birth = ?, contact_number = ?, email = ?, address = ? " +
                           "WHERE patient_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, patient.getFullName());
            if (patient.getDateOfBirth() != null) {
                ps.setDate(2, new java.sql.Date(patient.getDateOfBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
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
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, patientId);
            ps.executeUpdate();
        }
    }
 
    @Override
    public List<Patient> getAllPatients() throws SQLException {
        final String sql = "SELECT patient_id, full_name, date_of_birth, contact_number, email, address FROM patient";
        List<Patient> list = new ArrayList<>();
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list; // Never return null
    }
 
    private Patient mapRow(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setPatientId(rs.getInt("patient_id"));
        p.setFullName(rs.getString("full_name"));
        Date dob = rs.getDate("date_of_birth");
        p.setDateOfBirth(dob != null ? new java.util.Date(dob.getTime()) : null);
        p.setContactNumber(rs.getString("contact_number"));
        p.setEmail(rs.getString("email"));
        p.setAddress(rs.getString("address"));
        return p;
    }
}