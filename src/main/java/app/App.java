package app;

import functions.*;
import model.Florist;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppController {

    private static Florist florist;
    static Scanner sc = new Scanner(System.in);

    public AppController(){
    }

    public static void main(String[] args) {
        System.out.println("\nIntrodueix el nom de la floristeria que vols gestionar:");
        String floristName = sc.nextLine();
        if (FileManager.createFile(floristName)) {
            florist = FileManager.readFile(floristName);
            System.out.println("\nLa floristeria " + floristName + " ja existeix. S'ha carregat l'arxiu de la floristeria " +
                    floristName + ".");
        } else {
            florist = new Florist(floristName);
            FileManager.writeFile(floristName, florist);
        }
        showMainMenu(floristName);
    }

    public static void showMainMenu(String floristName) {
        boolean exit = false;
        do {
            System.out.println("\033[0;4m" + "\nMENU DE GESTIÓ DE LA FLORISTERIA " + floristName + ":\n" + "\u001B[0m" +
                               "(1) Afegir producte.\n" +
                               "(2) Retirar producte.\n" +
                               "(3) Mostrar estoc.\n" +
                               "(4) Gestió de tickets i Vendes totals.\n" +
                               "(0) Sortir");
            try {
                System.out.println("\nIntrodueix el número de la teva opció.");
            } catch (InputMismatchException e) {
                System.out.println("Has de introduir un numero.");
            }
            int menuOption = sc.nextInt();
            switch (menuOption) {
                case 1 -> AddProductFunction.addProduct(florist);
                case 2 -> RemoveProductFunction.removeProduct(florist);
                case 3 -> ShowProductListFunction.showProductListMenu(florist);
                case 4 -> TicketManagementFunction.showTicketsMenu(florist);
                case 0 -> {
                     System.out.println("Has escollit sortir: Gracies per fer servir la nostra aplicació.");
                     exit = true;
                }
                default -> System.out.println("Ha de ser un numero entre 0 i 4.");
            }
        } while (!exit);
    }

  /*  private static void showProductListMenu() {
        boolean exit = false;
        do {
            System.out.println("""
                    \033[0;4m
                    MOSTRAR ESTOC:\u001B[0m
                    (1) Mostrar llista de productes.
                    (2) Valor total de l'estoc de productes.
                    (0) Tornar al Menú de Gestió.""");
            try {
                System.out.println("\nIntrodueix el número de la teva opció.");
            } catch (InputMismatchException e) {
                System.out.println("Has de introduir un número.");
            }
            int menuOption = sc.nextInt();
            switch (menuOption) {
                case 1 -> showProductList();
                case 2 -> calculateTotalValueStock();
                case 0 -> exit = true;
                default -> System.out.println("Ha de ser un número entre 0 i 2.");
            }
        } while (!exit);
    }*/

  /*  private static void showTicketsMenu() {
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
                case 1 -> addTicket();
                case 2 -> showTicketsList();
                case 3 -> calculateTotalSales();
                case 0 -> exit = true;
                default -> System.out.println("Ha de ser un numero entre 0 i 3.");
            }
        } while (!exit);
    }

    private static void calculateTotalSales() {
        double totalSales= 0.00;
        if (florist.getTickets().size() == 0){
            System.out.println("\nNo s'ha fet cap venda.");
        }
        for (Ticket ticket : florist.getTickets()){
            totalSales += ticket.getTotalValorTicket();
        }
        System.out.println("\nLes vendes totals fetes per la floristeria son: " +
                totalSales + ".");
    }

    private static void showTicketsList() {
        if (florist.getTickets().size() == 0) {
            System.out.println("\nNo n'hi han tickets a la llista.");
        } else {
            System.out.println("\nL'actual llista de tickets es:");
            for (Ticket ticket : florist.getTickets()) {
                System.out.println(ticket);
            }
        }
    }

    private static void addTicket() {
        Ticket ticket = createTicket();
        florist.getTickets().add(ticket);
        System.out.println("\nEl nou ticket es:");
        System.out.println (ticket);
        FileManager.writeFile(florist.getFloristName(), florist);
    }

    private static Ticket createTicket() {
        Ticket ticket = new Ticket();
        //Product product = null;
        Product clonedProduct;
        boolean buildingTicket = false;
        while (!buildingTicket) {
            Product productToAdd = selectProductToAdd();
            System.out.println("\nHas escollit el producte: \n" + productToAdd);
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
            if (addAnotherProduct) {
                buildingTicket = false;
            } if (!addAnotherProduct) {
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

    private static Product selectProductToAdd() {
        List<Product> availableProducts = buildAvailableProductList();
        showAvailableProductsList(availableProducts);
        Product productToAdd = null;
        boolean selectingProduct = true;
        while (selectingProduct) {
            System.out.println("\nIntrodueix el codi del producte que vols afegir al ticket:");
            Scanner sc = new Scanner(System.in);
            int productIdToAdd = sc.nextInt();
            for (Product product : availableProducts) {
                if (product.getProductId() == productIdToAdd) {
                    return product;
                }
            }
            System.out.println("No n'hi ha cap producte disponible amb aquest codi.");
        }
        return null;
    }

    private static void showAvailableProductsList(List<Product> availableProducts) {
        if (availableProducts.size() == 0) {
            System.out.println("\nNo hi han productes disponibles.");
        } else {
            System.out.println("\nEls productes disponibles son:");
            for (Product product : availableProducts) {
                System.out.println(product);
            }
        }
    }

    private static List<Product> buildAvailableProductList() {
        List<Product> availableProducts = new ArrayList<>();
        for (Product product : florist.getProducts()) {
            if (product.getQuantity() > 0) {
                availableProducts.add(product);
            }
        }
        return availableProducts;
    }*/

  /*  private static void showProductList() {
        if (florist.getProducts().size() == 0) {
            System.out.println("\nNo hi han productes en estoc.");
        } else {
            System.out.println("\nL'actual llista de productes es:");
            for (Product product : florist.getProducts()) {
                System.out.println(product);
            }
        }
    }

    private static void calculateTotalValueStock() {
        double totalValueStock = 0.00;
        if (florist.getProducts().size() == 0){
            System.out.println("\nNo n`hi ha cap producte en estoc.");
            }
        for (Product product : florist.getProducts()){
            totalValueStock = totalValueStock + product.getPrice() * product.getQuantity();
        }
        System.out.println("\nEl valor total dels productes en estoc és " +
                totalValueStock + ".");
    }*/

   /* private static void removeProduct() {
        Product productToRemove = selectProductToRemove();
        florist.getProducts().remove(productToRemove);
        System.out.println("\nFet. Producte eliminat.");
        FileManager.writeFile(florist.getFloristName(), florist);
    }

    private static Product selectProductToRemove() {
        if (florist.getProducts().size() == 0) {
            System.out.println("\nNo hi han productes per eliminar.");
        } else {
            boolean askingForProductId = true;
            while (askingForProductId) {
                System.out.println("\nIntrodueix el codi del producte que vols eliminar:");
                Scanner sc = new Scanner(System.in);
                int productIdToRemove = sc.nextInt();
                for (Product product : florist.getProducts()) {
                    if (product.getProductId() == productIdToRemove) {
                        return product;
                    }
                }
                System.out.println("No n'hi ha cap producte per eliminar amb aquest codi.");
            }
        } return  null;
    }*/

   /* public static void addProduct() {
        Product product = createProduct();
        florist.getProducts().add(product);
        System.out.println("\nFet. S'ha afegit el producte:");
        System.out.println (product);
        FileManager.writeFile(florist.getFloristName(), florist);
    }

    private static Product createProduct() {
        int typeProduct = askForTypeProduct();
        String name = Input.askForString("\nIntrodueix el nom del producte:");
        double price = Input.askForDouble("\nIntrodueix el preu del producte:");
        int quantity = Input.askForInt("\nIntrodueix la quantitat del producte:");
        int productId = generateIdProduct();

        Product product = null;
        if (typeProduct == 1) {
            String category = "Arbre";
            String height = Input.askForString("\nIntrodueix l'alçada de l'arbre (en metres):");
            String characteristic = (height + " metres.");
            product = new Product(category, productId, name, characteristic, price, quantity);
        } else if (typeProduct == 2){
            String category = "Flor";
            String color = Input.askForString("\nIntrodueix el color de la flor:");
            String characteristic = ("color " + color);
            product= new Product(category, productId, name, characteristic, price, quantity);
        } else if (typeProduct == 3){
            String category = "Decoració";
            String characteristic= askForTypeMaterial();
            product = new Product(category, productId, name, characteristic, price, quantity);
        }
        return product;
    }

    private static String askForTypeMaterial() {
        int typeMaterial = Input.askForInt("""
            \nIntrodueix el tipus de material:
            (1) Fusta.
            (2) Plastic.""");
        String characteristic = null;
        if (typeMaterial == 1) {
            characteristic = "fusta";
        } else if (typeMaterial == 2) {
            characteristic = "plastic";
        }
        return characteristic;
    }

    private static int generateIdProduct() {
        int productId = 0;
        if (florist.getProducts().size() == 0) {
            productId = 1;
        } else if (florist.getProducts().size() > 0) {
        int lastIndexProduct = florist.getProducts().size() - 1;
        int idLastProduct = florist.getProducts().get(lastIndexProduct).getProductId();
            productId = idLastProduct + 1;
        }
        return productId;
    }

    private static int askForTypeProduct() {
        return Input.askForInt("""
            \nIntrodueix el tipus de producte que vols afegir:
            (1) Arbre.
            (2) Flor.
            (3) Decoració.""");
    }*/
}