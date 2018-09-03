package com.songspk.database;

import com.songspk.global.SysProperties;

import java.sql.SQLException;

public class DatabaseUtil {
    private static DatabaseConnectionPool databaseConnectionPool;

    static {
        try {
            databaseConnectionPool = new DatabaseConnectionPool(
                    SysProperties.getProperty("DB_URL")+SysProperties.getProperty("DB_NAME"), SysProperties.getProperty("DB_USERNAME"),  SysProperties.getProperty("DB_PASSWORD"), 350, 350);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnectionPool getDataseBaseConnectionPool() {
        return databaseConnectionPool;
    }
}