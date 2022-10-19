import com.google.gson.annotations.Expose;

public class Product implements Cloneable{

    @Expose
    private static String TYPE_PRODUCT;
    private static int productId = 1;

    protected String code;

    @Expose
    protected String name;
    @Expose
    protected double price;
    @Expose
    protected int quantity;

    public Product() {
    }

    public Product(String TYPE_PRODUCT, String name, double price, int quantity) {
        this.TYPE_PRODUCT = TYPE_PRODUCT;
        this.code = ("PRD0" + productId++);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
     }

    public String getCode() {
          return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
          return name;
     }

    public void setName(String name) {
          this.name = name;
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

    public static String getTypeProduct() {
        return TYPE_PRODUCT;
    }

    public static void setTypeProduct(String typeProduct) {
        TYPE_PRODUCT = typeProduct;
    }

    public static int getProductId() {
        return productId;
    }

    public static void setProductId(int productId) {
        Product.productId = productId;
    }

    public Object clone() throws CloneNotSupportedException{
          return super.clone();
     }

     @Override
     public String toString() {
         return "categoria= " + TYPE_PRODUCT + "  ;  " + "codi= " + code + "  ;  " +
                 "nom= " + name +  "  ;  " + "preu= " + price + "   ;  " + "estoc= " + quantity;
     }
}
