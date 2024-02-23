package org.example.DAO;

import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {

    Connection connect;

    public SellerDAO(Connection connect) {
        this.connect = connect;
    }

    public List<Seller> getAllSeller() {

            List<Seller> sellerResults = new ArrayList<>();
        try {
            PreparedStatement ps = connect.prepareStatement("select * from Seller");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int sellerID= resultSet.getInt("sellerID");
                String sellerName = resultSet.getString("sellerName");
                Seller s = new Seller(sellerID, sellerName);
                sellerResults.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerResults;
    }


    public void insertSeller(Seller s) {
            try{
            PreparedStatement ps = connect.prepareStatement("insert into SELLER(sellerID,sellerName) values ( ?,?)");
            ps.setInt(1,s.getSellerID());
            ps.setString(2, s.getSellerName());
            System.out.println("Insert seller method " + s.getSellerID()+" " +s.getSellerName());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public Seller getSellerByID(int id){

        try{
            PreparedStatement ps = connect.prepareStatement(
                    "select * from seller where sellerID = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int sellerID = rs.getInt("sellerID");
                String sellerName = rs.getString("sellerName");
                Seller s = new Seller( sellerID,sellerName);
                System.out.println(s);
                return s;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }
}
