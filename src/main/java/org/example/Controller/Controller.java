package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.Exception.ProductException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Exception.SellerNotFoundException;

import java.util.List;

public class Controller {

    ProductService productService;
    SellerService sellerService;

    public Controller(ProductService productService, SellerService sellerService) {
        this.productService = productService;
        this.sellerService = sellerService;
    }

        //Configure Javalin to create Restful endpoints.
    public Javalin getAPI() {
        Javalin api = Javalin.create();
        /*  Check the health of the server */
        api.get("/health/", context -> {
            context.result("the server is UP");
        });
        //endpoint to retrieve all products.
        api.get("/product/", context -> {
            List<Product> productList = productService.getProductList();
            context.json(productList);
        });
        // end point to retrieve all sellers
        api.get("seller/", context -> {
            List<Seller> sellerList = sellerService.getAllSeller();
            System.out.println("get method from controller " + sellerList);
            context.json(sellerList);
        });
        //Endpoint to retrieve a specific product by ID.
        api.get("product/{ProductID}", context -> {
            long id = Long.parseLong(context.pathParam("ProductID"));
            Product p = productService.getProductByID(id);
            if (p == null) {
                context.status(404);
            } else {
                context.json(p);
                context.status(200);
            }
        });
        //Endpoint to retrieve Seller ID
        api.get("sellerID/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            try{
                Seller s = sellerService.getSellerById(id);
                context.json(s);
            }catch (SellerNotFoundException e){
                context.status(404);
            }
        });


        //Endpoint to add a new product and also checking whether the seller exists before creating a product
        api.post("product/", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Product p = om.readValue(context.body(),Product.class);
                if(!sellerService.doesSellerExist(p.getSellerName())){
                    context.status(400);
                    context.result("Seller does not exist");
                    return;
                }
                Product newProduct= productService.addProduct(p);
                Main.log.info(String.valueOf(newProduct));
                context.status(201);
                context.json(newProduct);
            }catch (JsonProcessingException e){
                context.status(400);
            }catch(ProductException e){
                context.result(e.getMessage());
                context.status(400);
            }
        });
        //Endpoint to add a new seller
        api.post("seller/",context -> {
            try{
                ObjectMapper om = new ObjectMapper();
                Seller s = om.readValue(context.body(),Seller.class);
                sellerService.addSeller(s);
                System.out.println("controller " + s);
                context.status(201);

            }catch(JsonProcessingException e){
                context.status(400);
            }
        });


        //Endpoint to update a product by using Product ID
        api.put("product/{ProductID}",context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Product updatedProduct = om.readValue(context.body(), Product.class);
                boolean updated = productService.updateProduct(updatedProduct);
                if (updated) {
                    context.status(200);
                    context.result("Product updated successfully");
                } else {
                    context.status(404);
                    context.result("Product not found");
                }
            } catch (JsonProcessingException e) {
                context.status(400);
                context.result("Error Processing Json data ");
            }
        });

        //Endpoint to delete a product
        api.delete("product/{ProductID}", ctx->{
            try {
                long id = Long.parseLong(ctx.pathParam("ProductID"));
                boolean deleted = productService.deleteProduct((id));
                    if(deleted) {
                        ctx.status(200);
                        ctx.result("Product deleted successfully");
                    }else{
                        ctx.status(404);
                        ctx.result("Product not found");
                    }
            } catch (NumberFormatException e){
                ctx.status(400);
                ctx.result( "Error parsing the product ID");

            }
        });

        return api;
    }
}
