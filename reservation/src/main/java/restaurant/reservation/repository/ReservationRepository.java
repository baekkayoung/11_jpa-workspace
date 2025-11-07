package restaurant.reservation.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import restaurant.reservation.domain.Reservation;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    // 예약하기
    public void save(Reservation reservation){
        em.persist(reservation);
    }

    // 예약찾기
    public Reservation findOne(Long id){
        return em.find(Reservation.class,id);
    }

}
