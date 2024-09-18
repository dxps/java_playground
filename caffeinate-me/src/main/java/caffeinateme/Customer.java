package caffeinateme;

import lombok.Data;

@Data
public class Customer {

    private Integer distanceFromShop;

    public void placesOrderFor(String order) {
        // ...
    }

}
