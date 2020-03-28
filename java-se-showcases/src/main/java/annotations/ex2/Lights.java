package annotations.ex2;

/**
 * A simple lights class.
 *
 * @author vision8
 */
public class Lights {

    private String color;

    private LightsType type;

    // no constructor included

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LightsType getType() {
        return type;
    }

    public void setType(LightsType type) {
        this.type = type;
    }

}
