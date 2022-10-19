package functions;

import app.FileManager;
import app.Input;
import model.Florist;
import model.Product;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StockManegementFunction {

    static Scanner sc = new Scanner(System.in);

    public static void showProductListMenu(Florist florist) {
        boolean exit = false;
        do {
            System.out.println("""
                    \033[0;4m
                    GESTIÓ DE L'ESTOC:\u001B[0m
                    (1) Mostrar llista de productes.
                    (2) Afegir noves unitats a un producte de l'estoc.
                    (3) Valor total de l'estoc de productes.
                    (0) Tornar al Menú de Gestió.""");
            try {
                System.out.println("\nIntrodueix el número de la teva opció.");
            } catch (InputMismatchException e) {
                System.out.println("Has de introduir un número.");
            }
            int menuOption = sc.nextInt();
            switch (menuOption) {
                case 1 -> showProductList(florist);
                case 2 -> setQuantityProduct(florist);
                case 3 -> calculateTotalValueStock(florist);
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
            ShowProduct.showProductListByCategory(florist.getProducts());
            Input.pressEnter();
        }
    }

    private static void setQuantityProduct(Florist florist) {
        Product productToModify = selectProductToModify(florist);
        int unitsToAdd = Input.askForInt("Quantes unitats vols afegir?");
        productToModify.setQuantity(productToModify.getQuantity() + unitsToAdd);
        System.out.println("\nFet. Unitats afegides");
        FileManager.writeFile(florist.getFloristName(), florist);
    }

    private static Product selectProductToModify(Florist florist) {
        if (florist.getProducts().size() == 0) {
            System.out.println("\nNo hi han productes a la llista.");
        } else {
            boolean askingForProductId = true;
            while (askingForProductId) {
                System.out.println("\nIntrodueix el id del producte al que vols afegir més unitats:");
                Scanner sc = new Scanner(System.in);
                int productIdToModify = sc.nextInt();
                for (Product product : florist.getProducts()) {
                    if (product.getProductId() == productIdToModify) {
                        return product;
                    }
                }
                System.out.println("No n'hi ha cap producte amb aquest id.");
            }
        } return  null;
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
