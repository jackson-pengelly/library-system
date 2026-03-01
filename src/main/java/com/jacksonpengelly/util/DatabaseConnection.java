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
			InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties");

			if (input == null) {
				System.out.println("Sorry, unable to find application.properties");
			} else {
				properties.load(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// method to get database connection
	public static Connection getConnection() throws SQLException {
		String password = System.getenv("DB_PASSWORD");

		if (password == null || password.isEmpty()) {
			password = properties.getProperty("db.password");
		}
		return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"),
				password);
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
