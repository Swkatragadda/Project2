package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;

import java.util.List;

    public class ProductService {
        ProductDAO productDAO;
        SellerService sellerService;

        public ProductService(ProductDAO productDAO, SellerService sellerService) {
            this.productDAO = productDAO;
            this.sellerService = sellerService;
        }

        public List<Product> getProductList() {
            Main.log.info("Getting List of Products:" + productDAO);
            return productDAO.getAllProducts();
        }

        //Method to add product and check if seller exists before adding the product
        // checks if product name and seller name is not null and price is not less than 0
        public Product addProduct(Product p) throws ProductException {
            int sellerId = getSellerIdByName(p.getSellerName());

            p.setSellerID(sellerId);
            if (p.getProductName() == null || p.getSellerName() == null || p.getPrice() < 0) {
                Main.log.info("throwing product exception if product name or seller name is null  and if price is less than 0:" + p);
                throw new ProductException("Invalid product details");
            }
            if (!sellerService.doesSellerExist(p.getSellerName())) {
                throw new ProductException("Seller does not exist");
            }
            long productID = (long) (Math.random() * Long.MAX_VALUE);
            p.setProductID(productID);
            productDAO.insertProduct(p);
            return p;
        }

        //Method to get product ID from Product list
        public Product getProductByID(long productID) {
            Main.log.info("Retrieving product id :" + productDAO);
            return productDAO.getProductById(productID);
        }

    // Method to updateProduct that takes the updated attributes and iterates through the product list
    public boolean updateProduct(Product updatedProduct){
        return productDAO.updateProduct(updatedProduct);
            }


    //Method to Delete the product by ProductID
    public boolean deleteProduct(long productID){
       return productDAO.deleteProduct(productID);
    }

    private int getSellerIdByName(String sellerName) throws ProductException
    {
        List<Seller> sellers = sellerService.getSellerList();

        for(Seller seller:sellers){
            if(seller.getSellerName().equals(sellerName)){
                return seller.getSellerID();
            }
        }
        throw new ProductException("Seller with name " + sellerName + " not found");
    }
}




