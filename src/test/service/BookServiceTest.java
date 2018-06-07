package test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static test.common.TestHelper.MASTERING_JAVA;
import static test.common.TestHelper.MASTERING_JAVASCRIPT;
import static test.common.TestHelper.MASTERING_LINUX;
import static test.common.TestHelper.MASTERING_TESTING;

import java.util.ArrayList;
import java.util.List;

import main.entity.BookEntity;
import main.entity.UserEntity;
import main.exception.BusinessException;
import main.service.BookServiceBean;
import org.junit.Before;
import org.junit.Test;

/**
 * @author AdNovum Informatik AG
 */
public class BookServiceTest {

	private BookServiceBean bookService;

	private UserEntity testUser;

	private List<BookEntity> bookshelf;

	private UserEntity createDefaultTestUser() {
		UserEntity user = new UserEntity();
		user.setUserName("testUser");
		user.setDepartment("Software Engineer");
		user.setSeniorityLevel("Profesional Software Engineer");
		user.setBorrowingBookTitles(new ArrayList<String>());
		return user;
	}

	private BookEntity createNewBook(String title) {
		BookEntity book = new BookEntity();
		book.setTitle(title);
		book.setBorrowed(Boolean.FALSE);
		return book;
	}

	private void addBooksToBookshelf() {
		bookshelf = new ArrayList<BookEntity>();
		bookshelf.add(createNewBook(MASTERING_JAVA));
		bookshelf.add(createNewBook(MASTERING_JAVASCRIPT));
		bookshelf.add(createNewBook(MASTERING_LINUX));
		bookshelf.add(createNewBook(MASTERING_TESTING));
	}

	@Before
	public void setup() {
		testUser = createDefaultTestUser();
		addBooksToBookshelf();
		bookService = new BookServiceBean();
	}

	@Test
	public void testFindBookByTitleFromBookshelf() {
		// given:
		String title = MASTERING_JAVA;

		// when:
		BookEntity bookFound = bookService.findBookByTitle(title, bookshelf);

		// then:
		assertNotNull(bookFound);
		assertEquals(title, bookFound.getTitle());
		assertFalse(bookFound.isBorrowed());
	}

	@Test
	public void testCannotBorrowBookIfThatBookWasLent() {
		// given: a book which was lent to someone else
		BookEntity lentBook = new BookEntity();
		lentBook.setTitle(MASTERING_JAVA);
		lentBook.setBorrowed(Boolean.TRUE);

		try {
			// when:
			bookService.borrowBook(testUser, lentBook);
			fail();
		}
		catch (BusinessException e) {
			// then:
			assertEquals(BusinessException.BOOK_WAS_LENT, e.getMessage());
		}
	}

	private void assertUpdateToBookshelf(List<BookEntity> bookshelf, BookEntity bookToUpdate) {
		for (BookEntity book : bookshelf) {
			if (book.getTitle().equalsIgnoreCase(bookToUpdate.getTitle())) {
				assertEquals(bookToUpdate.isBorrowed(), book.isBorrowed());
				return;
			}
		}
		fail("There must be an update to the bookshelf");
	}

	@Test
	public void testBorrowBook() {
		// given: get the book from bookshelf
		BookEntity bookToBorrow = bookService.findBookByTitle(MASTERING_JAVA, bookshelf);
		assertNotNull(bookToBorrow);
		assertFalse(bookToBorrow.isBorrowed());

		// when: borrow that book
		bookService.borrowBook(testUser, bookToBorrow);

		// then:
		assertEquals(MASTERING_JAVA, bookToBorrow.getTitle());
		assertTrue(bookToBorrow.isBorrowed());
		assertUpdateToBookshelf(bookshelf, bookToBorrow);
		assertEquals(1, testUser.getBorrowingBookTitles().size());
		assertEquals(MASTERING_JAVA, testUser.getBorrowingBookTitles().get(0));
	}

	@Test
	public void testReturnBorrowedBook() {
		// given: user borrowed 1 book
		BookEntity bookToReturn = bookService.findBookByTitle(MASTERING_LINUX, bookshelf);
		bookService.borrowBook(testUser, bookToReturn);
		assertEquals(1, testUser.getBorrowingBookTitles().size());
		assertTrue(bookToReturn.isBorrowed());

		// when:
		bookService.returnBook(testUser, bookToReturn);

		// then:
		bookToReturn = bookService.findBookByTitle(MASTERING_LINUX, bookshelf);
		assertFalse(bookToReturn.isBorrowed());
		assertEquals(0, testUser.getBorrowingBookTitles().size());
		assertUpdateToBookshelf(bookshelf, bookToReturn);
	}
}