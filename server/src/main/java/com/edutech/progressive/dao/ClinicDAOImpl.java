package com.edutech.progressive.dao;
 
import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Clinic;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class ClinicDAOImpl implements ClinicDAO {
 
    @Override
    public int addClinic(Clinic clinic) throws SQLException {
        final String sql = "INSERT INTO clinic (clinic_name, location, doctor_id, contact_number, established_year) " +
                           "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());
            if (clinic.getDoctorId() != 0) {
                ps.setInt(3, clinic.getDoctorId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, clinic.getContactNumber());
            ps.setInt(5, clinic.getEstablishedYear());
 
            ps.executeUpdate();
 
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            return -1;
        }
    }
 
    @Override
    public Clinic getClinicById(int clinicId) throws SQLException {
        final String sql = "SELECT clinic_id, clinic_name, location, doctor_id, contact_number, established_year " +
                           "FROM clinic WHERE clinic_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, clinicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        }
    }
 
    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        final String sql = "UPDATE clinic SET clinic_name = ?, location = ?, doctor_id = ?, contact_number = ?, established_year = ? " +
                           "WHERE clinic_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());
            if (clinic.getDoctorId() != 0) {
                ps.setInt(3, clinic.getDoctorId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, clinic.getContactNumber());
            ps.setInt(5, clinic.getEstablishedYear());
            ps.setInt(6, clinic.getClinicId());
 
            ps.executeUpdate();
        }
    }
 
    @Override
    public void deleteClinic(int clinicId) throws SQLException {
        final String sql = "DELETE FROM clinic WHERE clinic_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, clinicId);
            ps.executeUpdate();
        }
    }
 
    @Override
    public List<Clinic> getAllClinics() throws SQLException {
        final String sql = "SELECT clinic_id, clinic_name, location, doctor_id, contact_number, established_year FROM clinic";
        List<Clinic> list = new ArrayList<>();
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list; // Never return null
    }
 
    private Clinic mapRow(ResultSet rs) throws SQLException {
        Clinic c = new Clinic();
        c.setClinicId(rs.getInt("clinic_id"));
        c.setClinicName(rs.getString("clinic_name"));
        c.setLocation(rs.getString("location"));
        c.setDoctorId(rs.getInt("doctor_id"));
        c.setContactNumber(rs.getString("contact_number"));
        c.setEstablishedYear(rs.getInt("established_year"));
        return c;
    }
}
