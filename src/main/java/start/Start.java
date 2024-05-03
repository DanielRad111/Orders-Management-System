package start;

import java.util.logging.Logger;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import presentation.main.MainView;
import presentation.client.ClientController;
import presentation.client.ClientView;
import presentation.order.OrderController;
import presentation.order.OrderView;
import presentation.product.ProductController;
import presentation.product.ProductView;

import javax.swing.*;

public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the Main View
            MainView mainView = new MainView();

            // Create the BLL instances
            ClientBLL clientBLL = new ClientBLL();
            ProductBLL productBLL = new ProductBLL();
            OrderBLL orderBLL = new OrderBLL();

            // Create the Client and Product Views
            ClientView clientView = new ClientView();
            ProductView productView = new ProductView();

            // Create the Controllers and wire the Views and the BLLs
            ClientController clientController = new ClientController(clientView, clientBLL);
            ProductController productController = new ProductController(productView, productBLL);

            // Set up action listeners for the main view buttons
            mainView.getViewClientsButton().addActionListener(e -> clientView.setVisible(true));
            mainView.getViewProductsButton().addActionListener(e -> productView.setVisible(true));

//             Set up action listener for the "View Orders" button
            mainView.getViewOrdersButton().addActionListener(e -> {
//                Create the Order View and Controller when the button is clicked
                OrderView orderView = new OrderView(clientBLL.getAllClientNames(), productBLL.getAllProductNames());
                new OrderController(orderView, orderBLL, clientBLL, productBLL);
            });
        });
    }
}
