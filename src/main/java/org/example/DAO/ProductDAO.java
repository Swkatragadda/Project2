package org.example.DAO;

import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.SellerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class ProductDAO {

    Connection connect;

    public ProductDAO(Connection connect) {
        this.connect = connect;
    }

    public void insertProduct(Product p) {

        try {
            PreparedStatement ps = connect.prepareStatement("insert into PRODUCT" +
                    " (productID, productName,price,sellerID,sellerName) " +
                    "values (?, ?, ?, ?,?)");
            ps.setLong(1, p.getProductID());
            ps.setString(2, p.getProductName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4,p.getSellerID());
            ps.setString(5, p.getSellerName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Product> getAllProducts() {
        List<Product> resultPaintings = new ArrayList<>();
        try {
            PreparedStatement ps = connect.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productID = rs.getLong("productID");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                int sellerID = rs.getInt("sellerID");
                String sellerName = rs.getString("sellerName");
                Product p = new Product(productID, productName, price, sellerName, sellerID);
                resultPaintings.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultPaintings;
    }


    public Product getProductById(long id) {
        try {
            PreparedStatement ps = connect.prepareStatement("select * from Product where productID = ?");
           ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
              long productID = rs.getLong("productID");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                int sellerID = rs.getInt("sellerID");
                String sellerName = rs.getString("sellerName");
                Product p = new Product(productID, productName, price, sellerName, sellerID);
                System.out.println(p);
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProduct(Product updatedProduct) {
        try {
            PreparedStatement ps = connect.prepareStatement("update PRODUCT set productName=?,price=?,sellerName=? where productID=? ");
            ps.setString(1, updatedProduct.getProductName());
            ps.setDouble(2, updatedProduct.getPrice());
            ps.setString(3, updatedProduct.getSellerName());
            ps.setLong(4, updatedProduct.getProductID());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean deleteProduct(long productID) {
        try {
            PreparedStatement ps = connect.prepareStatement("delete from PRODUCT where productID =?");
            ps.setLong(1,productID);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}




