package annotations.tbd;

import java.lang.annotation.Annotation;

/**
 * Testing some cars ...
 *
 * @author vision8
 */
public class CarTests {

    @CarLightsSpec("yellow")
    private static Car car1;

    @CarLightsSpec(type = LightsType.XENON)
    private static Car car2;

    public static void main(String... args) {


        car1 = new Car();
        car2 = new Car();

        processCarLightsSpec(car1);
        car1.processCarLightsSpec();

        System.out.println(car1);
        System.out.println(car2);
    }

    static void processCarLightsSpec(Car car) {

        Annotation annotation = car.getClass().getAnnotation(CarLightsSpec.class);
        System.out.println(" CarTests: annotation applied: " + annotation);
    }

}
