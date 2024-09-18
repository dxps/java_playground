package caffeinateme;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class OrderCoffeeSteps {

    Customer cathy = new Customer();
    String cathysOrder;
    Barrista barry = new Barrista();

    @Given("Cathy is {int} meters from the coffee shop")
    public void cathy_is_n_meters_from_the_coffee_shop(Integer distanceInMeters) {

        cathy.setDistanceFromShop(distanceInMeters);
    }

    @When("Cathy orders a {string}")
    public void cathy_orders_a(String order) {
        cathysOrder = order;
        cathy.placesOrderFor(cathysOrder);
    }

    @Then("Barry should receive the order")
    public void barry_should_receive_the_order() {
        assertThat(barry.getPendingOrders(), hasItem(cathysOrder));
    }

    @Then("Barry should know that the coffee is Urgent")
    public void barry_should_know_that_the_coffee_is_urgent() {

        assertThat(barry.getUrgentOrders(), hasItem(cathysOrder));
    }

}
