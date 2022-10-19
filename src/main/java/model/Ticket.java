package model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private int ticketId;
    private List<Product> productsTicket;

    public Ticket(int ticketId) {
       this.ticketId = ticketId;
       this.productsTicket = new ArrayList<>();
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

    public void showTicket (Ticket ticket){
        System.out.println("\nTICKET Nº " + ticketId + ":\n" + "Total: " + getTotalValorTicket() + "\nProductes:");
        showProductsTickets(productsTicket);

    }
    public void showProductsTickets(List<Product> productsTicket){
        String format = "%1$-15s %2$-10s %3$-15s %4$-15s %5$-10s %6$-10s\n";
        System.out.format("\033[0;4m" + format,  "Categoria", "Id", "Producte", "Descripció", "Preu", "Unitats" + "\u001B[0m" );
        for (Product product : productsTicket) {
            System.out.format(format, product.getCategory(), product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
        }
    }

    @Override
    public String toString() {
        return "Ticket Número " + ticketId + ":\n" +
                "Valor Total Ticket= " + getTotalValorTicket() +
                ", productsTicket=" + productsTicket;
    }
}


