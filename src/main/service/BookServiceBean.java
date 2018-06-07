package main.service;

import static main.exception.BusinessException.BOOK_NOT_FOUND;
import static main.exception.BusinessException.BOOK_WAS_LENT;

import java.util.ArrayList;
import java.util.List;

import main.entity.BookEntity;
import main.entity.UserEntity;
import main.exception.BusinessException;

/**
 * @author AdNovum Informatik AG
 */
public class BookServiceBean {

	private boolean checkIfBookCanBeBorrowed(BookEntity bookToBorrow) {
		return !bookToBorrow.isBorrowed();
	}

	public BookEntity findBookByTitle(String title, List<BookEntity> bookshelf) {
		for (BookEntity book : bookshelf) {
			if (book.getTitle().equalsIgnoreCase(title)) {
				return book;
			}
		}

		throw new BusinessException(BOOK_NOT_FOUND);
	}

	public void borrowBook(UserEntity user, BookEntity book) {
		if (!checkIfBookCanBeBorrowed(book)) {
			throw new BusinessException(BOOK_WAS_LENT);
		}

		List<String> bookTitleToBorrow = new ArrayList<String>();
		bookTitleToBorrow.add(book.getTitle());
		user.setBorrowingBookTitles(bookTitleToBorrow);
		book.setBorrowed(Boolean.TRUE);
	}

	public void returnBook(UserEntity user, BookEntity book) {
		// check if user has that book

		// get the book

		// return it

	}
}
