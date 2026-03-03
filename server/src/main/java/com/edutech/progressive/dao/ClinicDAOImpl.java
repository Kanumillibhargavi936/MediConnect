package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Clinic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;   // IMPORTANT: java.sql.Statement
import java.sql.Types;
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

            Integer did = clinic.getDoctorId();
            if (did != null && did > 0) {
                ps.setInt(3, did);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, clinic.getContactNumber());

            if (clinic.getEstablishedYear() != null) {
                ps.setInt(5, clinic.getEstablishedYear());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // return new clinic_id
                    }
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
                    return mapRowToClinic(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        final String sql = "UPDATE clinic " +
                           "SET clinic_name = ?, location = ?, doctor_id = ?, contact_number = ?, established_year = ? " +
                           "WHERE clinic_id = ?";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());

            Integer did = clinic.getDoctorId();
            if (did != null && did > 0) {
                ps.setInt(3, did);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, clinic.getContactNumber());

            if (clinic.getEstablishedYear() != null) {
                ps.setInt(5, clinic.getEstablishedYear());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

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
                list.add(mapRowToClinic(rs));
            }
        }
        return list;
    }

    // ---------- helper ----------
    private Clinic mapRowToClinic(ResultSet rs) throws SQLException {
        Clinic c = new Clinic();
        c.setClinicId(rs.getInt("clinic_id"));
        c.setClinicName(rs.getString("clinic_name"));
        c.setLocation(rs.getString("location"));

        Object didObj = rs.getObject("doctor_id");
        if (didObj != null) {
            c.setDoctorId(((Number) didObj).intValue());
        } else {
            c.setDoctorId(null);
        }

        c.setContactNumber(rs.getString("contact_number"));

        Object eyObj = rs.getObject("established_year");
        if (eyObj != null) {
            c.setEstablishedYear(((Number) eyObj).intValue());
        } else {
            c.setEstablishedYear(null);
        }

        return c;
    }
}