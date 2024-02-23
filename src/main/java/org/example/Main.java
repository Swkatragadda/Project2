package org.example;

import io.javalin.Javalin;
import org.example.Controller.Controller;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    //instantiation of logger for logging
    public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {


//        instantiate & inject all dependencies of my project

        Connection connect = ConnectionSingleton.getConnection();
        SellerDAO sellerDAO=new SellerDAO(connect);
        ProductDAO productDAO= new ProductDAO(connect);
        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService =new ProductService(productDAO,sellerService);
        Controller controller =new Controller(productService, sellerService);


        Javalin api= controller.getAPI();
        api.start(9004);
    }

}