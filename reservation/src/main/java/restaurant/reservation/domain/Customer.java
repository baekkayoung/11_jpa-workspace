package restaurant.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Customer {

    @Id @GeneratedValue
    @Column(name="CUSTOMER_ID")
    private Long id;

    private String name;

    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();


}
