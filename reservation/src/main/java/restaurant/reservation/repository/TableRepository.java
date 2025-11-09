package restaurant.reservation.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import restaurant.reservation.domain.Tables;

@Repository
@RequiredArgsConstructor
public class TableRepository {

    private final EntityManager em;

    public void save(Tables tables) {
        em.persist(tables);
    }

    public Tables findById(Long id) {
        return em.find(Tables.class, id);
    }

}
