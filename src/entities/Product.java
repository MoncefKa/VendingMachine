package entities;


public class Product {

    private Integer ref;
    private String name;
    private Double price;
    private Integer quantity;

    public Product(String name, Double price, Integer quantity,Integer ref) {
        this.ref=ref;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getRef(){
        return ref;
    }
    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

}
