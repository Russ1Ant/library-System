package book.viewBooksInDB;

import java.util.Date;

public class Book {
	
	//attributes
	private String title;
	private String author;
	private String isbn;
	private  int quantity;
	private String publisher;
	private int issued;
	private Date issueDate;
	
	public Book(String title, String author, String isbn, int quantity, String publisher, int issued, Date issueDate) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.quantity = quantity;
		this.publisher = publisher;
		this.issued = issued;
		this.issueDate = issueDate;
	}
	
	//getters and Setters
	
	public String getTitle() {
		return title;
	}
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getIssued() {
		return issued;
	}
	public void setIssued(int issued) {
		this.issued = issued;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	
}
