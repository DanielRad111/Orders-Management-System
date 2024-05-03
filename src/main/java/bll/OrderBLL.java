package bll;

import bll.validators.Validator;
import dao.DAO;
import dao.OrderDAO;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        validators = new ArrayList<Validator<Order>>();
        orderDAO = new OrderDAO();
    }

    public List<Order> findAllOrders(){
        List<Order> orders = orderDAO.findAll();
        if(orders == null){
            throw new NoSuchElementException();
        }
        return orders;
    }

    public Order findOrderById(int id){
        Order order = orderDAO.findById(id);
        if(order == null){
            throw new NoSuchElementException();
        }
        return order;
    }

    public int insertOrder(Order order) throws IllegalAccessException {
        for(Validator<Order> validator : validators){
            validator.validate(order);
        }
        return orderDAO.insert(order);
    }

    public void updateOrder(Order order) throws IllegalAccessException {
        orderDAO.update(order);
    }

    public void deleteOrder(int id) throws IllegalAccessException {
        orderDAO.delete(id);
    }
}
