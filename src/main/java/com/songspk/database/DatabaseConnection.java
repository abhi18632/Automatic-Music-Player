package com.songspk.database;

import com.songspk.global.SysProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by rishabh
 */
public class DatabaseConnection {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class);

    public static Connection getConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(SysProperties.getInstance().getProperty("DB_DRIVER"));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            dbConnection = DriverManager.getConnection(
                    SysProperties.getInstance().getProperty("DB_URL") + SysProperties.getInstance().getProperty("DB_NAME")
                    , SysProperties.getInstance().getProperty("DB_USERNAME")
                    , SysProperties.getInstance().getProperty("DB_PASSWORD")
            );
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return dbConnection;
    }

    public static void leaveConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
