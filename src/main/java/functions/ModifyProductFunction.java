package functions;

import app.FileManager;
import app.Input;
import model.Florist;
import model.Product;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ModifyProductFunction {

    static Scanner sc = new Scanner(System.in);

    public static void showModifyProductMenu(Florist florist) {
        boolean exit = false;
        do {
            System.out.println("""
                    \033[0;4m
                    ACTUALITZAR PRODUCTE.\u001B[0m
                    (1) Modificar el preu d'un producte.
                    (2) Afegir noves unitats a un producte de l'estoc.
                    (0) Tornar al Menú de Gestió.""");
            try {
                System.out.println("\nIntrodueix el número de la teva opció.");
            } catch (InputMismatchException e) {
                System.out.println("Has de introduir un número.");
            }
            int menuOption = sc.nextInt();
            switch (menuOption) {
                case 1 -> setPriceProduct(florist);
                case 2 -> setQuantityProduct(florist);
                case 0 -> exit = true;
                default -> System.out.println("Ha de ser un número entre 0 i 2.");
            }
        } while (!exit);
    }

    private static void setPriceProduct(Florist florist) {
        Product productToModify = selectProductToModify(florist);
        System.out.println("El preu actual del producte escollit és: " +
                productToModify.getPrice());
        int newPrice= Input.askForInt("\nQuin és el nou preu?");
        productToModify.setPrice(newPrice);
        System.out.println("Fet. Has canviat el preu del producte.");
        productToModify.showProduct(productToModify);
        FileManager.writeFile(florist.getFloristName(), florist);
        Input.pressEnter();
    }

    private static void setQuantityProduct(Florist florist) {
        Product productToModify = selectProductToModify(florist);
        System.out.println("L'estoc actual del producte escollit és: " +
                productToModify.getQuantity());
        int unitsToAdd = Input.askForInt("\nQuantes unitats vols afegir?");
        productToModify.setQuantity(productToModify.getQuantity() + unitsToAdd);
        System.out.println("\nFet. Has afegit " + unitsToAdd +
                " unitats a l'estoc del producte.");
        productToModify.showProduct(productToModify);
        FileManager.writeFile(florist.getFloristName(), florist);
        Input.pressEnter();
    }

    private static Product selectProductToModify(Florist florist) {
        if (florist.getProducts().size() == 0) {
            System.out.println("\nNo hi han productes a la llista.");
        } else {
            boolean askingForProductId = true;
            while (askingForProductId) {
                System.out.println("\nIntrodueix el id del producte que vols actualitzar:");
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
}
