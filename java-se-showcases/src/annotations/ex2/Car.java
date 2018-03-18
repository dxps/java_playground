package annotations.ex2;

import java.lang.annotation.Annotation;

/**
 * @author vision8
 */
public class Car {

    private Lights lights;

    public void processCarLightsSpec() {

        Annotation annotation = this.getClass().getAnnotation(CarLightsSpec.class);
        System.out.println(" Car: annotation applied: " + annotation);
    }

    @Override
    public String toString() {
        return String.format("Car { lights='%s' }", lights);
    }

}
