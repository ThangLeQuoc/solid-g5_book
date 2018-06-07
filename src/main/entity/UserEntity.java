package main.entity;

import java.util.List;

/**
 * @author AdNovum Informatik AG
 */
public class UserEntity {

	private String userName;

	private String department;

	private String seniorityLevel;

	private List<String> borrowingBookTitles;

	/* GETTER & SETTERS */

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSeniorityLevel() {
		return seniorityLevel;
	}

	public void setSeniorityLevel(String seniorityLevel) {
		this.seniorityLevel = seniorityLevel;
	}

	public List<String> getBorrowingBookTitles() {
		return borrowingBookTitles;
	}

	public void setBorrowingBookTitles(List<String> borrowingBookTitles) {
		this.borrowingBookTitles = borrowingBookTitles;
	}
}
