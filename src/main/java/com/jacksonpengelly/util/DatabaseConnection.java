package com.jacksonpengelly.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	private static final Properties properties = new Properties();
	
	// static block to load properties file when class is loaded
	static {
		try {
			InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("applications.properties");
			
			if (input == null) {
				System.out.println("Sorry, unable to find applications.properties");
			} else {
				properties.load(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// method to get database connection
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				properties.getProperty("db.url"),
				properties.getProperty("db.username"),
				properties.getProperty("db.password")
		);
	}
	
	// test connection
	public static boolean testConnection() {
		try (Connection conn = getConnection()) {
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
