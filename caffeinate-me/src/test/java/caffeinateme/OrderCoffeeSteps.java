package caffeinateme;

import caffeinateme.model.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderCoffeeSteps {

    ProductCatalog catalog = new ProductCatalog();
    CoffeeShop coffeeShop = new CoffeeShop(catalog);
    Customer customer;
    Order order;

    @Given("{} is a CaffeinateMe customer")
    public void a_caffeinate_me_customer_named(String customerName) {
        customer = coffeeShop.registerNewCustomer(customerName);
    }

    @Given("Cathy is {int} meters from the coffee shop")
    public void cathy_is_n_meters_from_the_coffee_shop(Integer distanceInMeters) {
        customer.setDistanceFromShop(distanceInMeters);
    }

    @ParameterType("\"[^\"]*\"")
    public Order order(String orderedProduct) {
        return Order.of(1, orderedProduct).forCustomer(customer);
    }

    @When("Cathy orders a {order}")
    public void cathy_orders_a(Order order) {
        this.order = order;
        customer.placesAnOrderFor(order).at(coffeeShop);
    }

    @When("Cathy orders a {order} with a comment {string}")
    public void cathy_orders_with_comment(Order order, String comment) {
        this.order = order.withComment(comment);
        customer.placesAnOrderFor(order).at(coffeeShop);
    }

    @Then("Barry should receive the order")
    public void barry_should_receive_the_order() {
        assertThat(coffeeShop.getPendingOrders()).contains(order);
    }

    @Then("Barry should know that the order is {}")
    public void barry_should_know_that_order_is(OrderStatus expectedStatus) {
        Order cathysOrder = coffeeShop.getOrderFor(customer)
                .orElseThrow( () -> new AssertionError("No order found!"));
        assertThat(cathysOrder.getStatus()).isEqualTo(expectedStatus);
    }

    @And("the order should have the comment {string}")
    public void theOrderShouldHaveTheComment(String comment) {
        assertThat(order.getComment()).isEqualTo(comment);
    }

    @DataTableType
    public OrderItem orderItem(Map<String, String> row) {
        return new OrderItem(row.get("Product"), Integer.parseInt(row.get("Quantity")));
    }

    // These two are left as FYI. The one-liner alternative below uses a regex.
    // @Given("Cathy has placed an order for the following items:")
    // @When("Cathy places an order for the following items:")
    @When("^Cathy (?:has placed|places) an order for the following items:")
    public void cathyPlacesAnOrderForTheFollowingItems(List<OrderItem> items) {
        this.order = new Order(items, customer);
        coffeeShop.placeOrder(order);
    }

    @And("the order should contain {int} line items")
    public void theOrderShouldContainLineItems(int expectedNumberOfLineItems) {
        var order = coffeeShop.getOrderFor(customer).get();
        assertThat(order.getItems()).hasSize(expectedNumberOfLineItems);
    }

    @And("the order should contain the following products:")
    public void order_should_contain_following_products(List<String> expectedProducts) {
        var order = coffeeShop.getOrderFor(customer).get();
        List<String> productItems = order.getItems().stream()
                .map(OrderItem::product)
                .collect(Collectors.toList());
        assertThat(productItems).hasSameElementsAs(expectedProducts);
    }

    @DataTableType
    public ProductPrice productPrice(Map<String, String> row) {
        return new ProductPrice(row.get("Product"), Double.parseDouble(row.get("Price")));
    }

    @Given("the following prices:")
    public void theFollowingPrices(List<ProductPrice> productPrices) {
        catalog.addProductsWithPrices(productPrices);
    }

    Receipt receipt;

    @When("he/she asks for a receipt")
    public void sheAsksForAReceipt() {
        receipt = coffeeShop.getReceiptFor(customer);
    }

}
