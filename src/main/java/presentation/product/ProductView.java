package presentation.product;

import model.Product;
import presentation.order.OrderView;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The view for managing products, including adding, editing, and deleting products.
 */
public class ProductView extends JFrame {
    /** Text field for entering product ID. */
    private JTextField productIdField = new JTextField(5);
    /** Text field for entering product name. */
    private JTextField productNameField = new JTextField(20);
    /** Text field for entering product quantity. */
    private JTextField quantityField = new JTextField(20);
    /** Button for adding a new product. */
    private JButton addProductButton = new JButton("Add Product");
    /** Button for editing an existing product. */
    private JButton editProductButton = new JButton("Edit Product");
    /** Button for deleting an existing product. */
    private JButton deleteProductButton = new JButton("Delete Product");
    /** Table for displaying product information. */
    private JTable productTable;
    /** Table model for the product table. */
    private DefaultTableModel tableModel;

    public ProductView(){
        JScrollPane tableScrollPane = new JScrollPane();
        productTable = new JTable();
        tableScrollPane.setViewportView(productTable);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Products");
        setSize(700, 500);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        add(new JLabel("Product ID: "));
        add(productIdField);

        add(new JLabel("Product Name:"));
        add(productNameField);

        add(new JLabel("Quantity:"));
        add(quantityField);

        add(addProductButton);
        add(editProductButton);
        add(deleteProductButton);

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

    public JTextField getProductIdField() {return productIdField;}
    public JTextField getProductNameField() { return productNameField; }
    public JTextField getQuantityField() { return quantityField; }
    public JButton getAddProductButton() { return addProductButton;}
    public JButton getEditProductButton() { return editProductButton; }
    public JButton getDeleteProductButton() { return deleteProductButton; }
    public JTable getProductTable() { return productTable; }

    /**
     * Gets the ID of the selected product from the product table.
     *
     * @return The ID of the selected product.
     * @throws RuntimeException if no product is selected.
     */
    public int getproductId(){
        int selectedRow = productTable.getSelectedRow();
        if(selectedRow != -1){
            return Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        }else{
            throw new RuntimeException("No product selected");
        }
    }

    /**
     * Generates a table from a list of objects.
     *
     * @param objects The list of objects to populate the table with.
     */
    public void generateTableFromObjects(List<?> objects) {
        if (objects != null && !objects.isEmpty()) {
            Class<?> objClass = objects.get(0).getClass();
            Field[] fields = objClass.getDeclaredFields();
            String[] columnNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                columnNames[i] = fields[i].getName();
            }
            tableModel = new DefaultTableModel(columnNames, 0);
            productTable.setModel(tableModel);

            for (Object obj : objects) {
                Object[] rowData = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    try {
                        rowData[i] = fields[i].get(obj);
                    } catch (IllegalAccessException e) {
                        Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                tableModel.addRow(rowData);
            }
        }
    }
}
