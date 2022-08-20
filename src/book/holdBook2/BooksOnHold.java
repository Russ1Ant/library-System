package book.holdBook2;

public class BooksOnHold {
	
	
	private String isbn;
	private String bookTitle;
	private String studentId;
	private String studentName;
	
	
	public BooksOnHold(String isbn, String bookTitle, String studentId, String studentName) {
		// TODO Auto-generated constructor stub
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.studentId = studentId;
		this.studentName = studentName;
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
