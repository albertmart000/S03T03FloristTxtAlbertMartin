package model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private static int ticketId = 1;

    private String code;
    private List<Product> productsTicket;

    public Ticket() {
       code = ("TICK0" + ticketId++);
       this.productsTicket = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Product> getProducts() {
        return productsTicket;
    }

    public void setProducts(List<Product> products) {
        this.productsTicket = products;
    }

    public void addProduct(Product product) {
        if(product != null){
            this.productsTicket.add(product);
        }
    }

    public double getTotalValorTicket(){
        double totalValor = 0;
        for (Product product : productsTicket) {
            totalValor += product.getPrice() * product.getQuantity();
        }
        return  totalValor;
    }



    @Override
    public String toString() {
        return "Ticket{" +
                "code='" + code + '\'' +
                ", productsTicket=" + productsTicket +
                '}';
    }
}


