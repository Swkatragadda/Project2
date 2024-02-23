package org.example.Tests;

import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Exception.SellerNotFoundException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class SellerProductTests {

    private ProductService productService;
    private SellerService sellerService;

    @Before

    public void setUp() {
        Connection connect = ConnectionSingleton.getConnection();
        ProductDAO productDAO = new ProductDAO(connect);
        SellerDAO sellerDAO = new SellerDAO(connect);
        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(productDAO, sellerService);
    }

    @Test
    //test to verify that getProductList returns a non-null list
    public void getProductList() {
        List<Product> productList = productService.getProductList();
        assertNotNull(productList);

    }

    @Test
    //test to add a valid product and ensure it is successfully added
    public void addProduct_ValidProduct() throws ProductException {
        //Mocking a seller and adding it to seller service
        Seller mockSeller = new Seller(1, "HomeDepot");

        sellerService.addSeller(mockSeller);
        //creating a product using the known service
        Product mockProduct = new Product(1, "Paintbox", 30.0, "HomeDepot", 1);
        //Adding a product using the service and verifying it is added successfully
        Product addedProduct = productService.addProduct(mockProduct);
        assertNotNull(addedProduct);
        assertEquals(mockProduct, addedProduct);

    }

    @Test
    //Test to add an invalid product and ensure it throws error
    public void addProduct_InvalidProduct() {
        // Creating an invalid product without required fields
        Product invalidProduct = new Product();

        //Verifying that adding an invalid product throws error
        assertThrows(ProductException.class, () -> productService.addProduct(invalidProduct));
    }

    @Test
    //Test to retrive a product by a valid ProductID
    public void getProductByID_ValidProductID() {
        //Creating a mock product and using the services to retrive it by ID
        Product mockProduct = new Product(1, "Paintbox", 40.0, "HomeDepot", 1);
        productService.getProductByID(1);

    }

    @Test
    //Test to retrieve a product with invalid productId
    public void getProductID_InvalidProductID() {
        assertThrows(Exception.class, () -> productService.getProductByID(-1));
    }

    /////////////Testcases for Seller classes////////////////////////////////

    @Test
    //test to verify that the getallseller returns a non null list
    public void addseller_validSeller() {
        //Creating a valid seller
        Seller mockSeller = new Seller(1, "Homedepot");
        //adding the seller  using the service
        sellerService.addSeller(mockSeller);
    }

    @Test
//Test to add an invalid seller with out required fields
    public void addseller_invalidSeller() {
        assertThrows(Exception.class, () -> sellerService.addSeller(new Seller()));
    }

    @Test
//Test to check if a an existing seller is correctly identified
    public void doesSellerExist() {
        //Adding a mock seller to service
        Seller mockSeller = new Seller(1, "homedepot");
        sellerService.addSeller(mockSeller);
        assertTrue(sellerService.doesSellerExist("mockSeller"));
    }

    @Test
//test to retrive a seller by a valid seller id
    public void getSellerbyId_ValidSellerID() throws SellerNotFoundException {

        Seller mockSeller = new Seller(1, "homedepot");
        sellerService.addSeller(mockSeller);
        //retrieve the seller by ID
        Seller retrievedSeller = sellerService.getSellerById(1);
        assertNotNull((retrievedSeller));
        assertEquals(mockSeller, retrievedSeller);
    }


    @Test
//Test to retrieve a invalid seller id
    public void getSellerbyId_InvalidSellerID() {
        //verifying that retrieving a seller with an invlaid ID
        assertThrows(SellerNotFoundException.class, () -> sellerService.getSellerById(-1));
    }

}


























