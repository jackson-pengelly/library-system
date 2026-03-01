package com.jacksonpengelly.util;

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

	public void addBook(String isbn, String title, String author, String genre, int totalCopies)
			throws ValidationException {
		if (isbn == null || isbn.trim().isEmpty() || title == null || title.trim().isEmpty() || author == null
				|| author.trim().isEmpty() || genre == null || genre.trim().isEmpty()) {
			throw new ValidationException("Error: All fields (ISBN, Title, Author, Genre) are required.");
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

		Book newBook = new Book(isbn, title, author, genre, totalCopies);
		bookDAO.addBook(newBook);
	}

	public void registerMember(String firstName, String lastName, String email, String phone)
			throws ValidationException {
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

		Member newMember = new Member(firstName, lastName, email, phone);
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
}
