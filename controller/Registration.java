package in.vishal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.vishal.Entity.User;
import in.vishal.service.UserImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@RequestMapping("/library")
public class Registration {

	@Autowired
	private UserImpl service;
	
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/registor")
	public String registorePage() {
		return "registore";
	}
	
	
	@PostMapping("/userRegister")
	public String registoreUser(@ModelAttribute User user,Model model) {
	String page="";
	boolean exist=service.checkUser(user.getUserEmail());
		if(exist==false) {
			Integer uid=service.saveUser(user);
			String uname=user.getUName();
			
			if(uid>0) {
				model.addAttribute("message", uname + " Registered Succesfully with id :" + uid);
				page = "login";
			} else {
				model.addAttribute("message", "Email already exists! Please use a different email.");
				page = "redirect:/library/registor";
			
			}
		}else {

			model.addAttribute("message", "Registration UnSuccesfull");
			page = "redirect:/library/registor";

		}
		
		return page;
		
	}
	
	//login controller
	@PostMapping("/userlogin")
	public String userLogin(User user, HttpSession session, Model model) { //we can also use @modelAttribute
	    String page = "login"; // Default to login page if unsuccessful
	    String status = service.loginUser(user.getUserEmail(), user.getUPassword(), session);
	    System.out.println("Session uname: " + session.getAttribute("uname"));

	    if ("success".equals(status)) { // Avoid potential NullPointerException
	        model.addAttribute("uname", session.getAttribute("uname"));
	        model.addAttribute("umail", session.getAttribute("umail"));
	        model.addAttribute("uphone", session.getAttribute("uphone"));

	        if ("admin@gmail.com".equals(user.getUserEmail()) && "12345678".equals(user.getUPassword())) {
	            page = "home";
	        } else {
	            page = "home";
	        }
	    } else {
	        model.addAttribute("status", "Invalid email or password");
	    }

	    return page;
	}
	
	//logout
	@GetMapping("/logout")
	public String userLogout(HttpSession session) {
	    session.invalidate();
	    return "home";
	}

}
