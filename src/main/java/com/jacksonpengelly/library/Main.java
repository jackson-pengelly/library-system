package com.jacksonpengelly.library;

import java.util.List;
import java.util.Scanner;

import com.jacksonpengelly.exception.ValidationException;
import com.jacksonpengelly.models.Book;
import com.jacksonpengelly.models.Member;
import com.jacksonpengelly.util.LibraryService;

public class Main {
	public static void main(String[] args) {
		LibraryService service = new LibraryService();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("=== Library Menu ===");
			System.out.println("1. Book Management");
			System.out.println("2. Member Management");
			System.out.println("3. Transaction Management");
			System.out.println("4. Exit");
			int choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println();

			switch (choice) {
			case 1:
				System.out.println("=== Book Management ===");
				System.out.println("1. Add Book");
				System.out.println("2. View All Books");
				System.out.println("3. Search For Book");
				System.out.println("4. Delete Book");
				System.out.println("5. Update Book");
				choice = scanner.nextInt();
				scanner.nextLine();
				System.out.println();

				switch (choice) {
				case 1:
					System.out.print("ISBN: ");
					String isbn = scanner.nextLine();

					System.out.print("Title: ");
					String title = scanner.nextLine();

					System.out.print("Author: ");
					String author = scanner.nextLine();

					System.out.print("Publisher: ");
					String publisher = scanner.nextLine();

					System.out.print("Publication Year: ");
					int publicationYear = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Genre: ");
					String genre = scanner.nextLine();

					System.out.print("Total Copies: ");
					int totalCopies = scanner.nextInt();
					scanner.nextLine();

					try {
						service.addBook(isbn, title, author, publisher, publicationYear, genre, totalCopies);
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					List<Book> books;
					try {
						books = service.viewAllBooks();

						books.forEach(
								s -> System.out.println(s.getTitle() + ", by " + s.getAuthor() + " - " + s.getISBN()));
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.println("Search by 1. ISBN or 2. Book ID");
					choice = scanner.nextInt();
					scanner.nextLine();

					switch (choice) {
					case 1:
						System.out.print("ISBN: ");
						isbn = scanner.nextLine();

						try {
							System.out.println(service.searchBook(isbn).toString());
						} catch (ValidationException e) {
							System.out.println(e.getMessage());
						}
						break;
					case 2:
						System.out.print("Book ID: ");
						int bookID = scanner.nextInt();
						scanner.nextLine();

						try {
							System.out.println(service.searchBook(bookID).toString());
						} catch (ValidationException e) {
							System.out.println(e.getMessage());
						}
						break;
					}
					break;
				case 4:
					System.out.print("ID of book to delete: ");
					int bookID = scanner.nextInt();
					scanner.nextLine();

					try {
						service.deleteBook(bookID);
						System.out.println("Book with " + bookID + " ID successfully deleted.");
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.print("ID of book to update: ");
					int id = scanner.nextInt();
					scanner.nextLine();

					Book book;
					try {
						book = service.searchBook(id);
						System.out.println("(Press enter to keep current value)");

						System.out.print("ISBN [" + book.getISBN() + "]: ");
						String isbnInput = scanner.nextLine();
						if (!isbnInput.trim().isEmpty()) {
							isbn = isbnInput;
						} else {
							isbn = book.getISBN();
						}

						System.out.print("Title [" + book.getTitle() + "]: ");
						String input = scanner.nextLine();
						if (!input.trim().isEmpty()) {
							title = input;
						} else {
							title = book.getTitle();
						}

						System.out.print("Author [" + book.getAuthor() + "]: ");
						input = scanner.nextLine();
						if (!input.trim().isEmpty()) {
							author = input;
						} else {
							author = book.getAuthor();
						}

						System.out.print("Publisher [" + book.getPublisher() + "]: ");
						input = scanner.nextLine();
						if (!input.trim().isEmpty()) {
							publisher = input;
						} else {
							publisher = book.getPublisher();
						}

						System.out.print("Publication Year [" + book.getPublicationYear() + "]: ");
						input = scanner.nextLine();
						if (!input.trim().isEmpty()) {
							try {
								publicationYear = Integer.parseInt(input);
							} catch (NumberFormatException e) {
								throw new ValidationException("Error: Publication year must be an integer.");
							}
						} else {
							publicationYear = book.getPublicationYear();
						}

						System.out.print("Genre [" + book.getGenre() + "]: ");
						input = scanner.nextLine();
						if (!input.trim().isEmpty()) {
							genre = input;
						} else {
							genre = book.getGenre();
						}

						System.out.print("Total Copies [" + book.getTotalCopies() + "]: ");
						input = scanner.nextLine();
						if (!input.trim().isEmpty()) {
							try {
								totalCopies = Integer.parseInt(input);
							} catch (NumberFormatException e) {
								throw new ValidationException("Error: Total copies must be an integer.");
							}

							if (totalCopies < book.getTotalCopies() - book.getAvailableCopies()) {
								throw new ValidationException(
										"Error: Total copies cannot be less than number of copies currently issued.");
							}
						} else {
							totalCopies = book.getTotalCopies();
						}
						
						service.updateBook(id, isbn, title, author, publisher, publicationYear, genre, totalCopies);
						System.out.println("Book with " + id + " ID successfully updated.");
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
						break;
					}
					break;
				default:
					System.out.println("Invalid choice.");
					break;
				}
				break;
			case 2:
				System.out.println("=== Member Management ===");
				System.out.println("1. Register Member");
				System.out.println("2. View All Members");
				System.out.println("3. Search For Member");
				System.out.println("4. Delete Member");
				System.out.println("5. Update Member");
				choice = scanner.nextInt();
				scanner.nextLine();
				System.out.println();

				switch (choice) {
				case 1:
					System.out.print("First name: ");
					String firstName = scanner.nextLine();

					System.out.print("Last name: ");
					String lastName = scanner.nextLine();

					System.out.print("Email: ");
					String email = scanner.nextLine();

					System.out.print("Phone number: ");
					String phoneNumber = scanner.nextLine();

					System.out.print("Address: ");
					String address = scanner.nextLine();

					System.out.print("Premium member? (y/n): ");
					boolean premium = scanner.nextLine().trim().equalsIgnoreCase("y");

					System.out.print("Active member? (y/n): ");
					boolean active = scanner.nextLine().trim().equalsIgnoreCase("y");

					try {
						service.registerMember(firstName, lastName, email, phoneNumber, address, premium, active);
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					List<Member> members;
					try {
						members = service.viewAllMembers();

						members.forEach(s -> System.out
								.println(s.getFirstName() + " " + s.getLastName() + " - " + s.getEmail()));
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.print("Search for member by 1. Member ID or 2. Email.");
					choice = scanner.nextInt();
					scanner.nextLine();

					switch (choice) {
					case 1:
						System.out.print("Member ID: ");
						int memberID = scanner.nextInt();
						scanner.nextLine();

						try {
							Member member = service.searchForMemberByID(memberID);
							System.out.println(member.toString());
						} catch (ValidationException e) {
							System.out.println(e.getMessage());
						}
						break;
					case 2:
						System.out.print("Email: ");
						email = scanner.nextLine();

						try {
							Member member = service.searchForMemberByEmail(email);
							System.out.println(member.toString());
						} catch (ValidationException e) {
							System.out.println(e.getMessage());
						}
						break;
					default:
						System.out.println("Invalid choice.");
						break;
					}
					break;
				case 4:
					System.out.print("Member ID: ");
					int memberID = scanner.nextInt();
					scanner.nextLine();

					try {
						service.deleteMember(memberID);
						System.out.println("Member with " + memberID + " ID successfully deleted.");
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.print("Member ID: ");
					memberID = scanner.nextInt();
					scanner.nextLine();

					Member member;
					try {
						member = service.searchForMemberByID(memberID);
						System.out.println("(Press enter to keep current value)");

						System.out.print("First name [" + member.getFirstName() + "]: ");
						String input = scanner.nextLine();
						String firstNameInput;
						if (!input.trim().isEmpty()) {
							firstNameInput = input;
						} else {
							firstNameInput = member.getFirstName();
						}

						System.out.print("Last name [" + member.getLastName() + "]: ");
						input = scanner.nextLine();
						String lastNameInput;
						if (!input.trim().isEmpty()) {
							lastNameInput = input;
						} else {
							lastNameInput = member.getLastName();
						}

						System.out.print("Email [" + member.getEmail() + "]: ");
						input = scanner.nextLine();
						String emailInput;
						if (!input.trim().isEmpty()) {
							emailInput = input;
						} else {
							emailInput = member.getEmail();
						}

						System.out.print("Phone number [" + member.getPhoneNumber() + "]: ");
						input = scanner.nextLine();
						String phoneNumberInput;
						if (!input.trim().isEmpty()) {
							phoneNumberInput = input;
						} else {
							phoneNumberInput = member.getPhoneNumber();
						}

						System.out.print("Address [" + member.getAddress() + "]: ");
						input = scanner.nextLine();
						String addressInput;
						if (!input.trim().isEmpty()) {
							addressInput = input;
						} else {
							addressInput = member.getAddress();
						}

						System.out.print("Premium member? (y/n) [" + (member.isPremium() ? "y" : "n") + "]: ");
						input = scanner.nextLine();
						boolean premiumInput;
						if (!input.trim().isEmpty()) {
							premiumInput = input.trim().equalsIgnoreCase("y");
						} else {
							premiumInput = member.isPremium();
						}

						System.out.print("Active member? (y/n) [" + (member.isActive() ? "y" : "n") + "]: ");
						input = scanner.nextLine();
						boolean activeInput;
						if (!input.trim().isEmpty()) {
							activeInput = input.trim().equalsIgnoreCase("y");
						} else {
							activeInput = member.isActive();
						}
						
						service.updateMember(memberID, firstNameInput, lastNameInput, emailInput, phoneNumberInput,
								addressInput, premiumInput, activeInput);
						System.out.println("Member with " + memberID + " ID successfully updated.");
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				default:
					System.out.println("Invalid choice.");
					break;
				}
				break;
			case 3:
				System.out.println("=== Transaction Management ===");
				System.out.println("1. Issue Book");
				System.out.println("2. Return Book");
				choice = scanner.nextInt();
				scanner.nextLine();
				System.out.println();

				switch (choice) {
				case 1:
					System.out.print("Book ID: ");
					int bookID = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Member ID: ");
					int memberID = scanner.nextInt();
					scanner.nextLine();

					try {
						service.issueBook(memberID, bookID);
						System.out.println("Book issued.");
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					System.out.print("Book ID: ");
					bookID = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Member ID: ");
					memberID = scanner.nextInt();
					scanner.nextLine();

					try {
						service.returnBook(memberID, bookID);
						System.out.println("Book returned.");
					} catch (ValidationException e) {
						System.out.println(e.getMessage());
					}
					break;
				default:
					System.out.println("Invalid choice.");
					break;
				}
				break;
			case 4:
				scanner.close();
				System.out.println("Exiting...");
				System.exit(0);
			default:
				System.out.println("Invalid choice. ");
				break;
			}
		}
	}
}