package caffeinateme.model;

import java.util.*;

public class CoffeeShop {

    private static final int MAX_DISTANCE = 10000;

    private final Queue<Order> orders = new LinkedList<>();
    private Map<String, Customer> registeredCustomers = new HashMap<>();
    private final ProductCatalog productCatalog;

    public CoffeeShop(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public void placeOrder(Order order) {
        placeOrder(order, MAX_DISTANCE);
    }

    public void placeOrder(Order order, int distanceInMetres) {
        if (distanceInMetres <= 200) {
            order = order.withStatus(OrderStatus.Urgent);
        } else if (distanceInMetres >= 1000) {
            order = order.withStatus(OrderStatus.Low);
        }
        orders.add(order);
    }

    public List<Order> getPendingOrders() {
        return new ArrayList<>(orders);
    }

    public Optional<Order> getOrderFor(Customer customer) {
        return orders.stream()
                .filter(order -> order.getCustomer().equals(customer))
                .findFirst();
    }

    public Customer registerNewCustomer(String customerName) {
        return new Customer(customerName);
    }

}
