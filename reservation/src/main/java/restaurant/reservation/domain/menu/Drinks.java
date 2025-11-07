package restaurant.reservation.domain.menu;

import jakarta.persistence.Enumerated;

public class Drinks extends Menu {

    private boolean alcoholic;

    @Enumerated
    private Temperature temperature;
}
