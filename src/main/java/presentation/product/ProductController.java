package presentation.product;

import bll.ProductBLL;
import model.Product;

import java.util.List;
/**
 * Controller class for managing products in the presentation layer.
 */
public class ProductController {
    /** The view for managing products. */
    private ProductView productView;
    /** The bll for managing products. */
    private ProductBLL productBll;

    /**
     * Constructs a new ProductController object.
     *
     * @param productView The view for managing products.
     * @param productBll The bll for managing products.
     */
    public ProductController(ProductView productView, ProductBLL productBll) {
        this.productView = productView;
        this.productBll = productBll;

        productView.getAddProductButton().addActionListener(e -> addProduct());
        productView.getEditProductButton().addActionListener(e -> editProduct());
        productView.getDeleteProductButton().addActionListener(e -> deleteProduct());

        refreshTable();
    }

    /**
     * Adds a new product to the database.
     */
    private void addProduct() {
        int id = Integer.parseInt(productView.getProductIdField().getText());
        String name = productView.getProductNameField().getText();
        int quantity = Integer.parseInt(productView.getQuantityField().getText());
        Product product = new Product(id, name, quantity);
        productBll.insertProduct(product);
        productView.getProductIdField().setText("");
        productView.getProductNameField().setText("");
        productView.getQuantityField().setText("");
        refreshTable();
    }

    /**
     * Edits an existing product in the system.
     */
    private void editProduct() {
        int id = productView.getproductId();
        String name = productView.getProductNameField().getText();
        int quantity = Integer.parseInt(productView.getQuantityField().getText().trim());

        Product product = new Product(id, name, quantity);
        productBll.update(product);

        productView.getProductNameField().setText("");
        productView.getQuantityField().setText("");

        refreshTable();
    }

    /**
     * Deletes an existing product from the system.
     */
    private void deleteProduct() {
        int row = productView.getProductTable().getSelectedRow();
        if(row >= 0){
            int id = Integer.parseInt(productView.getProductTable().getValueAt(row, 0).toString());
            productBll.deleteProduct(id);

            refreshTable();
        }else{
            System.out.println("No row selected");
        }
    }

    /**
     * Refreshes the product table with updated data.
     */
    private void refreshTable(){
        List<Product> products = productBll.findAllProducts();
        productView.generateTableFromObjects(products);
    }
}
