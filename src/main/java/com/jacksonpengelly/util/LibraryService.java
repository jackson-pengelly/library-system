package com.jacksonpengelly.util;

import java.util.List;

import com.jacksonpengelly.dao.BookDAO;
import com.jacksonpengelly.dao.MemberDAO;
import com.jacksonpengelly.dao.TransactionDAO;
import com.jacksonpengelly.exception.ValidationException;
import com.jacksonpengelly.models.Book;
import com.jacksonpengelly.models.Member;

public class LibraryService {
	private final int premiumUserMaxLoanCount = 6;
	private final int standardUserMaxLoanCount = 3;
	private final int isbnLength1 = 10;
	private final int isbnLength2 = 13;

	private BookDAO bookDAO = new BookDAO();
	private MemberDAO memberDAO = new MemberDAO();
	private TransactionDAO transactionDAO = new TransactionDAO();

	public void addBook(String isbn, String title, String author, String publisher, int publicationYear, String genre,
			int totalCopies) throws ValidationException {
		if (isbn == null || isbn.trim().isEmpty() || title == null || title.trim().isEmpty() || author == null
				|| author.trim().isEmpty() || genre == null || genre.trim().isEmpty() || publisher == null
				|| publisher.trim().isEmpty()) {
			throw new ValidationException("Error: All fields (ISBN, Title, Author, Genre, Publisher) are required.");
		}

		if (totalCopies <= 0) {
			throw new ValidationException("Error: Total copies must be greater than 0.");
		}

		if (isbn.length() != isbnLength1 && isbn.length() != isbnLength2) {
			throw new ValidationException(
					"Error: ISBN must be " + isbnLength1 + " or " + isbnLength2 + " digits long. ");
		}

		if (!isbn.matches("[0-9]+")) {
			throw new ValidationException("Error: ISBN must only contain digits. ");
		}

		if (bookDAO.getBookByISBN(isbn) != null) {
			throw new ValidationException("Error: Book with " + isbn + " already exists.");
		}

		if (publicationYear < 0) {
			throw new ValidationException("Error: Publication year must be greater than 0.");
		}

		Book newBook = new Book(isbn, title, author, publisher, publicationYear, genre, totalCopies);
		bookDAO.addBook(newBook);
	}

	public void registerMember(String firstName, String lastName, String email, String phone, String address,
			boolean premium, boolean active) throws ValidationException {
		if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()
				|| email == null || email.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
			throw new ValidationException(
					"Error: All fields (First name, Last name, Email, Phone number) are required.");
		}

		if (!email.contains("@") || !email.contains(".")) {
			throw new ValidationException("Error: Email must contain \"@\" and \".\".");
		}

		if (memberDAO.getMemberByEmail(email) != null) {
			throw new ValidationException("Error: Email already in use.");
		}

		if (!phone.matches("[0-9]+")) {
			throw new ValidationException("Error: Phone number must only contain digits.");
		}

		if (address == null || address.trim().isEmpty()) {
			throw new ValidationException("Error: Address is required.");
		}

		Member newMember = new Member(firstName, lastName, email, phone, address, premium, active);
		memberDAO.registerMember(newMember);
	}

	public void issueBook(int memberID, int bookID) throws ValidationException {
		if (memberDAO.getMemberByMemberID(memberID) == null) {
			throw new ValidationException("Error: Member with " + memberID + " does not exist.");
		}

		Book book = bookDAO.getBookByID(bookID);
		if (book == null) {
			throw new ValidationException("Error: Book with " + bookID + " does not exist.");
		}
		if (book.getAvailableCopies() == 0) {
			throw new ValidationException("Error: No available copies of book with " + bookID);
		}

		Member member = memberDAO.getMemberByMemberID(memberID);

		if (member.isPremium()) { // allow 6 active loans for premium members
			if (transactionDAO.getLoanCount(memberID) >= premiumUserMaxLoanCount) {
				throw new ValidationException("Error: Member has reached loan limit for premium users (6).");
			}
		} else {
			if (transactionDAO.getLoanCount(memberID) >= standardUserMaxLoanCount) {
				throw new ValidationException("Error: Member has reached loan limit for standard users (3).");
			}
		}

		if (transactionDAO.hasBook(memberID, bookID)) {
			throw new ValidationException("Error: Member has already checked this book out.");
		}

		transactionDAO.issueBook(memberID, bookID);
	}

	public Book searchBook(String isbn) throws ValidationException {
		if (isbn == null || isbn.trim().isEmpty()) {
			throw new ValidationException("Error: ISBN can not be empty.");
		}

		if (!isbn.matches("[0-9]+")) {
			throw new ValidationException("Error: ISBN can only contain diigts");
		}

		Book book = bookDAO.getBookByISBN(isbn);

		if (book == null) {
			throw new ValidationException("Error: Book with ISBN: " + isbn + " does not exist.");
		}
		return bookDAO.getBookByISBN(isbn);
	}

	public Book searchBook(int bookID) throws ValidationException {
		Book book = bookDAO.getBookByID(bookID);

		if (book == null) {
			throw new ValidationException("Error: Book with " + bookID + " ID does not exist.");
		}
		return book;
	}

	public List<Book> viewAllBooks() throws ValidationException {
		List<Book> books = bookDAO.getAllBooks();

		if (books == null) {
			throw new ValidationException("Error: No books found.");
		}
		return books;
	}

	public void deleteBook(int bookID) throws ValidationException {
		Book book = bookDAO.getBookByID(bookID);

		if (book == null) {
			throw new ValidationException("Error: Book with " + bookID + " ID does not eixst.");
		}

		bookDAO.deleteBook(bookID);
	}

	public void returnBook(int memberID, int bookID) throws ValidationException {
		if (memberDAO.getMemberByMemberID(memberID) == null) {
			throw new ValidationException("Error: Member with " + memberID + " ID not found.");
		}

		if (bookDAO.getBookByID(bookID) == null) {
			throw new ValidationException("Error: Book with + " + bookID + " ID not found.");
		}

		int transactionID = transactionDAO.getTransactionID(memberID, bookID);

		if (transactionID == -1) {
			throw new ValidationException(
					"Error: Transaction with member ID: " + memberID + " and book ID: " + bookID + " not found.");
		}

		transactionDAO.returnBook(transactionID);
	}

	public List<Member> viewAllMembers() throws ValidationException {
		List<Member> members = memberDAO.getAllMembers();

		if (members == null) {
			throw new ValidationException("Error: No members found.");
		}

		return members;
	}

	public Member searchForMemberByID(int memberID) throws ValidationException {
		Member member = memberDAO.getMemberByMemberID(memberID);

		if (member == null) {
			throw new ValidationException("Error: Member with " + memberID + " not found.");
		}

		return member;
	}

	public Member searchForMemberByEmail(String email) throws ValidationException {
		Member member = memberDAO.getMemberByEmail(email);

		if (member == null) {
			throw new ValidationException("Error: Member with email: " + email + " not found.");
		}

		return member;
	}

	public void deleteMember(int memberID) throws ValidationException {
		Member member = memberDAO.getMemberByMemberID(memberID);
		if (member == null) {
			throw new ValidationException("Error: Member with " + memberID + " ID does not exist. ");
		}

		memberDAO.deleteMember(member);
	}

	public void updateBook(int bookID, String isbn, String title, String author, String publisher, int publicationYear,
			String genre, int totalCopies) throws ValidationException {
		Book book = bookDAO.getBookByID(bookID);

		if (book == null) {
			throw new ValidationException("Error: Book with " + bookID + " ID does not exist.");
		}

		if (isbn != null && !isbn.trim().isEmpty()) {
			if (isbn.length() != isbnLength1 && isbn.length() != isbnLength2) {
				throw new ValidationException(
						"Error: ISBN must be " + isbnLength1 + " or " + isbnLength2 + " digits long. ");
			}

			if (!isbn.matches("[0-9]+")) {
				throw new ValidationException("Error: ISBN must only contain digits. ");
			}

			book.setISBN(isbn);
		}

		if (title != null && !title.trim().isEmpty()) {
			book.setTitle(title);
		}

		if (author != null && !author.trim().isEmpty()) {
			book.setAuthor(author);
		}

		if (publisher != null && !publisher.trim().isEmpty()) {
			book.setPublisher(publisher);
		}

		if (publicationYear > 0) {
			book.setPublicationYear(publicationYear);
		}

		if (genre != null && !genre.trim().isEmpty()) {
			book.setGenre(genre);
		}

		if (totalCopies > 0) {
			book.setTotalCopies(totalCopies);
		}

		bookDAO.updateBook(book);
	}

	public void updateMember(int memberID, String firstName, String lastName, String email, String phone,
			String address, boolean premium, boolean active) throws ValidationException {
		Member member = memberDAO.getMemberByMemberID(memberID);

		if (member == null) {
			throw new ValidationException("Error: Member with " + memberID + " ID does not exist.");
		}

		if (firstName != null && !firstName.trim().isEmpty()) {
			member.setFirstName(firstName);
		}

		if (lastName != null && !lastName.trim().isEmpty()) {
			member.setLastName(lastName);
		}

		if (email != null && !email.trim().isEmpty()) {
			if (!email.contains("@") || !email.contains(".")) {
				throw new ValidationException("Error: Email must contain \"@\" and \".\".");
			}

			member.setEmail(email);
		}

		if (phone != null && !phone.trim().isEmpty()) {
			member.setPhoneNumber(phone);
		}

		if (!phone.matches("[0-9]+")) {
			throw new ValidationException("Error: Phone number must only contain digits.");
		}

		if (address != null && !address.trim().isEmpty()) {
			member.setAddress(address);
		}

		member.setPremium(premium);

		member.setActive(active);

		memberDAO.updateMember(member);
	}
}
