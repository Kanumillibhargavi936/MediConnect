package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// @Repository("clinicDAOImpl")
public class ClinicDAOImpl implements ClinicDAO {

    public ClinicDAOImpl() {}

    @Override
    public int addClinic(Clinic clinic) throws SQLException {
        String sql = "INSERT INTO clinic (clinic_name, location, doctor_id, contact_number, established_year) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet keys = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());

            Integer doctorId = null;
            if (clinic.getDoctor() != null) {
                doctorId = clinic.getDoctor().getDoctorId();
            }
            if (doctorId != null && doctorId > 0) {
                ps.setInt(3, doctorId);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, clinic.getContactNumber());
            ps.setInt(5, clinic.getEstablishedYear());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Creating clinic failed: no rows affected.");
            }

            keys = ps.getGeneratedKeys();
            if (keys != null && keys.next()) {
                int id = keys.getInt(1);
                clinic.setClinicId(id);
                return id;
            } else {
                throw new SQLException("Creating clinic failed: no ID obtained.");
            }

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while adding clinic", e);
        } finally {
            closeQuietly(keys);
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public Clinic getClinicById(int clinicId) throws SQLException {
        String sql = "SELECT clinic_id, clinic_name, location, doctor_id, contact_number, established_year " +
                     "FROM clinic WHERE clinic_id = ?";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, clinicId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Clinic c = new Clinic();
                c.setClinicId(rs.getInt("clinic_id"));
                c.setClinicName(rs.getString("clinic_name"));
                c.setLocation(rs.getString("location"));
                c.setContactNumber(rs.getString("contact_number"));
                c.setEstablishedYear(rs.getInt("established_year"));

                int doctorId = rs.getInt("doctor_id");
                if (!rs.wasNull()) {
                    Doctor d = new Doctor();
                    d.setDoctorId(doctorId);
                    c.setDoctor(d);
                } else {
                    c.setDoctor(null);
                }

                return c;
            }
            return null;

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while fetching clinic id=" + clinicId, e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        String sql = "UPDATE clinic SET clinic_name = ?, location = ?, doctor_id = ?, contact_number = ?, " +
                     "established_year = ? WHERE clinic_id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());

            Integer doctorId = null;
            if (clinic.getDoctor() != null) {
                doctorId = clinic.getDoctor().getDoctorId();
            }
            if (doctorId != null && doctorId > 0) {
                ps.setInt(3, doctorId);
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, clinic.getContactNumber());
            ps.setInt(5, clinic.getEstablishedYear());
            ps.setInt(6, clinic.getClinicId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while updating clinic id=" + clinic.getClinicId(), e);
        } finally {
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public void deleteClinic(int clinicId) throws SQLException {
        String sql = "DELETE FROM clinic WHERE clinic_id = ?";

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, clinicId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while deleting clinic id=" + clinicId, e);
        } finally {
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    @Override
    public List<Clinic> getAllClinics() throws SQLException {
        String sql = "SELECT clinic_id, clinic_name, location, doctor_id, contact_number, established_year " +
                     "FROM clinic ORDER BY clinic_name ASC, clinic_id ASC";

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Clinic> clinics = new ArrayList<>();

        try {
            connection = DatabaseConnectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Clinic c = new Clinic();
                c.setClinicId(rs.getInt("clinic_id"));
                c.setClinicName(rs.getString("clinic_name"));
                c.setLocation(rs.getString("location"));
                c.setContactNumber(rs.getString("contact_number"));
                c.setEstablishedYear(rs.getInt("established_year"));

                int doctorId = rs.getInt("doctor_id");
                if (!rs.wasNull()) {
                    Doctor d = new Doctor();
                    d.setDoctorId(doctorId);
                    c.setDoctor(d);
                } else {
                    c.setDoctor(null);
                }

                clinics.add(c);
            }

            return clinics;

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Unexpected error while fetching all clinics", e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(connection);
        }
    }

    private void closeQuietly(AutoCloseable ac) {
        if (ac != null) {
            try { ac.close(); } catch (Exception ignored) {}
        }
    }
}
