package com.edutech.progressive.dao;
 
import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Doctor;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class DoctorDAOImpl implements DoctorDAO {
 
    @Override
    public int addDoctor(Doctor doctor) throws SQLException {
        final String sql = "INSERT INTO doctor (full_name, specialty, contact_number, email, years_of_experience) " +
                           "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialty());
            ps.setString(3, doctor.getContactNumber());
            ps.setString(4, doctor.getEmail());
            ps.setInt(5, doctor.getYearsOfExperience());
 
            ps.executeUpdate();
 
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    doctor.setDoctorId(id);
                    return id;
                }
            }
            return -1; // No key generated
        }
    }
 
    @Override
    public Doctor getDoctorById(int doctorId) throws SQLException {
        final String sql = "SELECT doctor_id, full_name, specialty, contact_number, email, years_of_experience " +
                           "FROM doctor WHERE doctor_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        }
    }
 
    @Override
    public void updateDoctor(Doctor doctor) throws SQLException {
        final String sql = "UPDATE doctor SET full_name = ?, specialty = ?, contact_number = ?, email = ?, years_of_experience = ? " +
                           "WHERE doctor_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialty());
            ps.setString(3, doctor.getContactNumber());
            ps.setString(4, doctor.getEmail());
            ps.setInt(5, doctor.getYearsOfExperience());
            ps.setInt(6, doctor.getDoctorId());
 
            ps.executeUpdate();
        }
    }
 
    @Override
    public void deleteDoctor(int doctorId) throws SQLException {
        final String sql = "DELETE FROM doctor WHERE doctor_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, doctorId);
            ps.executeUpdate();
        }
    }
 
    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        final String sql = "SELECT doctor_id, full_name, specialty, contact_number, email, years_of_experience FROM doctor";
        List<Doctor> list = new ArrayList<>();
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list; // never return null
    }
 
    @Override
    public List<Doctor> getDoctorSortedByExperience() throws SQLException {
        final String sql = "SELECT doctor_id, full_name, specialty, contact_number, email, years_of_experience " +
                           "FROM doctor ORDER BY years_of_experience ASC";
        List<Doctor> list = new ArrayList<>();
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list; // never return null
    }
 
    private Doctor mapRow(ResultSet rs) throws SQLException {
        Doctor d = new Doctor();
        d.setDoctorId(rs.getInt("doctor_id"));
        d.setFullName(rs.getString("full_name"));
        d.setSpecialty(rs.getString("specialty"));
        d.setContactNumber(rs.getString("contact_number"));
        d.setEmail(rs.getString("email"));
        d.setYearsOfExperience(rs.getInt("years_of_experience"));
        return d;
    }
}