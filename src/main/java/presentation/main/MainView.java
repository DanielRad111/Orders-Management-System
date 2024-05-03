package presentation.main;

import presentation.client.ClientView;
import presentation.order.OrderView;
import presentation.product.ProductView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JButton viewClientsButton = new JButton("View Clients");
    private JButton viewProductsButton = new JButton("View Products");
    private JButton viewOrdersButton = new JButton("View Orders");

    public MainView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(viewClientsButton);
        add(viewProductsButton);
        add(viewOrdersButton);

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

    public static void main(String[] args) {
        new MainView();
    }
}
