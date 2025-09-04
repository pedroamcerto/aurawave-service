package com.aurawave.core.configurations;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class de conexão com banco de dados Oracle.
 */

@Configuration
public class OracleConnectionConfig {
    public static Connection getConnection() throws SQLException {
        String url  = System.getenv().getOrDefault("ORACLE_URL", "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl");
        String user = System.getenv().getOrDefault("ORACLE_USER", "rm558982");
        String pass = System.getenv().getOrDefault("ORACLE_PASSWORD", "050505");

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        return DriverManager.getConnection(url, props);
    }
}