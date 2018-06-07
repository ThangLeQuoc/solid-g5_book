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
import main.service.BookBorrowingServiceBean;
import org.junit.Before;
import org.junit.Test;

/**
 * @author AdNovum Informatik AG
 */
public class BookBorrowingServiceTest {

	private BookBorrowingServiceBean bookBorrowingService;

	private UserEntity testUser;

	private List<BookEntity> bookshelf;

	private UserEntity createDefaultTestUser() {
		UserEntity user = new UserEntity();
		user.setUserName("testUser");
		user.setDepartment("Software Engineer");
		user.setSeniorityLevel("Profesional Software Engineer");
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
		bookBorrowingService = new BookBorrowingServiceBean();
	}

	@Test
	public void testFindBookByTitleFromBookshelf() {
		// given:
		String title = MASTERING_JAVA;

		// when:
		BookEntity bookFound = bookBorrowingService.findBookByTitle(title, bookshelf);

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
			bookBorrowingService.borrowBook(testUser, lentBook);
			fail();
		}
		catch (BusinessException e) {
			// then:
			assertEquals(BusinessException.BOOK_WAS_LENT, e.getMessage());
		}
	}

	@Test
	public void testBorrowBook() {
		// given:
		UserEntity user = testUser;
		String title = MASTERING_JAVA;
		BookEntity bookToBorrow = bookBorrowingService.findBookByTitle(title, bookshelf);
		assertNotNull(bookToBorrow);

		// when:
		bookBorrowingService.borrowBook(user, bookToBorrow);

		// then:
		assertEquals(title, bookToBorrow.getTitle());
		assertTrue(bookToBorrow.isBorrowed());
	}
}
