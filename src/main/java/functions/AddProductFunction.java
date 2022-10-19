package functions;

import app.FileManager;
import app.Input;
import model.Florist;
import model.Product;

public class AddProductFunction {

    public static void addProduct(Florist florist) {
        Product product = createProduct(florist);
        florist.getProducts().add(product);
        System.out.println("\nFet. S'ha afegit el producte:" );
        product.showProduct(product);
        FileManager.writeFile(florist.getFloristName(), florist);
    }

    private static Product createProduct(Florist florist) {
        int typeProduct = askForTypeProduct();
        String name = Input.askForString("\nIntrodueix el nom del producte:");
        double price = Input.askForDouble("\nIntrodueix el preu del producte:");
        int quantity = Input.askForInt("\nIntrodueix la quantitat del producte:");
        int productId = generateIdProduct(florist);

        Product product = null;
        if (typeProduct == 1) {
            String category = "Arbre";
            String height = Input.askForString("\nIntrodueix l'alçada de l'arbre (en metres):");
            String description = (height + " metres.");
            product = new Product(category, productId, name, description, price, quantity);
        } else if (typeProduct == 2){
            String category = "Flor";
            String color = Input.askForString("\nIntrodueix el color de la flor:");
            String description = ("color " + color);
            product= new Product(category, productId, name, description, price, quantity);
        } else if (typeProduct == 3){
            String category = "Decoració";
            String description = askForTypeMaterial();
            product = new Product(category, productId, name, description, price, quantity);
        }
        return product;
    }

    private static String askForTypeMaterial() {
        int typeMaterial = Input.askForInt("""
            \nIntrodueix el tipus de material:
            (1) Fusta.
            (2) Plàstic.""");
        String characteristic = null;
        if (typeMaterial == 1) {
            characteristic = "fusta";
        } else if (typeMaterial == 2) {
            characteristic = "plàstic";
        }
        return characteristic;
    }

    private static int generateIdProduct(Florist florist) {
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
    }
}
