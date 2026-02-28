package com.jacksonpengelly.models;

import java.time.LocalDate;

public class Book {
	private int bookID;
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private LocalDate publicationYear;
	private String genre;
	private int totalCopies;
	private int availableCopies;
	private LocalDate dateAdded;

	// empty constructor
	Book() {

	}
	
	// constructor for creating new book
	Book(String isbn, String title, String author, String genre, int totalCopies) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.totalCopies = totalCopies;
	}
	
	// constructor for loading from db
	Book(int bookID, String isbn, String title, String author, String publisher, LocalDate publicationYear, String genre, int totalCopies, int availableCopies, LocalDate dateAdded) {
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
	public String toString() {
		return String.format("ID: %d | ISBN: %s | Title: %s | Author: %s | Genre %s | Available: %d/%d", bookID, isbn, title, author, genre, availableCopies, totalCopies);
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
	
	public LocalDate getPublicationYear() {
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
	public void setBookID(int newBookID) {
		bookID = newBookID;
	}
	
	public void setISBN(String newISBN) {
		isbn = newISBN;
	}
	
	public void setTitle(String newTitle) {
		title = newTitle;
	}
	
	public void setAuthor(String newAuthor) {
		author = newAuthor;
	}
	
	public void setPublisher(String newPublisher) {
		publisher = newPublisher;
	}
	
	public void setPublicationYear(LocalDate newPublicationYear) {
		publicationYear = newPublicationYear;
	}
	
	public void setGenre(String newGenre) {
		genre = newGenre;
	}
	
	public void setTotalCopies(int newTotalCopies) {
		totalCopies = newTotalCopies;
	}
	
	public void setAvailableCopies(int newAvailableCopies) {
		availableCopies = newAvailableCopies;
	}
	
	public void setDateAdded(LocalDate newDateAdded) {
		dateAdded = newDateAdded;
	}
}
