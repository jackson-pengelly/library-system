package com.jacksonpengelly.library;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver NOT found!");
        }

        System.out.println("Setup complete!");
    }
}