package com.jacksonpengelly.models;

import java.time.LocalDate;

public class Book {
	private int bookID;
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private int publicationYear;
	private String genre;
	private int totalCopies;
	private int availableCopies;
	private LocalDate dateAdded;

	// empty constructor
	public Book() {

	}

	// constructor for creating new book
	public Book(String isbn, String title, String author, String genre, int totalCopies) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.totalCopies = totalCopies;
		this.availableCopies = totalCopies;
		this.dateAdded = LocalDate.now();
	}

	// constructor for loading from db
	public Book(int bookID, String isbn, String title, String author, String publisher, int publicationYear,
			String genre, int totalCopies, int availableCopies, LocalDate dateAdded) {
		this.bookID = bookID;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.genre = genre;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
		this.dateAdded = dateAdded;
	}

	// method to check if book is available
	public boolean bookAvailable() {
		return availableCopies > 0;
	}

	// return formatted string with book info
	@Override
	public String toString() {
		return String.format("ID: %d | ISBN: %s | Title: %s | Author: %s | Genre %s | Available: %d/%d", bookID, isbn,
				title, author, genre, availableCopies, totalCopies);
	}

	// getters
	public int getBookID() {
		return bookID;
	}

	public String getISBN() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public String getGenre() {
		return genre;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	// setters
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}
	
	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}
	
	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}
}
