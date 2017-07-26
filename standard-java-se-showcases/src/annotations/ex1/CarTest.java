package annotations.ex1;

/**
 * Simple test of a Car.
 */
public class CarTest {

    public static void main(String... args) throws Exception {

        Car car = new Car("1234");

        if (car.initEngine())
            System.out.println(">>> COOL! It started!");
        else
            System.out.println(">>> " + car);
    }

}
