package java_assignments;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tekworks {

	public static void main(String[] args) {
		LibraryManager library = new LibraryManager();
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("--Library Manager Shelf Master--");
			System.out.println("1 add book");
			System.out.println("2 remove book");
			System.out.println("3 search book");
			System.out.println("4 borrow book");
			System.out.println("5 return book");
			System.out.println("6 display all books");
			System.out.println("7 save and exit");
			System.out.println("enter yout choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
			case 1:
				System.out.println("enter title: ");
				String title=sc.nextLine();
				System.out.println("enter author: ");
				String author=sc.nextLine();
				System.out.println("enter isbn: ");
				String isbn=sc.nextLine();
				library.addBook(new Book(title,author,isbn,true));
				break;
			
			case 2:
				System.out.println("enter isbn: ");
				isbn=sc.nextLine();
				library.removeBook(isbn);
				break;
				
			case 3:
				System.out.println("enter title or author or isbn: ");
				String query=sc.nextLine();
				library.searchBook(query);
				break;
				
			case 4:
				System.out.println("enter isbn: ");
				isbn=sc.nextLine();
				library.borrowBook(isbn);
				break;
				
			case 5 :
				System.out.println("enter isbn: ");
				isbn=sc.nextLine();
				library.returnBook(isbn);
				break;
				
			case 6:
				library.displayBook();
				break;
				
			case 7:
				library.saveBook();
				System.out.println("good bye!");
				return;
			
			default:
				System.out.println("invalid choice try again ");
				
				
			
			}
		}
		

	}

}
class LibraryManager{
	private List<Book> books;
	private static final String File_Name ="LibraryManager_data.ser";
	
	public LibraryManager() {
		books = loadBooks();
	}
	
	public void addBook(Book book) {
		for(Book existingBook :books) {
			if(book.getIsbn().equals(existingBook.getIsbn())) {
				System.out.println("isbn is already exists");
				return;
			}
			
		}
		books.add(book);
		System.out.println("book added succesfully");
	}
	public void removeBook(String isbn) {
		for(Book book :books) {
			if(book.getIsbn().equals(isbn)) {
				books.remove(book);
				System.out.println("book removed succesfully");
				return;
			}
		}
		System.out.println("no book found with this isbn");
	}
	public void searchBook(String query) {
		for(Book book : books) {
			if(query.equals(book.getTitle())
				|| query.equals(book.getAuthor())
				|| query.equals(book.getIsbn())) {
				System.out.println(book);
				return;
			}
		}
		System.out.println("no book found");
		
	}
	public void borrowBook(String isbn) {
		for(Book book : books) {
			if(book.getIsbn().equals(isbn)) {
				book.setAvailable(false);
				System.out.println("book borrowed succesfully");
				return;
			}
		}
		System.out.println("book not found");
	}
	public void returnBook(String isbn) {
		for(Book book : books) {
			if(book.getIsbn().equals(isbn)) {
				if(!book.isAvailable()) {
					book.setAvailable(true);
					System.out.println("book returned sucesfully");
					
				}else {
					System.out.println("book is not borrowed");
					
				}
			}return;
			
		}System.out.println("no book found with this isbn");
		
		
	}
	public void displayBook() {
		if(books.isEmpty()) {
			System.out.println("out of stock");
		}else {
			for(Book book:books) {
				System.out.println(book);
			}
		}
	}
	public void saveBook() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(File_Name))) {
			oos.writeObject(books);
			System.out.println("books saved succesfully");
		} catch (IOException e) {
			System.out.println("error in saving books"+ e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	public List<Book> loadBooks(){
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(File_Name))){
			return(List<Book>) ois.readObject();
		}
	    catch (IOException | ClassNotFoundException e) {
        System.out.println("No previous data found. Starting fresh.");
        return new ArrayList<>();
    }
	}
	
}
class Book implements Serializable{
	private String title;
	private String author;
	private String isbn;
	private boolean isAvailable;
	public Book(String title, String author, String isbn, boolean isAvailable) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.isAvailable = isAvailable;
	}
	public String getTitle() {
		return title;
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
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", isbn=" + isbn + ", isAvailable=" + isAvailable + "]";
	}
	
}
