package bll;

import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    ProductDAO productDAO = new ProductDAO();
    public ProductBLL(){
    }
    public Product findProductById(int id){
        Product product = productDAO.findById(id);
        if(product == null){
            throw new NoSuchElementException("The product with the id = " + id + " does not exist");
        }
        return product;
    }

    public Product findProductByName(String name){
        Product product = productDAO.findByName(name);
        if(product == null){
            throw new NoSuchElementException("The product with the name = " + name + " does not exist");
        }
        return product;
    }

    public List<Product> findAllProducts(){
        return productDAO.findAll();
    }

    public void insertProduct(Product product){
        productDAO.insert(product);
    }

    public void update(Product product){
        productDAO.update(product);
    }

    public void deleteProduct(int id){
        productDAO.delete(id);
    }

    public List<String> getAllProductNames(){
        List<String> productNames = new ArrayList<>();
        for(Product product : productDAO.findAll()){
            productNames.add(product.getName());
        }
        return productNames;
    }

    public String[][] generateTableFromProducts(){
        List<Product> products = productDAO.findAll();
        return productDAO.generateTableFromObjects(products);
    }
}
