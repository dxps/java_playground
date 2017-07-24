package annotations.ex1;

/**
 * @author vision8
 */
public class Engine {

    private String id;

    public Engine(String id) {
        this.id = id;
    }

    @InitMethod
    public void initialize() {
        System.out.println(
                String.format(">>> Initing engine '%s': woom woom ...", id));
    }

    public String getId() {
        return id;
    }

}
