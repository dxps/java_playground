package annotations.ex1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Testing an engine.
 *
 * @author vision8
 */
public class EngineTests {

    public static void main(String... args) throws Exception {

        Engine engine = new Engine("1234");

        Method[] engineMethods = engine.getClass().getMethods();

        for (Method engineMethod : engineMethods) {
            Annotation[] featureAnnotations = engineMethod.getDeclaredAnnotations();
            for (Annotation featureAnnotation : featureAnnotations) {
                if (featureAnnotation instanceof InitMethod) {
                    engineMethod.invoke(engine);
                }
            }
        }
    }

}
