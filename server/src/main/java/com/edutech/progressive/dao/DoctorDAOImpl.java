
package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// @Repository("doctorDAOImpl")
public class DoctorDAOImpl implements DoctorDAO {

    public DoctorDAOImpl()
    {
        
    }

    @Override
    public int addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctor (full_name, specialty, contact_number, email, years_of_experience) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet keys = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialty());
            ps.setString(3, doctor.getContactNumber());
            ps.setString(4, doctor.getEmail());
            ps.setInt(5, doctor.getYearsOfExperience());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Creating doctor failed: no rows affected.");
            }

            keys = ps.getGeneratedKeys();
            if (keys != null && keys.next()) {
                int id = keys.getInt(1);
                doctor.setDoctorId(id);
                return id;
            } else {
                throw new SQLException("Creating doctor failed: no ID obtained.");
            }

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while adding doctor", e);
        } finally {
            closeQuietly(keys);
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws SQLException {
        String sql = "SELECT doctor_id, full_name, specialty, contact_number, email, years_of_experience " +
                    "FROM doctor WHERE doctor_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
        }
        return null;
    } 
    @Override
    public void updateDoctor(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctor SET full_name = ?, specialty = ?, contact_number = ?, email = ?, " +
                "years_of_experience = ? WHERE doctor_id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialty());
            ps.setString(3, doctor.getContactNumber());
            ps.setString(4, doctor.getEmail());
            ps.setInt(5, doctor.getYearsOfExperience());
            ps.setInt(6, doctor.getDoctorId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while updating doctor id=" + doctor.getDoctorId(), e);
        } finally {
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public void deleteDoctor(int doctorId) throws SQLException {
        String sql = "DELETE FROM doctor WHERE doctor_id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while deleting doctor id=" + doctorId, e);
        } finally {
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        String sql = "SELECT doctor_id, full_name, specialty, contact_number, email, years_of_experience " +
                "FROM doctor ORDER BY full_name ASC, doctor_id ASC";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setDoctorId(rs.getInt("doctor_id"));
                d.setFullName(rs.getString("full_name"));
                d.setSpecialty(rs.getString("specialty"));
                d.setContactNumber(rs.getString("contact_number"));
                d.setEmail(rs.getString("email"));
                d.setYearsOfExperience(rs.getInt("years_of_experience"));
                doctors.add(d);
            }

            return doctors;

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while fetching all doctors", e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    private void closeQuietly(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ignored) {
            }
        }
    }
}
