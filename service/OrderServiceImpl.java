package in.vishal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishal.Entity.Orders;
import in.vishal.Infservice.OrderService;
import in.vishal.repo.OrdersRepo;

@Service
public class OrderServiceImpl implements OrderService{

 @Autowired
 OrdersRepo repo;

 	@Override
    public Integer addBookToOrder(Orders order)
 	{
 			return repo.save(order).getOrderId();
     }

 	

 	@Override
 	public List<Orders> getOrdersByEmail(String userMail) {
 	    return repo.findByUserMail(userMail); // Assuming the method takes an email parameter
 	}


 	@Override
	public void cancleOrderById(Integer orderId) {
		repo.deleteById(orderId);
	}


 	@Override
	public List<Orders> getallOrders() {
		return repo.findAll();
	}



	


	

	
 
 

	

	
}
