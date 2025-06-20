package in.vishal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vishal.Entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Integer>{

	List<Orders> findByUserMail(String userMail);

}
