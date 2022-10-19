package functions;

import app.FileManager;
import app.Input;
import model.Florist;
import model.Product;
import model.Ticket;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TicketManagementFunction {

    static Scanner sc = new Scanner(System.in);

    public static void showTicketsMenu(Florist florist) {
        boolean exit = false;
        do {
            System.out.println("""
                    \033[0;4m
                    GESTIÓ DE TICKETS i VENDES TOTALS:\u001B[0m
                    (1) Crear nou ticket.
                    (2) Mostrar l'historial de tickets.
                    (3) Vendes totals.
                    (0) Tornar al Menu de Gestió.""");
            try {
                System.out.println("\nIntrodueix el número de la teva opció.");
            } catch (InputMismatchException e) {
                System.out.println("Has de introduir un numero.");
            }
            int menuOption = sc.nextInt();
            switch (menuOption) {
                case 1 -> addTicket(florist);
                case 2 -> showTicketsList(florist);
                case 3 -> calculateTotalSales(florist);
                case 0 -> exit = true;
                default -> System.out.println("Ha de ser un numero entre 0 i 3.");
            }
        } while (!exit);
    }

    private static void calculateTotalSales(Florist florist) {
        double totalSales= 0.00;
        if (florist.getTickets().size() == 0){
            System.out.println("\nNo s'ha fet cap venda.");
        }
        for (Ticket ticket : florist.getTickets()){
            totalSales += ticket.getTotalValorTicket();
        }
        System.out.println("\nLes vendes totals fetes per la floristeria son: " +
                totalSales + ".");
        Input.pressEnter();
    }

    private static void showTicketsList(Florist florist) {
        if (florist.getTickets().size() == 0) {
            System.out.println("\nNo n'hi han tickets a la llista.");
        } else {
            System.out.println("\nL'historial de tickets es:");
            for (Ticket ticket : florist.getTickets()) {
                ticket.showTicket(ticket);
            }
        }
        Input.pressEnter();
    }

    private static void addTicket(Florist florist) {
        Ticket ticket = createTicket(florist);
        florist.getTickets().add(ticket);
        System.out.println("\nEl nou ticket es:");
        ticket.showTicket(ticket);
        FileManager.writeFile(florist.getFloristName(), florist);
        Input.pressEnter();
    }

    private static Ticket createTicket(Florist florist) {
        int ticketId = florist.getTickets().size() + 1;
        Ticket ticket = new Ticket(ticketId);
        Product clonedProduct;
        boolean buildingTicket = false;
        while (!buildingTicket) {
            Product productToAdd = selectProductToAdd(florist);
            System.out.println("\nHas escollit el producte: \n");
            productToAdd.showProduct(productToAdd);
            if (productToAdd != null) {
                int unitsProduct = askForUnitsProduct(productToAdd);
                productToAdd.setQuantity(productToAdd.getQuantity() - unitsProduct);
                try {
                    clonedProduct = (Product) productToAdd.clone();
                    clonedProduct.setQuantity(unitsProduct);
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                ticket.addProduct(clonedProduct);
                System.out.println("\nFet. S'han afegit " + unitsProduct +
                        " unitats del producte id= " + productToAdd.getProductId() + ".");
            }
            boolean addAnotherProduct = Input.askForYesOrNot("""
                    \nVols afegir un altre producte al ticket?
                    (1) Si.
                    (2) No.
                    """);
            if (!addAnotherProduct) {
                buildingTicket = true;
            }
        }
        return ticket;
    }

    private static int askForUnitsProduct(Product productToAdd) {
        boolean askingForUnits = true;
        int unitsProduct = 0;
        while (askingForUnits) {
            unitsProduct = Input.askForInt("\nIntrodueix les unitats que vols d'aquest producte:");
            if (unitsProduct > 0 && unitsProduct <= productToAdd.getQuantity()) {
                return unitsProduct;
            } else {
                System.out.println("\nNo n'hi han tantes unitats en stock.");
            }
        }
        return unitsProduct;
    }

    private static Product selectProductToAdd(Florist florist) {
        List<Product> availableProducts = buildAvailableProductList(florist);
        showAvailableProductsList(florist, availableProducts);
        boolean selectingProduct = true;
        while (selectingProduct) {
            System.out.println("\nIntrodueix el id del producte que vols afegir al ticket:");
            Scanner sc = new Scanner(System.in);
            int productIdToAdd = sc.nextInt();
            for (Product product : availableProducts) {
                if (product.getProductId() == productIdToAdd) {
                    return product;
                }
            }
            System.out.println("No n'hi ha cap producte disponible amb aquest id.");
        }
        return null;
    }

    private static void showAvailableProductsList(Florist florist, List<Product> availableProducts) {
        if (availableProducts.size() == 0) {
            System.out.println("\nNo hi han productes disponibles.");
        } else {
            System.out.println("\nEls productes disponibles son:");
            florist.showProductListByCategory(availableProducts);
        }
    }

    private static List<Product> buildAvailableProductList(Florist florist) {
        List<Product> availableProducts = new ArrayList<>();
        for (Product product : florist.getProducts()) {
            if (product.getQuantity() > 0) {
                availableProducts.add(product);
            }
        }
        return availableProducts;
    }
}
