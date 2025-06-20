package in.vishal.Infservice;


import in.vishal.Entity.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {

	boolean checkUser(String email);
	
	Integer saveUser(User user);
	
    String loginUser(String userEmail, String uPassword, HttpSession session);


}
