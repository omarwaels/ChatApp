package iti.jets.app.server.db;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum DataSourceFactory {
    INSTANCE;

    private DataSource dataSource;

    DataSourceFactory() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(properties.getProperty("jdbc.url"));
        config.setUsername(properties.getProperty("jdbc.username"));
        config.setPassword(properties.getProperty("jdbc.password"));
        config.setIdleTimeout(60000);

        config.setMaximumPoolSize(120);

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getMySQLDataSource() {
        return INSTANCE.dataSource;
    }
}