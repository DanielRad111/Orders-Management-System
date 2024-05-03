package presentation.product;

import model.Product;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductView extends JFrame {
    private JTextField productNameField = new JTextField(20);
    private JTextField quantityField = new JTextField(20);

    private JButton addProductButton = new JButton("Add Product");
    private JButton editProductButton = new JButton("Edit Product");
    private JButton deleteProductButton = new JButton("Delete Product");

    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductView(){
        String[] columnNames = {"ID", "Name", "Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Products");
        setSize(700, 500);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(new JLabel("Product Name:"));
        add(productNameField);

        add(new JLabel("Quantity:"));
        add(quantityField);

        add(addProductButton);
        add(editProductButton);
        add(deleteProductButton);

        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane);

        setVisible(false);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if(selectedRow != -1){
                productNameField.setText((String)productTable.getValueAt(selectedRow, 1));
                quantityField.setText(productTable.getValueAt(selectedRow, 2).toString());
            }
        });
    }

    public JTextField getProductNameField() { return productNameField; }
    public JTextField getQuantityField() { return quantityField; }
    public JButton getAddProductButton() { return addProductButton;}
    public JButton getEditProductButton() { return editProductButton; }
    public JButton getDeleteProductButton() { return deleteProductButton; }
    public JTable getProductTable() { return productTable; }

    public int getproductId(){
        int selectedRow = productTable.getSelectedRow();
        if(selectedRow != -1){
            return Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        }else{
            throw new RuntimeException("No product selected");
        }
    }

    public void refreshTable(List<Product> products){
        tableModel.setRowCount(0);

        for(Product product : products){
            Object[] row = new Object[]{product.getId(), product.getName(), product.getQuantity()};
            tableModel.addRow(row);
        }
    }
}
