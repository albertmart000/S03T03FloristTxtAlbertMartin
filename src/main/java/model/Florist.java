package model;

import java.util.ArrayList;
import java.util.List;

public class Florist {

    private String floristName;
    private List<Product> products;
    private List<Ticket> tickets;

    public Florist(String name) {
        this.floristName = name;
        this.products = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }

    public String getFloristName() {
        return floristName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void showProductListByCategory(List<Product> products){
        String format = "%1$-10s %2$-15s %3$-15s %4$-10s %5$-10s\n";
        String [] categories = {"Arbre", "Flor", "Decoració"};
        for (String category: categories){
            System.out.println(category.toUpperCase());
            System.out.format(format, "Id", "Producte", "Descripció", "Preu", "Unitats");
            for (Product product : products) {
                if(product.getCategory().equals(category)){
                    System.out.format(format, product.getProductId(), product.getName(),
                            product.getDescription(), product.getPrice(), product.getQuantity());
                }
            }
        }
    }
}
