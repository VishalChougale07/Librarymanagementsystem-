package in.vishal.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uId; // Ensure this matches the database type (int in MySQL)

    @Column(name = "user_name", nullable = false)
    private String uName;

    @Column(name = "user_phone", nullable = false, unique = true)
    private String uPhone;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail; // Updated to match the form field name

    @Column(name = "user_password", nullable = false)
    private String uPassword;

	public Integer getUId() {
		return uId;
	}

	public void setUId(Integer uId) {
		this.uId = uId;
	}

	public String getUName() {
		return uName;
	}

	public void setUName(String uName) {
		this.uName = uName;
	}

	public String getUPhone() {
		return uPhone;
	}

	public void setUPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUPassword() {
		return uPassword;
	}

	public void setUPassword(String uPassword) {
		this.uPassword = uPassword;
	}
    
    
}
