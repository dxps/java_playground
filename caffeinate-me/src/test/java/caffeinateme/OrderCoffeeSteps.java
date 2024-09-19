package caffeinateme;

import caffeinateme.model.CoffeeShop;
import caffeinateme.model.Customer;
import caffeinateme.model.Order;
import caffeinateme.model.OrderStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class OrderCoffeeSteps {

    Customer cathy = new Customer("Cathy");
    String cathysOrder;
    CoffeeShop coffeeShop = new CoffeeShop();
    Order order;

    @Given("Cathy is {int} meters from the coffee shop")
    public void cathy_is_n_meters_from_the_coffee_shop(Integer distanceInMeters) {

        cathy.setDistanceFromShop(distanceInMeters);
    }

    @When("Cathy orders a {string}")
    public void cathy_orders_a(String orderedProduct) {

        this.order = Order.of(1, orderedProduct).forCustomer(cathy);
        cathy.placesAnOrderFor(order).at(coffeeShop);
    }

    @Then("Barry should receive the order")
    public void barry_should_receive_the_order() {

        assertThat(coffeeShop.getPendingOrders(), hasItem(order));
    }

    @Then("Barry should know that the order is {}")
    public void barry_should_know_that_order_is(OrderStatus expectedStatus) {

        Order cathysOrder = coffeeShop.getOrderFor(cathy).orElseThrow( () -> new AssertionError("No order found!"));

        assertThat(cathysOrder.getStatus(), is(expectedStatus));
    }

}
