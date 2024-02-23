package org.example.Model;

import java.util.Objects;

public class Product {

    private long productID;
    private int sellerID;
    private String productName;
    private double price;
    private String sellerName;
    public  Product(){

    }
    public Product(long productID, String productName, double price, String sellerName,int sellerID) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.sellerName = sellerName;
        this.sellerID=sellerID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getSellerID(){
        return sellerID;
    }

    public void setSellerID(int sellerID){
        this.sellerID=sellerID;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", sellerID=" + sellerID +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID == product.productID && sellerID == product.sellerID && Double.compare(price, product.price) == 0 && Objects.equals(productName, product.productName) && Objects.equals(sellerName, product.sellerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, sellerID, productName, price, sellerName);
    }
}



