package in.vishal.Infservice;

import java.util.List;

import in.vishal.Entity.Book;

public interface BookService {

	Integer saveBook(Book book);
	
	public List<Book> getAllBooks() ;

}
