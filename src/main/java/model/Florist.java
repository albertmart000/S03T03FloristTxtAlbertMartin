package model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Florist {

    @Expose
    private String name;
    @Expose
    private List<Product> products;
    @Expose
    private List<Ticket> tickets;

    public Florist() {
    }

    public Florist(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
