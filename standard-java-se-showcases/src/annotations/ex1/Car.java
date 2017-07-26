package annotations.ex1;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Testing an engine.
 */
public class Car {

    /** the engine of the car */
    private Engine engine;

    private String engineInitCallError;

    /** Create a new car with an engine whose id is provided. */
    public Car(String engineId) {
        this.engine = new Engine(engineId);
    }

    /** Initialize the engine. */
    public boolean initEngine() {

        Method[] engineMethods = engine.getClass().getMethods();

        for (Method engineMethod : engineMethods) {
            Annotation[] featureAnnotations = engineMethod.getAnnotations();
            for (Annotation featureAnnotation : featureAnnotations) {
                if (featureAnnotation instanceof InitMethod) {
                    try {
                        engineMethod.invoke(engine);
                    } catch (IllegalAccessException e) {
                        engineInitCallError = "Access error: " + e.getMessage();
                    } catch (InvocationTargetException e) {
                        engineInitCallError = "Call error: " + e.getMessage();
                    }
                }
            }
        }
        return isEngineInited();
    }

    /** Tell if engine is initialized. */
    public boolean isEngineInited() {
        return engineInitCallError == null;
    }

    /** Get the error that might be encountered during engine initialization. */
    public String getEngineInitCallError() {
        return engineInitCallError;
    }

}
