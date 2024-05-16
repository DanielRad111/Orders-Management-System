package presentation.order;

import bll.BillBLL;
import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
/**
 * Controller class for managing orders in the presentation layer.
 */
public class OrderController {
    private OrderView orderView;
    private OrderBLL orderBLL;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private BillBLL billBLL;

    /**
     * Constructs a new OrderController object.
     *
     * @param orderView The view associated with the order controller.
     * @param orderBLL The bll for orders.
     * @param clientBLL The bll for clients.
     * @param productBLL The bll for products.
     * @param billBLL The bll for bills.
     */
    public OrderController(OrderView orderView, OrderBLL orderBLL, ClientBLL clientBLL, ProductBLL productBLL, BillBLL billBLL) {
        this.orderView = orderView;
        this.orderBLL = orderBLL;
        this.clientBLL = clientBLL;
        this.productBLL = productBLL;
        this.billBLL = billBLL;

        refreshTable();

        orderView.getCreateOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = Integer.parseInt(orderView.getOrderId().getText());
                    String clientName = (String)orderView.getClientBox().getSelectedItem();
                    String productName = (String)orderView.getProductBox().getSelectedItem();
                    int quantity = Integer.parseInt(orderView.getQuantityField().getText());

                    Client client = clientBLL.findByName(clientName);
                    Product product = productBLL.findByName(productName);

                    if(product.getQuantity() >= quantity){
                        Order order = new Order(id, client.getId(), product.getId(), quantity);
                        orderBLL.insertOrder(order);
                        product.setQuantity(product.getQuantity() - quantity);
                        productBLL.update(product);
                        Bill bill = new Bill(order.getId(), client.getName(), product.getName(), quantity, LocalDate.now());
                        billBLL.insertBill(bill);
                        refreshTable();
                    }else{
                        JOptionPane.showMessageDialog(null, "The stock for the selected product is not enough for the selected quantity!","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (IllegalAccessException ex){
                    JOptionPane.showMessageDialog(null, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        orderView.getEditOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        orderView.getDeleteOrderButton().addActionListener(e-> {
            try {
                deleteOrder();
                refreshTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    /**
     * Refreshes the table displaying orders.
     */
    public  void refreshTable(){
        List<Order> orderList = orderBLL.findAllOrders();
        orderView.generateTableFromObjects(orderList);
    }

    /**
     * Deletes the selected order.
     *
     * @throws SQLException If a SQL exception occurs.
     * @throws IllegalAccessException If an illegal access exception occurs.
     */
    public void deleteOrder() throws SQLException, IllegalAccessException {
        Order toDelete;
        int row = orderView.getOrderTable().getSelectedRow();
        if(row >= 0){
            int id = Integer.parseInt(orderView.getOrderTable().getModel().getValueAt(row, 0).toString());
            int clientID = Integer.parseInt(orderView.getOrderTable().getModel().getValueAt(row, 1).toString());
            int productID = Integer.parseInt(orderView.getOrderTable().getModel().getValueAt(row, 2).toString());
            int quantity = Integer.parseInt(orderView.getOrderTable().getModel().getValueAt(row, 3).toString());
            toDelete = new Order(id, clientID, productID, quantity);
            orderBLL.deleteOrder(toDelete);
            refreshTable();
        }else{
            System.out.println("No row selected");
        }
    }
}
