package org.example.Model;

import java.util.Objects;

public class Seller {

    private int sellerID;
     private String sellerName;


    public Seller(){

    }

    public Seller(int sellerID,String sellerName ) {
        this.sellerID=sellerID;
        this.sellerName=sellerName;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }


    public String getSellerName() {
        return sellerName;
    }
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return sellerID == seller.sellerID && Objects.equals(sellerName, seller.sellerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerName, sellerID);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerName='" + sellerName + '\'' +
                ", sellerID=" + sellerID +
                '}';
    }


}


