package presentation.main;

import presentation.client.ClientView;
import presentation.order.OrderView;
import presentation.product.ProductView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The main view of the application.
 */
public class MainView extends JFrame {
    /** The button to view clients. */
    private JButton viewClientsButton = new JButton("View Clients");
    /** The button to view products. */
    private JButton viewProductsButton = new JButton("View Products");
    /** The button to view orders. */
    private JButton viewOrdersButton = new JButton("View Orders");
    /** The button to view bills. */
    private JButton viewBillsButton = new JButton("View Bills");

    public MainView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(viewClientsButton);
        add(viewProductsButton);
        add(viewOrdersButton);
        add(viewBillsButton);

        setVisible(true);
    }

    public JButton getViewClientsButton() {
        return viewClientsButton;
    }
    public JButton getViewProductsButton() {
        return viewProductsButton;
    }
    public JButton getViewOrdersButton() {
        return viewOrdersButton;
    }
    public JButton getViewBillsButton() {return viewBillsButton;}

    public static void main(String[] args) {
        new MainView();
    }
}
