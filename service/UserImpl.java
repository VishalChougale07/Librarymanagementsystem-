package in.vishal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishal.Entity.User;
import in.vishal.Infservice.UserService;
import in.vishal.repo.UserRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class UserImpl implements UserService{

	@Autowired
	private UserRepo repo;
	
	@Override
	public boolean checkUser(String email) {
		boolean exist=repo.existsByUserEmail(email);
		return exist;
	}

	@Override
	public Integer saveUser(User user) {
		return repo.save(user).getUId();
	}

	@Override
	public String loginUser(String userEmail, String uPassword, HttpSession session) {
	    User user = repo.findByUserEmail(userEmail);
	    
	    // Check if the user exists
	    if (user == null) {
	        System.out.println("Login failed: User not found.");
	        return "fail"; // Return fail if no user found
	    }

	    // Validate password
	    if (!user.getUPassword().equals(uPassword)) {
	        System.out.println("Login failed: Incorrect password.");
	        return "fail"; // Return fail if password is incorrect
	    }

	    // Store user details in the session
	    session.setAttribute("umail", user.getUserEmail());
	    session.setAttribute("uname", user.getUName());
	    session.setAttribute("uphone", user.getUPhone());

	    System.out.println("Login Successful...");
	    return "success"; // Return success if login is valid
	}


	

	

}
