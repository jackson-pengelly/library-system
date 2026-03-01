package com.jacksonpengelly.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.jacksonpengelly.models.Transaction;
import com.jacksonpengelly.util.DatabaseConnection;

public class TransactionDAO {
	public boolean issueBook(int memberID, int bookID) {
		String checkBookSql = "SELECT available_copies FROM books WHERE book_id = ?";
		String checkMemberSql = "SELECT premium FROM members WHERE member_id = ?";
		String updateBookSql = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";
		String insertSql = "INSERT INTO transactions (member_id, book_id, issue_date, due_date, fine_amount, status) VALUES (?, ?, ?, ?, ?, ?)";

		Connection conn = null;

		try {
			conn = DatabaseConnection.getConnection();
			conn.setAutoCommit(false);

			try (PreparedStatement stmt = conn.prepareStatement(checkBookSql)) {
				stmt.setInt(1, bookID);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					int availableCopies = rs.getInt("available_copies");

					if (availableCopies <= 0)
						return false;
				} else {
					return false;
				}
			}

			boolean isPremium = false;
			try (PreparedStatement stmt = conn.prepareStatement(checkMemberSql)) {
				stmt.setInt(1, memberID);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					isPremium = rs.getBoolean("premium");
				} else {
					return false;
				}
			}

			try (PreparedStatement stmt = conn.prepareStatement(updateBookSql)) {
				stmt.setInt(1, bookID);
				stmt.executeUpdate();
			}

			try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
				Transaction transaction = new Transaction(memberID, bookID, isPremium);

				stmt.setInt(1, transaction.getMemberID());
				stmt.setInt(2, transaction.getBookID());
				stmt.setDate(3, java.sql.Date.valueOf(transaction.getIssueDate()));
				stmt.setDate(4, java.sql.Date.valueOf(transaction.getDueDate()));
				stmt.setDouble(5, 0.0);
				stmt.setString(6, "Issued");

				stmt.executeUpdate();
			}

			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean returnBook(int transactionID) {
		String selectBookSql = "SELECT book_id, due_date, status FROM transactions WHERE transaction_id = ?";
		String updateTransactionSql = "UPDATE transactions SET return_date = ?, status = ?, fine_amount = ? WHERE transaction_id = ?";
		String updateBookSql = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";

		Connection conn = null;

		try {
			conn = DatabaseConnection.getConnection();
			conn.setAutoCommit(false);

			LocalDate dueDate = null;
			int bookID = 0;
			try (PreparedStatement stmt = conn.prepareStatement(selectBookSql)) {
				stmt.setInt(1, transactionID);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					String status = rs.getString("status");
					dueDate = rs.getDate("due_date").toLocalDate();
					bookID = rs.getInt("book_id");

					if (status.equalsIgnoreCase("Returned"))
						return false;
				} else {
					return false;
				}
			}

			double fineAmount = 0.0;
			if (LocalDate.now().toEpochDay() - dueDate.toEpochDay() > 0) {
				fineAmount = (LocalDate.now().toEpochDay() - dueDate.toEpochDay()) * 0.5;
			}

			try (PreparedStatement stmt = conn.prepareStatement(updateTransactionSql)) {
				stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
				stmt.setString(2, "Returned");
				stmt.setDouble(3, fineAmount);
				stmt.setInt(4, transactionID);

				stmt.executeUpdate();
			}

			try (PreparedStatement stmt = conn.prepareStatement(updateBookSql)) {
				stmt.setInt(1, bookID);

				stmt.executeUpdate();
			}
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
