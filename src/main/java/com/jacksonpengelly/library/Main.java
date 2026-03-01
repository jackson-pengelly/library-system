package com.jacksonpengelly.library;

import com.jacksonpengelly.util.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        if (DatabaseConnection.testConnection()) {
			System.out.println("Database connection successful!");
		} else {
			System.out.println("Database connection failed. Please check your configuration.");
		}
    }
}