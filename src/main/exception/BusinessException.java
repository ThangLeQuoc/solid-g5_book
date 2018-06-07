package main.exception;

/**
 * @author AdNovum Informatik AG
 */
public class BusinessException extends RuntimeException {

	public static final String BOOK_NOT_FOUND = "Book not found";

	public static final String BOOK_WAS_LENT = "You cannot borrow this book, it is lent to someone else";

	private String message;

	public BusinessException(String message) {
		super(message);
	}
}
