package caffeinateme;

import caffeinateme.model.CoffeeShop;
import caffeinateme.model.Customer;
import caffeinateme.model.Order;
import caffeinateme.model.OrderStatus;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderCoffeeSteps {

    CoffeeShop coffeeShop = new CoffeeShop();
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

}
