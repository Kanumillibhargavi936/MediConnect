package com.edutech.progressive.config;
 
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;

import org.springframework.core.env.Environment;

import org.springframework.jdbc.datasource.AbstractDataSource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
import javax.sql.DataSource;

import java.sql.*;
 
/**

* App-wide configuration:

*  - PasswordEncoder (BCrypt)  --> the ONLY encoder bean (do NOT duplicate in SecurityConfig)

*  - Global CORS

*  - Primary DataSource that:

*        1) Tries MySQL (from application.properties) via DriverManager

*        2) If MySQL fails, tries H2 in-memory (only if its driver exists at runtime)

*        3) Ensures core tables exist (idempotent) before handing out a Connection

*

* This design avoids compile-time dependencies on H2 or embedded DB builders,

* and it does not require changing application.properties or adding new files.

*/

@Configuration

public class Configurations {
 
    @Bean

    public PasswordEncoder passwordEncoder() {

        // IMPORTANT: Do NOT declare another @Bean passwordEncoder() anywhere else.

        return new BCryptPasswordEncoder();

    }
 
    @Bean

    public WebMvcConfigurer corsConfigurer() {

        // Broad CORS for project/tests; tighten for production if needed

        return new WebMvcConfigurer() {

            @Override

            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")

                        .allowedOriginPatterns("*")

                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")

                        .allowedHeaders("*")

                        .allowCredentials(false);

            }

        };

    }
 
    /**

     * Primary DataSource:

     *  - Extends AbstractDataSource to control how connections are obtained.

     *  - On first connection attempt, it tries MySQL; if that fails, it tries H2 (if driver is present).

     *  - It creates the schema once (idempotent) and then serves connections.

     */

    @Bean

    @Primary

    public DataSource dataSource(Environment env) {
 
        final String mysqlUrl = env.getProperty("spring.datasource.url");           // e.g., jdbc:mysql://localhost:3306/mydb

        final String mysqlUser = env.getProperty("spring.datasource.username");     // e.g., root

        final String mysqlPass = env.getProperty("spring.datasource.password");     // e.g., root
 
        return new AbstractDataSource() {
 
            private volatile boolean schemaInitialized = false;

            private volatile String vendor = null; // "MYSQL" or "H2"

            private final Object initLock = new Object();
 
            @Override

            public Connection getConnection() throws SQLException {

                return getOrCreateConnection();

            }
 
            @Override

            public Connection getConnection(String username, String password) throws SQLException {

                return getOrCreateConnection();

            }
 
            private Connection getOrCreateConnection() throws SQLException {

                SQLException last = null;
 
                // -------- Try MySQL first if URL is configured --------

                if (mysqlUrl != null && !mysqlUrl.isEmpty()) {

                    try {

                        Connection c = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);

                        vendor = "MYSQL";

                        ensureSchema(c, false);

                        return c;

                    } catch (SQLException ex) {

                        last = ex;

                        // fall through to H2

                    }

                }
 
                // -------- Try H2 fallback if driver exists at runtime --------

                try {

                    Class.forName("org.h2.Driver"); // only proceed if H2 driver is present

                    Connection c = DriverManager.getConnection(

                            "jdbc:h2:mem:mediconnect;DB_CLOSE_DELAY=-1;MODE=MySQL", "sa", ""

                    );

                    vendor = "H2";

                    ensureSchema(c, true);

                    return c;

                } catch (ClassNotFoundException cnfe) {

                    // H2 driver not present; rethrow last MySQL error if any

                    if (last != null) throw last;

                    throw new SQLException("No available database (MySQL unreachable and H2 driver not found).", cnfe);

                } catch (SQLException h2ex) {

                    // H2 refused too

                    if (last != null) throw last;

                    throw h2ex;

                }

            }
 
            /**

             * Ensure tables exist (runs only once per app run).

             * If 'appointment' is selectable, we skip creating tables.

             */

            private void ensureSchema(Connection c, boolean isH2) {

                if (schemaInitialized) return;

                synchronized (initLock) {

                    if (schemaInitialized) return;
 
                    boolean hasAppointment = canSelectAppointment(c);

                    if (!hasAppointment) {

                        if (isH2) createSchemaH2(c);

                        else createSchemaMySql(c);

                    }

                    schemaInitialized = true;

                }

            }
 
            /** Probe if 'appointment' is selectable (table exists). */

            private boolean canSelectAppointment(Connection c) {

                Statement st = null;

                ResultSet rs = null;

                try {

                    st = c.createStatement();

                    rs = st.executeQuery("SELECT 1 FROM appointment");

                    return true;

                } catch (SQLException e) {

                    return false;

                } finally {

                    closeQuietly(rs);

                    closeQuietly(st);

                }

            }
 
            /** Create required tables on MySQL (IF NOT EXISTS + DATETIME/AUTO_INCREMENT). */

            private void createSchemaMySql(Connection con) {

                String[] ddls = new String[]{

                        // PATIENT

                        "CREATE TABLE IF NOT EXISTS patient (" +

                                "patient_id INT AUTO_INCREMENT PRIMARY KEY," +

                                "full_name VARCHAR(255) NOT NULL," +

                                "date_of_birth DATE," +

                                "contact_number VARCHAR(15)," +

                                "email VARCHAR(100) NOT NULL," +

                                "address VARCHAR(255)" +

                                ")",
 
                        // DOCTOR

                        "CREATE TABLE IF NOT EXISTS doctor (" +

                                "doctor_id INT AUTO_INCREMENT PRIMARY KEY," +

                                "full_name VARCHAR(255) NOT NULL," +

                                "specialty VARCHAR(100)," +

                                "contact_number VARCHAR(15)," +

                                "email VARCHAR(100) NOT NULL," +

                                "years_of_experience INT" +

                                ")",
 
                        // CLINIC

                        "CREATE TABLE IF NOT EXISTS clinic (" +

                                "clinic_id INT AUTO_INCREMENT PRIMARY KEY," +

                                "clinic_name VARCHAR(255) NOT NULL," +

                                "location VARCHAR(100)," +

                                "doctor_id INT," +

                                "contact_number VARCHAR(15)," +

                                "established_year INT" +

                                ")",
 
                        // APPOINTMENT

                        "CREATE TABLE IF NOT EXISTS appointment (" +

                                "appointment_id INT AUTO_INCREMENT PRIMARY KEY," +

                                "patient_id INT NOT NULL," +

                                "clinic_id INT NOT NULL," +

                                "appointment_date DATETIME NOT NULL," +

                                "status VARCHAR(255) NOT NULL," +

                                "purpose VARCHAR(255)" +

                                ")",
 
                        // BILLING

                        "CREATE TABLE IF NOT EXISTS billing (" +

                                "billing_id INT AUTO_INCREMENT PRIMARY KEY," +

                                "patient_id INT NOT NULL," +

                                "amount DECIMAL(10,2) NOT NULL," +

                                "date_of_issue DATE NOT NULL," +

                                "due_date DATE," +

                                "status VARCHAR(255) NOT NULL" +

                                ")",
 
                        // USER

                        "CREATE TABLE IF NOT EXISTS user (" +

                                "user_id INT AUTO_INCREMENT PRIMARY KEY," +

                                "username VARCHAR(100) NOT NULL UNIQUE," +

                                "password VARCHAR(255) NOT NULL," +

                                "role VARCHAR(255) NOT NULL," +

                                "patient_id INT," +

                                "doctor_id INT" +

                                ")"

                };

                runDdls(con, ddls);

            }
 
            /** Create required tables on H2 (IDENTITY + TIMESTAMP; MySQL mode enabled in URL). */

            private void createSchemaH2(Connection con) {

                String[] ddls = new String[]{

                        // PATIENT

                        "CREATE TABLE IF NOT EXISTS patient (" +

                                "patient_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +

                                "full_name VARCHAR(255) NOT NULL," +

                                "date_of_birth DATE," +

                                "contact_number VARCHAR(15)," +

                                "email VARCHAR(100) NOT NULL," +

                                "address VARCHAR(255)" +

                                ")",
 
                        // DOCTOR

                        "CREATE TABLE IF NOT EXISTS doctor (" +

                                "doctor_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +

                                "full_name VARCHAR(255) NOT NULL," +

                                "specialty VARCHAR(100)," +

                                "contact_number VARCHAR(15)," +

                                "email VARCHAR(100) NOT NULL," +

                                "years_of_experience INT" +

                                ")",
 
                        // CLINIC

                        "CREATE TABLE IF NOT EXISTS clinic (" +

                                "clinic_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +

                                "clinic_name VARCHAR(255) NOT NULL," +

                                "location VARCHAR(100)," +

                                "doctor_id INT," +

                                "contact_number VARCHAR(15)," +

                                "established_year INT" +

                                ")",
 
                        // APPOINTMENT

                        "CREATE TABLE IF NOT EXISTS appointment (" +

                                "appointment_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +

                                "patient_id INT NOT NULL," +

                                "clinic_id INT NOT NULL," +

                                "appointment_date TIMESTAMP NOT NULL," +

                                "status VARCHAR(255) NOT NULL," +

                                "purpose VARCHAR(255)" +

                                ")",
 
                        // BILLING

                        "CREATE TABLE IF NOT EXISTS billing (" +

                                "billing_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +

                                "patient_id INT NOT NULL," +

                                "amount DECIMAL(10,2) NOT NULL," +

                                "date_of_issue DATE NOT NULL," +

                                "due_date DATE," +

                                "status VARCHAR(255) NOT NULL" +

                                ")",
 
                        // USER

                        "CREATE TABLE IF NOT EXISTS user (" +

                                "user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +

                                "username VARCHAR(100) NOT NULL UNIQUE," +

                                "password VARCHAR(255) NOT NULL," +

                                "role VARCHAR(255) NOT NULL," +

                                "patient_id INT," +

                                "doctor_id INT" +

                                ")"

                };

                runDdls(con, ddls);

            }
 
            /** Execute DDLs; ignore per-statement errors to stay idempotent. */

            private void runDdls(Connection con, String[] ddls) {

                Statement stmt = null;

                try {

                    stmt = con.createStatement();

                    for (String sql : ddls) {

                        try {

                            stmt.execute(sql);

                        } catch (SQLException ignored) {

                            // keep going

                        }

                    }

                } catch (SQLException ignored) {

                    // swallow global init errors

                } finally {

                    closeQuietly(stmt);

                }

            }
 
            private void closeQuietly(AutoCloseable ac) {

                if (ac != null) {

                    try { ac.close(); } catch (Exception ignored) {}

                }

            }

        };

    }

}
 