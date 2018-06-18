# solid-g5_book
Adnovum Group 5 - Book Challenge
## Specs
At the moment, there are 2 Entities of interest: User and Book.
User has 4 properties: name, department, seniority level, list of borrowing books (title)
Book has 2 properties: title and a check if it is borrowed or not.
User should be able to borrow a book. If that book is borrowed, user cannot borrow it until it is returned.
User cannot return a book if he/she does not borrow it.
There is a service to help user borrows/returns book.
## How to earn your points
apply Dependency Inversion to the code base. Without this pattern, you lose half of your total point.
fulfill the requirements: new test case scenarios are added base on requirement, the more tests you write the more points you earn.
all tests passes, each failed test will reduce your total points by 1.
Clean code will yield more bonuses base on reviewers.
