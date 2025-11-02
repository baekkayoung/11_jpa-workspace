package hellojpa.dto;

public class OrderProductDTO {
    private int orderAmount;
    private String productName;

    public OrderProductDTO(int orderAmount, String productName) {
        this.orderAmount = orderAmount;
        this.productName = productName;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderAmount=" + orderAmount +
                ", productName='" + productName + '\'' +
                '}';
    }
}