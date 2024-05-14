package start;

import bll.*;
import dao.ClientDAO;
import model.Bill;
import presentation.client.ClientController;
import presentation.client.ClientView;
import presentation.main.MainView;
import presentation.order.OrderController;
import presentation.order.OrderView;
import presentation.product.ProductController;
import presentation.product.ProductView;

import javax.swing.*;
import java.sql.Time;
import java.sql.Timestamp;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();

            ClientDAO clientDAO = new ClientDAO();
            ClientBLL clientBLL = new ClientBLL(clientDAO);
            ProductBLL productBLL = new ProductBLL();
            OrderBLL orderBLL = new OrderBLL();
            BillBLL billBLL = new BillBLL();

            ClientView clientView = new ClientView();
            ProductView productView = new ProductView();

            ClientController clientController = new ClientController(clientView, clientBLL);
            ProductController productController = new ProductController(productView, productBLL);

            mainView.getViewClientsButton().addActionListener(e -> clientView.setVisible(true));
            mainView.getViewProductsButton().addActionListener(e -> productView.setVisible(true));

            mainView.getViewOrdersButton().addActionListener(e -> {
                OrderView orderView = new OrderView(clientBLL.getAllClientNames(), productBLL.getAllProductNames());
                new OrderController(orderView, orderBLL, clientBLL, productBLL, billBLL);
            });
        });
    }
}
