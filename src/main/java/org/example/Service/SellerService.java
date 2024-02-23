package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exception.SellerNotFoundException;
import org.example.Model.Seller;

import java.util.List;

public class SellerService {

    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO){
        this.sellerDAO=sellerDAO;
    }

    public List<Seller>getAllSeller(){

        return sellerDAO.getAllSeller();
    }

    public void addSeller(Seller s){
        sellerDAO.insertSeller(s);
        System.out.println(s);
    }

    //Method to check if a seller with given name exists in the sellerlist
    public boolean doesSellerExist(String sellerName){
        List<Seller>sellers =getAllSeller();
        return sellers.stream().anyMatch(s-> s.getSellerName().equals(sellerName));

    }

    public Seller getSellerById(int id) throws SellerNotFoundException {
        Seller s = sellerDAO.getSellerByID(id);
        if(s == null){
            throw new SellerNotFoundException("no seller with such id found");
        }else{
            return s;
        }
    }

    public List<Seller> getSellerList() {
        return sellerDAO.getAllSeller();
    }
}
