package main.entity;

/**
 * @author AdNovum Informatik AG
 */
public class BookEntity {

	private String title;

	private boolean isBorrowed;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean borrowed) {
		isBorrowed = borrowed;
	}

}
