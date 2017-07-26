package annotations.ex1;

/**
 * @author vision8
 */
public class Engine {

    /** Identifier of the engine. */
    private String id;

    /** It tells if the engine was inited. */
    private boolean inited;

    /**
     * Create a new engine with the provided id.
     */
    public Engine(String engineId) {
        this.id = engineId;
    }

    /** Initialization of the engine. */
    @InitMethod
    public void initialize() {
        System.out.println(String.format(">>> Initing engine '%s': woom woom ...", id));
        this.inited = true;
    }

    /** Get the identifier of the engine.*/
    public String getId() {
        return id;
    }

    /** Tell if the engine was initialized. */
    public boolean isInited() {
        return inited;
    }

}
