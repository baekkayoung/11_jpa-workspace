package restaurant.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurant.reservation.domain.Customer;
import restaurant.reservation.domain.Reservation;
import restaurant.reservation.domain.Tables;
import restaurant.reservation.repository.CustomerRepository;
import restaurant.reservation.repository.ReservationRepository;
import restaurant.reservation.repository.TableRepository;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    /*
    * 1. 예약 생성 및 취소 기능을 구현해야 한다.
    o 예약 생성 시 테이블의 수용 인원과 예약 가능 여부를 확인해야 한다.
    o 예약 취소 시 상태를 변경해야 한다.
    * */

    // 예약 정보 등록
    public Long reservationInfo(Long customerId, Long tableId, int reservationPeopleCount, LocalDateTime reservationTime){
        Customer customer = customerRepository.findOne(customerId);
        Tables tables = tableRepository.findById(tableId);

        Reservation reservation = Reservation.createReservation(customer, tables, null, reservationPeopleCount, reservationTime);

        reservationRepository.save(reservation);

        return reservation.getId();
    }

    // 예약 찾기
    public Reservation findReservation(Long reservationId) {
        return reservationRepository.findOne(reservationId);
    }




}
