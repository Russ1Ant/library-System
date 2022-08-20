package book.viewIssued2;

import java.util.Date;

public class IssuedBooks {
	
	private String isbn;
	private String bookTitle;
	private String studentId;
	private String studentName;
	private Date issueDate;
	
	public IssuedBooks(String isbn, String title, String id, String name, Date issueDate) {
		this.isbn = isbn;
		this.bookTitle = title;
		this.studentId = id;
		this.studentName = name;
		this.issueDate = issueDate;
	}
	
	
	
	public Date getIssueDate() {
		return issueDate;
	}



	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}



	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	
	
}
