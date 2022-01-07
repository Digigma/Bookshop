package bookshop;

public class Book {

	private String isbn;
	private String title;
	private String firstauthor;
	private double price;
	private int quantity;
	
	
	public Book(String isbn,String title,String author,double price,int quantity) {
 
		this.isbn=isbn;
		this.title=title;
		this.firstauthor=author;
		this.price=price;
		this.quantity=quantity;
			
	}

	public Book() {
		
		this.isbn=" ";
		this.title=" ";
		this.firstauthor=" ";
		this.price=0;
		this.quantity=0;
		
	}

	// setter and getters for all properties of book
	public void setIsbn(String i) {
		  isbn=i;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setTitle(String str) {
		title=str;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setFirstAuthor(String str) {
		firstauthor=str;
	}
	
	public String getFirstAuthor() {
		return firstauthor;
	}
	
	public void setPrice(double d) {
		price=d;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setQuantity(int i) {
		quantity=i;
	}
	
	public int getQuantity() {
		return quantity;
	}

}
