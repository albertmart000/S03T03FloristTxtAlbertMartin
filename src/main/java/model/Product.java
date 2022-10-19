package model;

public class Product implements Cloneable{

    private String category;
    private int productId;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product(String category, int productId, String name, String description, double price, int quantity) {
        this.category = category;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
     }

    public String getCategory() {
        return category;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object clone() throws CloneNotSupportedException{
          return super.clone();
    }

    public void showProduct(Product product){
        String format = "%1$-10s %2$-15s %3$-15s %4$-10s %5$-10s\n";
        System.out.println(product.getCategory().toUpperCase());
        System.out.format(format, "Id", "Producte", "Descripció", "Preu", "Unitats");
        System.out.format(format, product.getProductId(), product.getName(),
                product.getDescription(), product.getPrice(), product.getQuantity());
    }

    @Override
    public String toString() {
         return "categoria= " + category + "  ;  " + "id= " + productId + "  ;  " +
                 "nom= " + name +  "  ;   " + "descripció= " + description + "  ;  " +
                 "preu= " + price + "   ;  " + "unitats= " + quantity;
    }
}
