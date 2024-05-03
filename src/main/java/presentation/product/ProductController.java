package presentation.product;

import bll.ProductBLL;
import model.Product;

import java.util.List;

public class ProductController {
    private ProductView productView;
    private ProductBLL productBll;

    public ProductController(ProductView productView, ProductBLL productBll) {
        this.productView = productView;
        this.productBll = productBll;

        productView.getAddProductButton().addActionListener(e -> addProduct());
        productView.getEditProductButton().addActionListener(e -> editProduct());
        productView.getDeleteProductButton().addActionListener(e -> deleteProduct());

        refreshTable();
    }

    private void addProduct() {
        String name = productView.getProductNameField().getText();
        int quantity = Integer.parseInt(productView.getQuantityField().getText());
        Product product = new Product(name, quantity);
        productBll.insertProduct(product);
        productView.getProductNameField().setText("");
        productView.getQuantityField().setText("");
        refreshTable();
    }

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

    private void refreshTable(){
        List<Product> products = productBll.findAllProducts();
        productView.refreshTable(products);
    }
}
