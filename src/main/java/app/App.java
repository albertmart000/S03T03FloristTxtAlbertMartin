package app;

import functions.*;
import model.Florist;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static Florist florist;
    static Scanner sc = new Scanner(System.in);

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
                               "(3) Actualitzar producte.\n" +
                               "(4) Estoc: Llista de productes i Valor actual.\n" +
                               "(5) Gestió de tickets i Vendes totals.\n" +
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
                case 3 -> ModifyProductFunction.showModifyProductMenu(florist);
                case 4 -> StockManagementFunction.showStockManagementMenu(florist);
                case 5 -> TicketManagementFunction.showTicketsMenu(florist);
                case 0 -> {
                     System.out.println("Has escollit sortir: Gracies per fer servir l'aplicació.");
                     exit = true;
                }
                default -> System.out.println("Ha de ser un numero entre 0 i 4.");
            }
        } while (!exit);
    }
}