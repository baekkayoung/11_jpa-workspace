package restaurant.reservation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import restaurant.reservation.domain.*;
import restaurant.reservation.repository.CustomerRepository;
import restaurant.reservation.repository.ReservationRepository;
import restaurant.reservation.repository.TableRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired ReservationService reservationService;
    @Autowired ReservationRepository reservationRepository;
    @Autowired CustomerRepository customerRepository;
    @Autowired TableRepository tableRepository;


    @Test
    public void 예약_생성하기() throws Exception {
        // given
        Customer customer = new Customer();
        customer.setName("백가영");
        customerRepository.save(customer);

        Tables table = new Tables();
        table.setTableNumber(1);
        table.setCapacity(4);
        table.setStatus(TableStatus.AVAILABLE);
        tableRepository.save(table);

        int reservationPeopleCount = 3;
        LocalDateTime reservationTime = LocalDateTime.now().plusDays(1);

        // when
        Long reservationId = reservationService.reservationInfo(customer.getId(), table.getId(), reservationPeopleCount, reservationTime);

        // then
        Reservation reservation = reservationRepository.findOne(reservationId);
        assertNotNull(reservation);
        assertEquals(customer.getId(), reservation.getCustomer().getId());
        assertEquals(table.getId(), reservation.getTables().getId());
        assertEquals(reservationPeopleCount, reservation.getReservationPeopleCount());
        assertEquals(ReservationStatus.RESERVED, reservation.getStatus());
        assertEquals(TableStatus.RESERVED, reservation.getTables().getStatus()); // 테이블 상태가 예약됨으로 변경됐는지
    }

}

