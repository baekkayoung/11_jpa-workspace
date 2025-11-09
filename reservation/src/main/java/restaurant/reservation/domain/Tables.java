package restaurant.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Tables {

    @Id @GeneratedValue
    @Column(name="TABLE_ID")
    private Long id;

    @OneToMany(mappedBy = "tables")
    private List<Reservation> reservations = new ArrayList<>();

    private int tableNumber;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    private int capacity;


}
