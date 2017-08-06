package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specification of car lights.
 *
 * @author vision8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CarLightsSpec {

    String value() default "white";

    LightsType type() default LightsType.STANDARD;

}
