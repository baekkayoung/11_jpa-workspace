package restaurant.reservation.domain.menu;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="dtype")
public abstract class Menu {

    @Id @GeneratedValue
    @Column(name="MENU_ID")
    private Long id;

    @Column(name = "MENU_NAME")
    private String name;

    @Column(name = "MENU_PRICE")
    private int price;

}
