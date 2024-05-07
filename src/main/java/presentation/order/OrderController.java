package presentation.order;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderController {
    private OrderView orderView;
    private OrderBLL orderBLL;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;

    public OrderController(OrderView orderView, OrderBLL orderBLL, ClientBLL clientBLL, ProductBLL productBLL) {
        this.orderView = orderView;
        this.orderBLL = orderBLL;
        this.clientBLL = clientBLL;
        this.productBLL = productBLL;

        orderView.getCreateOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String clientName = (String)orderView.getClientBox().getSelectedItem();
                    String productName = (String)orderView.getProductBox().getSelectedItem();
                    int quantity = Integer.parseInt(orderView.getQuantityField().getText());

                    Client client = clientBLL.findByName(clientName);
                    Product product = productBLL.findByName(productName);

                    if(product.getQuantity() >= quantity){
                        Order order = new Order(client.getId(), product.getId(), quantity);
                        orderBLL.insertOrder(order);
                        product.setQuantity(product.getQuantity() - quantity);
                        productBLL.update(product);
                    }else{
                        JOptionPane.showMessageDialog(null, "The stock for the selected product is not enough for the selected quantity!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (IllegalAccessException ex){
                    JOptionPane.showMessageDialog(null, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
