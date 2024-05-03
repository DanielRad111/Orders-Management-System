package presentation.order;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderView extends JFrame {
    private JComboBox<String> clientBox;
    private JComboBox<String> productBox;
    private JTextField quantityField = new JTextField(20);
    private JButton createOrderButton = new JButton("Create Order");

    public OrderView(List<String> clientNames, List<String> productNames) {
        setTitle("Order creation");
        clientBox = new JComboBox<>(clientNames.toArray(new String[0]));
        productBox = new JComboBox<>(productNames.toArray(new String[0]));

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(700, 500);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(new JLabel("Client Name:"), BorderLayout.WEST);
        add(clientBox, BorderLayout.CENTER);

        add(new JLabel("Product Name:"), BorderLayout.EAST);
        add(productBox, BorderLayout.SOUTH);

        add(new JLabel("Quantity:"), BorderLayout.NORTH);
        add(quantityField, BorderLayout.CENTER);

        add(createOrderButton);

        setVisible(true);
    }

    public JComboBox<String> getClientBox() {
        return clientBox;
    }

    public JComboBox<String> getProductBox() {
        return productBox;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }
}
