package in.vishal.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishal.Entity.Book;
import in.vishal.Entity.Orders;
import in.vishal.Infservice.BookService;
import in.vishal.repo.BookRepository;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepository repo;
	
	@Override
	public Integer saveBook(Book book) {
        
		return repo.save(book).getId();
	}

	@Override
	public List<Book> getAllBooks() {
		
		return repo.findAll();
	}

	public Book getBookById(Integer id) {
		return repo.findById(id).get();
	}

	
	
}
