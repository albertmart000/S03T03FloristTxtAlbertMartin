package functions;

import app.FileManager;
import model.Florist;
import model.Product;

import java.util.Scanner;

public class RemoveProductFunction {

    public static void removeProduct(Florist florist) {
        Product productToRemove = selectProductToRemove(florist);
        florist.getProducts().remove(productToRemove);
        System.out.println("\nFet. Producte eliminat.");
        FileManager.writeFile(florist.getFloristName(), florist);
    }

    private static Product selectProductToRemove(Florist florist) {
        if (florist.getProducts().size() == 0) {
            System.out.println("\nNo hi han productes per eliminar.");
        } else {
            boolean askingForProductId = true;
            while (askingForProductId) {
                System.out.println("\nIntrodueix el id del producte que vols eliminar:");
                Scanner sc = new Scanner(System.in);
                int productIdToRemove = sc.nextInt();
                for (Product product : florist.getProducts()) {
                    if (product.getProductId() == productIdToRemove) {
                        return product;
                    }
                }
                System.out.println("No n'hi ha cap producte per eliminar amb aquest id.");
            }
        } return  null;
    }
}
