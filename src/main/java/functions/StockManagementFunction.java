package functions;

import app.Input;
import model.Florist;
import model.Product;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StockManagementFunction {

    static Scanner sc = new Scanner(System.in);

    public static void showStockManagementMenu(Florist florist) {
        boolean exit = false;
        do {
            System.out.println("""
                    \033[0;4m
                    ESTOC: LLISTA DE PRODUCTES I VALOR ACTUAL.\u001B[0m
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
                case 1 -> showProductList(florist);
                case 2 -> calculateTotalValueStock(florist);
                case 0 -> exit = true;
                default -> System.out.println("Ha de ser un número entre 0 i 2.");
            }
        } while (!exit);
    }

    private static void showProductList(Florist florist) {
        if (florist.getProducts().size() == 0) {
            System.out.println("\nNo hi han productes en estoc.");
            Input.pressEnter();
        } else {
            System.out.println("\nL'actual llista de productes es:\n");
            florist.showProductListByCategory(florist.getProducts());
            Input.pressEnter();
        }
    }

    private static void calculateTotalValueStock(Florist florist) {
        double totalValueStock = 0.00;
        if (florist.getProducts().size() == 0){
            System.out.println("\nNo n`hi ha cap producte en estoc.");
        }
        for (Product product : florist.getProducts()){
            totalValueStock = totalValueStock + product.getPrice() * product.getQuantity();
        }
        System.out.println("\nEl valor total dels productes en estoc és " +
                totalValueStock + ".");
        Input.pressEnter();
    }
}
