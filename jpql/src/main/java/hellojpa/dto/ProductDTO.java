package hellojpa.dto;

public class ProductDTO {

    private String name;
    private int price;

    public ProductDTO(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return "ProductDTO{name='" + name + "', price=" + price + "}";
    }

}
