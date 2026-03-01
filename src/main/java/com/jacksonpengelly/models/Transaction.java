package com.jacksonpengelly.models;

import java.time.LocalDate;

public class Transaction {
	private int transactionID;
	private int memberID;
	private int bookID;
	private LocalDate issueDate; // when book was issued
	private LocalDate dueDate;
	private LocalDate returnDate;
	private double fineAmount;
	private String status; // "Issued", "Returned", "Overdue"
	private LocalDate creationDate; // when transaction was created

	// empty constructor
	public Transaction() {

	}

	// constructor for creating new transaction
	public Transaction(int memberID, int bookID, boolean isPremiumMember) {
		this.memberID = memberID;
		this.bookID = bookID;
		this.issueDate = LocalDate.now();
		this.dueDate = calculateDueDate(isPremiumMember);
		this.status = "Issued";
		this.fineAmount = 0.0;
		this.returnDate = null;
		this.creationDate = LocalDate.now();
	}

	// constructor for loading from db
	public Transaction(int transactionID, int memberID, int bookID, LocalDate issueDate, LocalDate dueDate,
			LocalDate returnDate, double fineAmount, String status, LocalDate creationDate) {
		this.transactionID = transactionID;
		this.memberID = memberID;
		this.bookID = bookID;
		this.issueDate = issueDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.fineAmount = fineAmount;
		this.status = status;
		this.creationDate = creationDate;
	}

	// method to check if transaction is overdue
	public boolean isOverdue() {
		return status.equals("Issued") && LocalDate.now().isAfter(dueDate);
	}

	// method to calculate duedate
	public LocalDate calculateDueDate(boolean isPremiumMember) {
		if (isPremiumMember) {
			return LocalDate.now().plusWeeks(4);
		} else {
			return LocalDate.now().plusWeeks(2);
		}
	}

	// method to get days overdue
	public long getDaysOverdue() {
		if (isOverdue()) {
			return LocalDate.now().toEpochDay() - dueDate.toEpochDay();
		}
		return 0;
	}

	// return formatted string with transaction info
	@Override
	public String toString() {
		String display = String.format("ID: %d | Book: %d | Member: %d | Issued %s | Due: %s | Status: %s",
				transactionID, bookID, memberID, issueDate, dueDate, status);

		if (isOverdue()) {
			display += String.format(" | Overdue by %d days | Fine: $%.2f", getDaysOverdue(), fineAmount);
		}

		return display;
	}

	// getters
	public int getTransactionID() {
		return transactionID;
	}

	public int getMemberID() {
		return memberID;
	}

	public int getBookID() {
		return bookID;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public double getFineAmount() {
		return fineAmount;
	}

	public String getStatus() {
		return status;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	// setters
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
