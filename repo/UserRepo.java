package in.vishal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.vishal.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByUserEmail(String userEmail);

	User findByUserEmail(String userEmail);
}


