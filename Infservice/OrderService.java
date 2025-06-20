package in.vishal.Infservice;

import java.util.List;

import in.vishal.Entity.Orders;

public interface OrderService {

	public Integer addBookToOrder(Orders order);
	
    //get orders by email
	public List<Orders> getOrdersByEmail(String userEmail);
	
	//cancle order
	public void cancleOrderById(Integer orderId);
	
	//get all the orders
	public List<Orders> getallOrders();
	
	
}
