package com.jacksonpengelly.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jacksonpengelly.models.Book;
import com.jacksonpengelly.util.DatabaseConnection;

public class BookDAO {
	public void addBook(Book book) {
		String sql = "INSERT INTO books (isbn, title, author, publisher, publication_year, genre, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, book.getISBN());
			stmt.setString(2, book.getTitle());
			stmt.setString(3, book.getAuthor());
			stmt.setString(4, book.getPublisher());
			stmt.setInt(5, book.getPublicationYear());
			stmt.setString(6, book.getGenre());
			stmt.setInt(7, book.getTotalCopies());
			stmt.setInt(8, book.getAvailableCopies());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		String sql = "SELECT book_id, isbn, title, author, publisher, publication_year, genre, total_copies, available_copies, date_added FROM books";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {
					int bookID = rs.getInt("book_id");
					String isbn = rs.getString("isbn");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String publisher = rs.getString("publisher");
					int publicationYear = rs.getInt("publication_year");
					String genre = rs.getString("genre");
					int totalCopies = rs.getInt("total_copies");
					int availableCopies = rs.getInt("available_copies");
					Date dateAddedSql = rs.getDate("date_added");

					Book b = new Book(bookID, isbn, title, author, publisher, publicationYear, genre, totalCopies,
							availableCopies, dateAddedSql.toLocalDate());
					books.add(b);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return books;
	}

	public Book getBookByISBN(String isbn) {
		String sql = "SELECT book_id, isbn, title, author, publisher, publication_year, genre, total_copies, available_copies, date_added FROM books WHERE isbn = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, isbn);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int bookID = rs.getInt("book_id");
					String isbnDB = rs.getString("isbn");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String publisher = rs.getString("publisher");
					int publicationYear = rs.getInt("publication_year");
					String genre = rs.getString("genre");
					int totalCopies = rs.getInt("total_copies");
					int availableCopies = rs.getInt("available_copies");
					Date dateAddedSql = rs.getDate("date_added");

					return new Book(bookID, isbnDB, title, author, publisher, publicationYear, genre, totalCopies,
							availableCopies, dateAddedSql.toLocalDate());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // book not found
	}

	public void updateBook(Book newBook) {
		String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, publisher = ?, publication_year = ?, genre = ?, total_copies = ?, available_copies = ?, WHERE book_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, newBook.getISBN());
			stmt.setString(2, newBook.getTitle());
			stmt.setString(3, newBook.getAuthor());
			stmt.setString(4, newBook.getPublisher());
			stmt.setInt(5, newBook.getPublicationYear());
			stmt.setString(6, newBook.getGenre());
			stmt.setInt(7, newBook.getTotalCopies());
			stmt.setInt(8, newBook.getAvailableCopies());
			stmt.setInt(9, newBook.getBookID());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBook(int bookID) {
		String sql = "DELETE FROM books WHERE book_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, bookID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Book getBookByID(int bookID) {
		String sql = "SELECT book_id, isbn, title, author, publisher, publication_year, genre, total_copies, available_copies, date_added FROM books WHERE book_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, bookID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int bookIDdb = rs.getInt("book_id");
					String isbnDB = rs.getString("isbn");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String publisher = rs.getString("publisher");
					int publicationYear = rs.getInt("publication_year");
					String genre = rs.getString("genre");
					int totalCopies = rs.getInt("total_copies");
					int availableCopies = rs.getInt("available_copies");
					Date dateAddedSql = rs.getDate("date_added");

					return new Book(bookIDdb, isbnDB, title, author, publisher, publicationYear, genre, totalCopies,
							availableCopies, dateAddedSql.toLocalDate());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // if no book found
	}
}