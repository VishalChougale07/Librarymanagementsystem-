package in.vishal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vishal.Entity.Book;
import in.vishal.Entity.Orders;
import in.vishal.repo.OrdersRepo;
import in.vishal.service.BookServiceImpl;
import in.vishal.service.OrderServiceImpl;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/Book")
public class BookController {

	@Autowired
	BookServiceImpl service;
	
	@Autowired
	OrderServiceImpl orderServ;
	
	
	
	@GetMapping("/home")
	public String Home(Model model,HttpSession session) {
		 if (session == null) {
	  	        return "redirect:/login"; // Prevent NullPointerException
	  	    }

	  	    Object uname = session.getAttribute("uname");
	  	    Object umail = session.getAttribute("umail");

	  	   
	  	    model.addAttribute("uname", uname);
	  	    model.addAttribute("umail", umail);

		
		return "home";
	}
	
	@GetMapping("/addbooks")
	public String addBooks(HttpSession session, Model model) {

		Object uname = session.getAttribute("uname");
	    Object umail = session.getAttribute("umail");
	    Object uphone = session.getAttribute("uphone");

	    // Debugging session values in the console
	    System.out.println("Session uname: " + uname);
	    System.out.println("Session umail: " + umail);
	    System.out.println("Session uphone: " + uphone);

	    if (uname != null) {
	        model.addAttribute("uname", uname);
	        model.addAttribute("umail", umail);
	        model.addAttribute("uphone", uphone);
	    }
		
		return "addBooks";
	}
	
	//adding book to database
	@PostMapping("/add")
	public String addBook(@ModelAttribute Book book,Model model) {
	   Integer uid=service.saveBook(book);
	   List<Book> books = service.getAllBooks(); 
	    if (books == null) {
	        books = new ArrayList<>(); // Avoid Thymeleaf errors
	    }
	    model.addAttribute("books", books);
	   
		return "books";
	}
	
	@GetMapping("/getallbooks")
	public String getallBooks(HttpSession session, Model model) {
		
		Object uname = session.getAttribute("uname");
	    Object umail = session.getAttribute("umail");
	    Object uphone = session.getAttribute("uphone");

	    // Debugging session values in the console
	    System.out.println("Session uname: " + uname);
	    System.out.println("Session umail: " + umail);
	    System.out.println("Session uphone: " + uphone);

	    if (uname != null) {
	        model.addAttribute("uname", uname);
	        model.addAttribute("umail", umail);
	        model.addAttribute("uphone", uphone);
	    }
	    List<Book> books = service.getAllBooks(); 
	    if (books == null) {
	        books = new ArrayList<>(); // Avoid Thymeleaf errors
	    }
	    model.addAttribute("books", books);
	   
		return "books";
	}
	
	@GetMapping("/buy-product/{id}")
	public String getBookById(@PathVariable("id") Integer id, HttpSession session, Model model) {
	    if (session == null) {
	        return "redirect:/login"; // Redirect to login if session is null
	    }

	    Object uname = session.getAttribute("uname");
	    if (uname == null) {
	        model.addAttribute("status", "Please login.");
	        return "login";
	    }

	    Book book = service.getBookById(id);
	    model.addAttribute("book", book);
	    return "buyingPage";
	}


	@PostMapping("/place-order")
	public String addingBookToOrder(@RequestParam("id") Integer id, HttpSession session, Model model) {
	    if (session == null) {
	        return "redirect:/login"; // Prevent NullPointerException
	    }

	    Object uname = session.getAttribute("uname");
	    Object umail = session.getAttribute("umail");

	    if (uname == null || umail == null) {
	        model.addAttribute("status", "Please login.");
	        return "login";
	    }

	    model.addAttribute("uname", uname);
	    model.addAttribute("umail", umail);

	    // Get Book 
	    Book book = service.getBookById(id);

	    if (book != null) {
	        Orders order = new Orders();
	        order.setUserName(uname.toString());  // Set user name
	        order.setUserMail(umail.toString()); // Set user email
	        order.setTitle(book.getTitle());
	        order.setAuthor(book.getAuthor());
	        order.setBookImage(book.getBookImage());
	        order.setPrice(book.getPrice());

	        Integer i = orderServ.addBookToOrder(order);
	        if (i > 0) {
	            // Fetch all orders from database (Assuming getAllOrders() exists)
	            List<Orders> ordersList = orderServ.getOrdersByEmail(umail.toString());
	            model.addAttribute("orders", ordersList);
	            return "orders"; // Return to orders page
	        } 
	    }

	    return "buyingPage"; // Book not found or order failed
	}
	
	
	@GetMapping("/allOrders")
	public String getAllOrders(Model model, HttpSession session) {
	    // Validate session
	    if (session == null || session.getAttribute("uname") == null || session.getAttribute("umail") == null) {
	        model.addAttribute("status", "Please login.");
	        return "redirect:/login";
	    }

	    // Retrieve user details from session
	    String uname = session.getAttribute("uname").toString();
	    String umail = session.getAttribute("umail").toString();
	    String uphone = (session.getAttribute("uphone") != null) ? session.getAttribute("uphone").toString() : "";

	    model.addAttribute("uname", uname);
	    model.addAttribute("umail", umail);
	    model.addAttribute("uphone", uphone);

	    // Fetch all orders
	    
	    List<Orders> ordersList = orderServ.getOrdersByEmail(umail);
	    model.addAttribute("orders", ordersList);

	    return "orders";
	}

     @PostMapping("/cancel-order")
     public String orderCancle(@RequestParam("orderId") Integer orderId, HttpSession session, Model model) {
    	 if (session == null) {
 	        return "redirect:/login"; // Prevent NullPointerException
 	    }

 	    Object uname = session.getAttribute("uname");
 	    Object umail = session.getAttribute("umail");

 	    if (uname == null || umail == null) {
 	        model.addAttribute("status", "Please login.");
 	        return "login";
 	    }

 	    model.addAttribute("uname", uname);
 	    model.addAttribute("umail", umail);

    	 orderServ.cancleOrderById(orderId);
    	 model.addAttribute("cancle","your order has been cancled...!");
    	 return "home";
     }
     
     @GetMapping("/getEachBook")
     public String bookUnderUse(Model model, HttpSession session) {
         if (session == null) {
             return "redirect:/login"; // Prevent NullPointerException
         }

         Object uname = session.getAttribute("uname");
         Object umail = session.getAttribute("umail");

         if (uname == null || umail == null) {
             model.addAttribute("status", "Please login.");
             return "login";
         }

         model.addAttribute("uname", uname);
         model.addAttribute("umail", umail);

         List<Orders> order = orderServ.getallOrders();
         model.addAttribute("orders", order);

         return "orders";
     }

}
